package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.SponsoriService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SponsoriController {
    private final SponsoriService sponsoriService;

    public SponsoriController(SponsoriService sponsoriService) {
        this.sponsoriService = sponsoriService;
    }
}
