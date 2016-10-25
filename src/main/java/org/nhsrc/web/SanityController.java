package org.nhsrc.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SanityController {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }
}
