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

    private String participanti;

    @OneToMany(
            targetEntity = Meci.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="idEditie", referencedColumnName = "idMeci")
    private List<Meci> meciuri = new ArrayList<>();

}
