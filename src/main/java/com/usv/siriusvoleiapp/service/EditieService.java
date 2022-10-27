package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.EditieRepository;
import org.springframework.stereotype.Service;

@Service
public class EditieService {
    private final EditieRepository editieRepository;

    public EditieService(EditieRepository editieRepository) {
        this.editieRepository = editieRepository;
    }
}
