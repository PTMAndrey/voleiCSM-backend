package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.StafService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StafController {
    private final StafService stafService;

    public StafController(StafService stafService) {
        this.stafService = stafService;
    }
}
