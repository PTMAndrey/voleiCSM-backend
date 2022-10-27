package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.DivizieService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DivizieController {
    private final DivizieService divizieService;

    public DivizieController(DivizieService divizieService) {
        this.divizieService = divizieService;
    }
}
