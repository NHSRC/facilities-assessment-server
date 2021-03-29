package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private static Logger logger = LoggerFactory.getLogger(MetabaseController.class);

    @RequestMapping(value = "metabase-dashboard-url", method = {RequestMethod.GET})
    public String getMetabaseDashboardEmbedUrl(@RequestParam Map<String, String> params) throws JsonProcessingException {
        return getEmbedUrl(params, "dashboard");
    }

    private String getEmbedUrl(Map<String, String> params, String type) throws JsonProcessingException {
        Payload payload = new Payload();
        PayloadResource payloadResource = type.equals("question") ? new PayloadQuestion() : new PayloadDashboard();
        payloadResource.setResourceId(Integer.parseInt(params.get(RESOURCE_ID)));
        payload.setResource(payloadResource);
        HashMap<String, String> metabaseRequestParams = new HashMap<>(params);
        metabaseRequestParams.remove(RESOURCE_ID);
        payload.setParams(metabaseRequestParams);

        logger.info(JsonUtil.OBJECT_MAPPER.writeValueAsString(payload));
        Jwt token = JwtHelper.encode(JsonUtil.OBJECT_MAPPER.writeValueAsString(payload), new MacSigner(metabaseSecretKey));
        String s = metabaseUrl + "/embed/" + type + "/" + token.getEncoded() + "#bordered=false&titled=false";
        logger.info(s);
        return s;
    }

    @RequestMapping(value = "metabase-question-url", method = {RequestMethod.GET})
    public String getMetabaseQuestionEmbedUrl(@RequestParam Map<String, String> params) throws JsonProcessingException {
        return getEmbedUrl(params, "question");
    }

    public class Payload {
        private PayloadResource resource;
        private Map params = new HashMap();
        private int exp = 1606896785;

        public PayloadResource getResource() {
            return resource;
        }

        public void setResource(PayloadResource resource) {
            this.resource = resource;
        }

        public Map getParams() {
            return params;
        }

        public void setParams(Map params) {
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