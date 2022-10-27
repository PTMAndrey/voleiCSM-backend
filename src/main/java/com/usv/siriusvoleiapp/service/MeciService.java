package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.MeciRepository;
import org.springframework.stereotype.Service;

@Service
public class MeciService {
    private final MeciRepository meciRepository;

    public MeciService(MeciRepository meciRepository) {
        this.meciRepository = meciRepository;
    }
}
