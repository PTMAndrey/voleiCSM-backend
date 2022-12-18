package com.usv.siriusvoleiapp.repository;

import com.usv.siriusvoleiapp.entity.IstoricPosturi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IstoricPosturiRepository extends CrudRepository<IstoricPosturi, Long> {
    static void save(List<IstoricPosturi> collect) {
    }
}
