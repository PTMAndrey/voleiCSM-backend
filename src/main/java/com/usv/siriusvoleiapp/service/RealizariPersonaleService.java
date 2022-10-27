package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.RealizariPersonaleRepository;
import org.springframework.stereotype.Service;

@Service
public class RealizariPersonaleService {
    private final RealizariPersonaleRepository realizariPersonaleRepository;

    public RealizariPersonaleService(RealizariPersonaleRepository realizariPersonaleRepository) {
        this.realizariPersonaleRepository = realizariPersonaleRepository;
    }
}
