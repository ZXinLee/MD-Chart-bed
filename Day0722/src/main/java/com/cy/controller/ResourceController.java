package com.cy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/retrieve")
    public String doRetrieve(){
        return "do retrieve resource success";
    }
}
