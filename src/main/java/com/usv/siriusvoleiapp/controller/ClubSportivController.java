package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.ClubSportivDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.service.ClubSportivService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cluburiSportive")
public class ClubSportivController {
    private final ClubSportivService clubSportivService;

    public ClubSportivController(ClubSportivService clubSportivService) {
        this.clubSportivService = clubSportivService;
    }

    @GetMapping
    public ResponseEntity<List<ClubSportivDto>> getCluburiSportive(){
        return ResponseEntity.ok(clubSportivService.getCluburiSportive());
    }

    @PostMapping
    public ResponseEntity<ClubSportivDto> addClubSportiv(@RequestBody ClubSportivDto clubSportivDto){
        return ResponseEntity.ok(clubSportivService.addClubSportiv(clubSportivDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubSportivDto> updateClubSportiv(@PathVariable Long id, @RequestBody ClubSportivDto clubSportivDto){
        return ResponseEntity.ok(clubSportivService.updateClubSportiv(id,clubSportivDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClubSportiv(@PathVariable Long id){
        clubSportivService.deleteClubSportiv(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{idClubSportiv}/divizie/{idDivizie}")
    public ResponseEntity<ClubSportiv> adaugareDivizieLaClubSportiv(@PathVariable Long idClubSportiv, @PathVariable Long idDivizie){
        return ResponseEntity.ok(clubSportivService.adaugareDivizieLaClubSportiv(idClubSportiv,idDivizie));
    }
}
