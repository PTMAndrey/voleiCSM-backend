package com.usv.siriusvoleiapp.controller;

import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusStire;
import com.usv.siriusvoleiapp.declaratieEnum.EnumTipStire;
import com.usv.siriusvoleiapp.dto.StiriDto;
import com.usv.siriusvoleiapp.entity.Stiri;
import com.usv.siriusvoleiapp.service.StiriService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/stiri")
public class StiriController {
    private final StiriService stiriService;

    public StiriController(StiriService stiriService) {
        this.stiriService = stiriService;
    }

    @GetMapping
    public ResponseEntity<List<Stiri>> getStiri(@RequestParam("status") EnumStatusStire status){
        return ResponseEntity.ok(stiriService.getStiri(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stiri> getStireDupaId(@PathVariable UUID id){
        return ResponseEntity.ok(stiriService.getStireDupaId(id));
    }

    @GetMapping("/filtru")
    public ResponseEntity<List<Stiri>> getStiri(@RequestParam("status") EnumStatusStire status, @RequestParam("tipStire") EnumTipStire tipStire, @RequestParam("numarZile") Optional<String> numarZile, @RequestParam("perioadaSpecifica") Optional<String> perioadaSpecifica, @RequestParam("dataSpecifica") Optional<String> dataSpecifica) throws ParseException {
        if(numarZile.isPresent())
            return ResponseEntity.ok(stiriService.getStiriFiltrate(status, tipStire, numarZile.get(), "", ""));
        else
            if(perioadaSpecifica.isPresent())
                return ResponseEntity.ok(stiriService.getStiriFiltrate(status, tipStire, "", perioadaSpecifica.get(), ""));
                else
                    if(dataSpecifica.isPresent())
                        return ResponseEntity.ok(stiriService.getStiriFiltrate(status, tipStire, "", "", dataSpecifica.get()));
                    else
                        return ResponseEntity.ok(stiriService.getStiriFiltrate(status, tipStire, "", "", ""));
    }

    @PostMapping
    public ResponseEntity<Stiri> addStire(@RequestParam("file") List<MultipartFile> file, @ModelAttribute StiriDto stiriDto) throws IOException, ParseException {
        return ResponseEntity.ok(stiriService.addStire(file,stiriDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stiri> updateStire(@PathVariable UUID id,@ModelAttribute StiriDto stiriDto, @RequestParam("file") Optional<List<MultipartFile>> file) throws IOException {
        if(file.isPresent())
            return ResponseEntity.ok(stiriService.updateStire(id, stiriDto, file.get()));
        else
            return ResponseEntity.ok(stiriService.updateStireFaraPoza(id, stiriDto));
    }

    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<Stiri> updateStatusStire(@PathVariable UUID id, @RequestParam("status") EnumStatusStire status) {
        return ResponseEntity.ok(stiriService.updateStatusStire(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStire(@PathVariable UUID id){
        stiriService.deleteStire(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
