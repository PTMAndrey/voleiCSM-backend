package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.PersoanaService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PerosanaController {
    private final PersoanaService persoanaService;

    public PerosanaController(PersoanaService persoanaService) {
        this.persoanaService = persoanaService;
    }
}
