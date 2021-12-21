package org.nhsrc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class HealthCheckService {
    @Value("${healthcheck.download.facilities.job}")
    private String mainJobId;
    @Value("${healthcheck.scoring.job}")
    private String scoringJobId;
    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String PING_BASE_URL = "https://hc-ping.com/";

    public void verifyMainJob() {
        verify(mainJobId);
    }

    public void verifyScoringJob() {
        verify(scoringJobId);
    }

    public void verify(String uuid) {
        if (!uuid.equals("dummy"))
            restTemplate.exchange(URI.create(String.format("%s%s", PING_BASE_URL, uuid)), HttpMethod.GET, null, String.class);
    }
}
