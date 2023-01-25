package com.usv.siriusvoleiapp.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Premii {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String denumire;

    private String locCampionat;

    private String an;

    private Long idEditie;

    private Long idDivizie;

    private String numeDivizie;

    private String numeEditie;
}
