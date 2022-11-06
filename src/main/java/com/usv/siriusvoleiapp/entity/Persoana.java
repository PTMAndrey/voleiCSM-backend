package com.usv.siriusvoleiapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumNumeDivizie;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Persoana implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPersoana;

    private String imagine;

    private String nume;

    private String prenume;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private String dataNasterii;

    private String inalitime;

    private String nationalitate;

    private String post;

    private String descriere;

    @Enumerated(EnumType.ORDINAL)
    private EnumNumeDivizie numeDivizie;

}
