package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.StafRepository;
import org.springframework.stereotype.Service;

@Service
public class StafService {
    private final StafRepository stafRepository;

    public StafService(StafRepository stafRepository) {
        this.stafRepository = stafRepository;
    }
}
