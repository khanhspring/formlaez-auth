package com.formlaez.auth.api.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "internal/ping")
public class PingInternalController {

    @GetMapping
    public String ping() {
        return "pong";
    }
}
