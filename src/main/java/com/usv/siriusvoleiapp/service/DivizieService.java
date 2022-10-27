package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.DivizieRepository;
import org.springframework.stereotype.Service;

@Service
public class DivizieService {
    private final DivizieRepository divizieRepository;

    public DivizieService(DivizieRepository divizieRepository) {
        this.divizieRepository = divizieRepository;
    }
}
