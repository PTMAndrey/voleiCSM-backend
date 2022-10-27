package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.SponsoriRepository;
import org.springframework.stereotype.Service;

@Service
public class SponsoriService {
    private final SponsoriRepository sponsoriRepository;

    public SponsoriService(SponsoriRepository sponsoriRepository) {
        this.sponsoriRepository = sponsoriRepository;
    }
}
