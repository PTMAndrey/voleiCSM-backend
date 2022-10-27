package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.service.EditieService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditieController {
    private final EditieService editieService;

    public EditieController(EditieService editieService) {
        this.editieService = editieService;
    }
}
