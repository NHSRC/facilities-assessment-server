package org.nhsrc.web.framework;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ErrorController {
    @RequestMapping(value = "/error/throw", method = {RequestMethod.GET})
    public void throwError() {
        throw new RuntimeException("Throwing error to check whether Bugsnag records it");
    }
}