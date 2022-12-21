package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.EditieDto;
import com.usv.siriusvoleiapp.entity.Editie;
import com.usv.siriusvoleiapp.service.EditieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editie")
public class EditieController {
    private final EditieService editieService;

    public EditieController(EditieService editieService) {
        this.editieService = editieService;
    }

    @GetMapping
    public ResponseEntity<List<Editie>> getEditii(){
        return ResponseEntity.ok(editieService.getEditii());
    }

    @PostMapping
    public ResponseEntity<Editie> addEditie(@RequestBody EditieDto editieDto) {
        return ResponseEntity.ok(editieService.addEditie(editieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditie(@PathVariable Long id){
        editieService.deleteEditie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addTo/{idEditie}/club/{idClub}/divizie/{idDivizie}")
    public ResponseEntity<Editie> addParticipantLaEditie(@PathVariable Long idEditie, @PathVariable Long idClub, @PathVariable Long idDivizie){
        return ResponseEntity.ok(editieService.addParticipantLaEditie(idEditie,idClub,idDivizie));
    }
}
