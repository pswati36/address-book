package com.addressbook.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UptimeController {

    @GetMapping("/uptime")
    Boolean getUptime() {
        return Boolean.TRUE;
    }

}
