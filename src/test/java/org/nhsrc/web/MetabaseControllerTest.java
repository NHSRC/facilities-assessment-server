package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.nhsrc.common.AbstractWebIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class MetabaseControllerTest extends AbstractWebIntegrationTest {
    @Autowired
    private MetabaseController metabaseController;

    @Test
    public void getMetabaseDashboardEmbedUrl() throws JsonProcessingException {
        HashMap<String, String> params = new HashMap<>();
        params.put(MetabaseController.RESOURCE_ID, "2");
        System.out.println(metabaseController.getMetabaseDashboardEmbedUrl(params));
    }
}
