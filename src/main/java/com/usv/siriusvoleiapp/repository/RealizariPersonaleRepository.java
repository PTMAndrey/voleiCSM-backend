package com.usv.siriusvoleiapp.repository;

import com.usv.siriusvoleiapp.entity.IstoricPersoana;
import com.usv.siriusvoleiapp.entity.RealizariPersonale;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RealizariPersonaleRepository extends CrudRepository<RealizariPersonale,Long> {
    static void save(List<RealizariPersonale> collect) {
    }
}
