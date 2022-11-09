package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.entity.IstoricPersoana;
import com.usv.siriusvoleiapp.service.IstoricPersoaneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/istoricPersoane")
public class IstoricPersoanaController {
    private final IstoricPersoaneService istoricPersoaneService;

    public IstoricPersoanaController(IstoricPersoaneService istoricPersoaneService) {
        this.istoricPersoaneService = istoricPersoaneService;
    }

    @GetMapping
    public ResponseEntity<List<IstoricPersoana>> getIstoricPersoane(){
        return ResponseEntity.ok(istoricPersoaneService.getIstoricPersoane());
    }

}
