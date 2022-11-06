package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.ClubSportivDto;
import com.usv.siriusvoleiapp.dto.DivizieDto;
import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.entity.ClubSportiv;
import com.usv.siriusvoleiapp.service.ClubSportivService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<ClubSportivDto> addClubSportiv(@RequestParam("file") MultipartFile file, @ModelAttribute ClubSportivDto clubSportivDto) throws IOException {
        return ResponseEntity.ok(clubSportivService.addClubSportiv(file,clubSportivDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubSportivDto> updateClubSportiv(@PathVariable Long id, @ModelAttribute ClubSportivDto clubSportivDto, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(clubSportivService.updateClubSportiv(id,clubSportivDto,file));
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

    @GetMapping("/{id}/divizii")
    public ResponseEntity<List<DivizieDto>> getDiviziiClubSportiv(@PathVariable Long id){
        return ResponseEntity.ok(clubSportivService.getDiviziiClubSportiv(id));
    }

    @PutMapping("/{idClubSportiv}/persoana/{idPersoana}")
    public ResponseEntity<ClubSportiv> adaugarePersoanaLaClubSportiv(@PathVariable Long idClubSportiv, @PathVariable Long idPersoana){
        return ResponseEntity.ok(clubSportivService.adaugarePersoanaLaClubSportiv(idClubSportiv,idPersoana));
    }

    @GetMapping("/{id}/persoane")
    public ResponseEntity<List<PersoanaDto>> getPersoaneClubSportiv(@PathVariable Long id){
        return ResponseEntity.ok(clubSportivService.getPersoaneClubSportiv(id));
    }
}
