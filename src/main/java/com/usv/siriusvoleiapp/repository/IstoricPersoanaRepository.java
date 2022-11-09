package com.usv.siriusvoleiapp.repository;

import com.usv.siriusvoleiapp.entity.IstoricPersoana;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IstoricPersoanaRepository extends CrudRepository<IstoricPersoana,Long> {
    static void save(List<IstoricPersoana> collect) {
    }
}
