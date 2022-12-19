package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.entity.IstoricPosturi;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.service.IstoricPosturiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/istoricPosturi")
public class IstoricPosturiController {
    private final IstoricPosturiService istoricPersoaneService;

    public IstoricPosturiController(IstoricPosturiService istoricPersoaneService) {
        this.istoricPersoaneService = istoricPersoaneService;
    }

    @GetMapping
    public ResponseEntity<List<IstoricPosturi>> getIstoricPersoane(){
        return ResponseEntity.ok(istoricPersoaneService.getIstoricPosturi());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<IstoricPosturi>> getIstoricPersoana(@PathVariable UUID id){
        return ResponseEntity.ok(istoricPersoaneService.getIstoricPosturi(id));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Persoana> adaugaIstoricPersoana(@PathVariable UUID id, @RequestBody List<IstoricPosturi> istoricPersoana) {
        return ResponseEntity.ok(istoricPersoaneService.adaugaIstoricPosturi(id,istoricPersoana));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Persoana> updateIstoricPosturi(@PathVariable UUID id, @RequestBody List<IstoricPosturi> istoricPosturis)  {
        return ResponseEntity.ok(istoricPersoaneService.updateIstoricPosturi(id,istoricPosturis));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIstoricPosturi(@PathVariable long id){
        istoricPersoaneService.deleteIstoricPosturi(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
