package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.StiriService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StiriController {
    private final StiriService stiriService;

    public StiriController(StiriService stiriService) {
        this.stiriService = stiriService;
    }
}
