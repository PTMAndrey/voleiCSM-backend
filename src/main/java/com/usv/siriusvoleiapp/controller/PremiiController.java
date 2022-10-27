package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.PremiiService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PremiiController {
    private final PremiiService premiiService;

    public PremiiController(PremiiService premiiService) {
        this.premiiService = premiiService;
    }
}
