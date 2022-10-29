package com.usv.siriusvoleiapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Divizie {
    @Id
    private Long idDivizie;

    private String denumireDivizie;

    @ManyToMany(mappedBy = "divizii")
    private List<ClubSportiv> cluburiSportive= new ArrayList<>();
}
