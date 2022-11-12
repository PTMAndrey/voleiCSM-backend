package com.usv.siriusvoleiapp.repository;

import com.usv.siriusvoleiapp.entity.Stiri;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StiriRepository extends CrudRepository<Stiri, UUID> {
}
