package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nhsrc.utils.JsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class MetabaseController {
    private static String METABASE_SITE_URL = "http://localhost:3000";
    private static String METABASE_SECRET_KEY = "b2ccad4e7b2579af6a21efaf43fd608ffc47226014692550a6635a38a307c442";
    public static final String RESOURCE_ID = "resourceId";

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
        // Need to encode the secret key
//        Jwt token = JwtHelper.encode("{\"resource\": {\"question\": 1}, \"params\": {}}", new MacSigner(METABASE_SECRET_KEY));
        System.out.println(JsonUtil.OBJECT_MAPPER.writeValueAsString(payload));
        Jwt token = JwtHelper.encode(JsonUtil.OBJECT_MAPPER.writeValueAsString(payload), new MacSigner(METABASE_SECRET_KEY));
        return METABASE_SITE_URL + "/embed/" + type + "/" + token.getEncoded() + "#bordered=false&titled=true";
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