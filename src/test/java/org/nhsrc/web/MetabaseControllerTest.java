package org.nhsrc.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

public class MetabaseControllerTest {
    @Test
    public void getMetabaseDashboardEmbedUrl() throws JsonProcessingException {
        MetabaseController metabaseController = new MetabaseController();
        System.out.println(metabaseController.getMetabaseDashboardEmbedUrl());
    }
}