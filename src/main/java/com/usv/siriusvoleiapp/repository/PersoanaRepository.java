package com.usv.siriusvoleiapp.repository;

import com.usv.siriusvoleiapp.entity.Persoana;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PersoanaRepository extends CrudRepository<Persoana, UUID> {
}
