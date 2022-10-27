package com.usv.siriusvoleiapp.service;

import com.usv.siriusvoleiapp.repository.ClubSportivRepository;
import org.springframework.stereotype.Service;

@Service
public class ClubSportivService {
    public final ClubSportivRepository clubSportivRepository;

    public ClubSportivService(ClubSportivRepository clubSportivRepository) {
        this.clubSportivRepository = clubSportivRepository;
    }
}
