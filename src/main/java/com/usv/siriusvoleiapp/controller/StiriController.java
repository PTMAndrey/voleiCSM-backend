package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.dto.PersoanaDto;
import com.usv.siriusvoleiapp.dto.StiriDto;
import com.usv.siriusvoleiapp.entity.Persoana;
import com.usv.siriusvoleiapp.entity.Stiri;
import com.usv.siriusvoleiapp.service.StiriService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stiri")
public class StiriController {
    private final StiriService stiriService;

    public StiriController(StiriService stiriService) {
        this.stiriService = stiriService;
    }

    @GetMapping
    public ResponseEntity<List<Stiri>> getStiri(@RequestParam("status") String status){
        return ResponseEntity.ok(stiriService.getStiri(status));
    }

    @PostMapping
    public ResponseEntity<Stiri> addStire(@RequestParam("file") List<MultipartFile> file, @ModelAttribute StiriDto stiriDto) throws IOException {
        return ResponseEntity.ok(stiriService.addStire(file,stiriDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stiri> updateStire(@PathVariable Long id,@ModelAttribute StiriDto stiriDto, @RequestParam("file") List<MultipartFile> file) throws IOException {
        return ResponseEntity.ok(stiriService.updateStire(id, stiriDto, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStire(@PathVariable Long id){
        stiriService.deleteStire(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
