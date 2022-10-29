package com.usv.siriusvoleiapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ClubSportiv {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idClubSportiv;

    private String numeClubSportiv;

    private String viziuneClubSportiv;

    private String istorieClubSportiv;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="clubSportiv_divizie",
            joinColumns = @JoinColumn(name="idClubSportiv"),
            inverseJoinColumns = @JoinColumn(name="idDivizie")
    )
    private List<Divizie> divizii = new ArrayList<>();

}
