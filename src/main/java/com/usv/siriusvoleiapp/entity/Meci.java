package com.usv.siriusvoleiapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Meci {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idEditie;

    private String status;

    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String data;

    private String numeAdversar;

    private String logoAdversar;

    private String locatie;

    private String scorCSM;

    private String scorAdversar;

    private String teren;
}
