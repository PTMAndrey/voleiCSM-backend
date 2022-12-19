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
public class Editie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEditie;

    private String numeEditie;

    private String perioada;

    @OneToMany(
            targetEntity = ClubSportiv.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="idClubSportiv", referencedColumnName = "idClubSportiv")
    private List<IstoricPosturi> idClubSportiv = new ArrayList<>();

    @OneToMany(
            targetEntity = Divizie.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="idDivizie", referencedColumnName = "idDivizie")
    private List<IstoricPosturi> idDivizie = new ArrayList<>();




}
