package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.StiriRepository;
import org.springframework.stereotype.Service;

@Service
public class StiriService {
    private final StiriRepository stiriRepository;

    public StiriService(StiriRepository stiriRepository) {
        this.stiriRepository = stiriRepository;
    }
}
