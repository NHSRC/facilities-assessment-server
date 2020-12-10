package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.util.HashMap;

public class MetabaseControllerTest {
    @Test
    public void getMetabaseDashboardEmbedUrl() throws JsonProcessingException {
        MetabaseController metabaseController = new MetabaseController();
        HashMap<String, String> params = new HashMap<>();
        params.put(MetabaseController.RESOURCE_ID, "2");
        System.out.println(metabaseController.getMetabaseDashboardEmbedUrl(params));
    }
}