package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.MeciService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeciController {
    private final MeciService meciService;

    public MeciController(MeciService meciService) {
        this.meciService = meciService;
    }
}
