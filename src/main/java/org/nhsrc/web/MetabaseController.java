package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nhsrc.domain.security.User;
import org.nhsrc.service.UserService;
import org.nhsrc.utils.JsonUtil;
import org.nhsrc.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class MetabaseController {
    @Value("${metabase.url}")
    private String metabaseUrl = "http://localhost:3000";
    @Value("${metabase.secret.key}")
    private String metabaseSecretKey;

    static final String RESOURCE_ID = "resourceId";

    private static final Logger logger = LoggerFactory.getLogger(MetabaseController.class);

    private static final String STATE_PARAM = "state";
    private static final String PROGRAM_PARAM = "assessment_tool_mode";
    private final UserService userService;

    @Autowired
    public MetabaseController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "metabase-dashboard-url", method = {RequestMethod.GET})
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<String> getMetabaseDashboardEmbedUrl(@RequestParam Map<String, String> params, Principal principal) throws JsonProcessingException {
        User user = userService.findUserByPrincipal(principal);
        String stateName = params.get(STATE_PARAM);
        String programName = params.get(PROGRAM_PARAM);
        if (StringUtil.isNotEmpty(stateName) && !userService.hasStatePrivilege(stateName, user)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (StringUtil.isNotEmpty(programName) && !user.hasProgramPrivilege(programName)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(getEmbedUrl(params), HttpStatus.OK);
    }

    private String getEmbedUrl(Map<String, String> params) throws JsonProcessingException {
        Payload payload = new Payload();
        PayloadResource payloadResource = new PayloadDashboard();
        payloadResource.setResourceId(Integer.parseInt(params.get(RESOURCE_ID)));
        payload.setResource(payloadResource);
        HashMap<String, String> metabaseRequestParams = new HashMap<>(params);
        metabaseRequestParams.remove(RESOURCE_ID);
        payload.setParams(metabaseRequestParams);

        logger.info(JsonUtil.OBJECT_MAPPER.writeValueAsString(payload));
        Jwt token = JwtHelper.encode(JsonUtil.OBJECT_MAPPER.writeValueAsString(payload), new MacSigner(metabaseSecretKey));
        String s = metabaseUrl + "/embed/" + "dashboard" + "/" + token.getEncoded() + "#bordered=false&titled=false";
        logger.info(s);
        return s;
    }

    public class Payload {
        private PayloadResource resource;
        private Map<String, String> params = new HashMap<>();
        private int exp = 1606896785;

        public PayloadResource getResource() {
            return resource;
        }

        public void setResource(PayloadResource resource) {
            this.resource = resource;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

//        public int getExp() {
//            return exp;
//        }

        public void setExp(int exp) {
            this.exp = exp;
        }
    }

    public abstract class PayloadResource {
        public abstract void setResourceId(int id);
    }

    public class PayloadQuestion extends PayloadResource {
        private int question;

        public int getQuestion() {
            return question;
        }

        public void setQuestion(int question) {
            this.question = question;
        }

        @Override
        public void setResourceId(int id) {
            this.setQuestion(id);
        }
    }

    public class PayloadDashboard extends PayloadResource {
        private int dashboard;

        public int getDashboard() {
            return dashboard;
        }

        public void setDashboard(int dashboard) {
            this.dashboard = dashboard;
        }

        @Override
        public void setResourceId(int id) {
            this.setDashboard(id);
        }
    }
}
