package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.PersoanaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersoanaService {
    private final PersoanaRepository persoanaRepository;

    public PersoanaService(PersoanaRepository persoanaRepository) {
        this.persoanaRepository = persoanaRepository;
    }
}
