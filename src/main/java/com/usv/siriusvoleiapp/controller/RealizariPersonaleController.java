package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.RealizariPersonaleService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealizariPersonaleController {
    private final RealizariPersonaleService realizariPersonaleService;

    public RealizariPersonaleController(RealizariPersonaleService realizariPersonaleService) {
        this.realizariPersonaleService = realizariPersonaleService;
    }
}
