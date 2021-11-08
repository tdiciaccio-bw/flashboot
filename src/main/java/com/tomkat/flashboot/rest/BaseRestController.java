package com.tomkat.flashboot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class BaseRestController {
    @GetMapping()
    public String home(Principal principal) {
        return "Hello " + principal.getName();
    }

}
