package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.PremiiRepository;
import org.springframework.stereotype.Service;

@Service
public class PremiiService {
    private final PremiiRepository premiiRepository;

    public PremiiService(PremiiRepository premiiRepository) {
        this.premiiRepository = premiiRepository;
    }
}
