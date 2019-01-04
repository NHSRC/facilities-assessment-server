package org.nhsrc.common;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractWebIntegrationTest {
    @Autowired
    protected TestRestTemplate testRestTemplate;

    protected Object getResponse(String url, Class responseType) {
        return responseType.cast(this.testRestTemplate.getForObject("/api/" + url, responseType));
    }
}
