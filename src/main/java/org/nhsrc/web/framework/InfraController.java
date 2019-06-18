package org.nhsrc.web.framework;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class InfraController {
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.HEAD})
    public void root(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/app/index.html");
        httpServletResponse.setStatus(302);
    }

    @RequestMapping(value = "/api/ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "/api/error/throw", method = {RequestMethod.GET, RequestMethod.POST})
    public void throwError() {
        throw new RuntimeException("Throwing error to check whether Bugsnag records it");
    }
}