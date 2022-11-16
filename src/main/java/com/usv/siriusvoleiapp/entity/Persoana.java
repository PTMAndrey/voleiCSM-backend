package com.usv.siriusvoleiapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumNumeDivizie;
import com.usv.siriusvoleiapp.declaratieEnum.EnumPersonal;
import com.usv.siriusvoleiapp.declaratieEnum.EnumPost;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Persoana implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String imagine;

    private String nume;

    private String prenume;

    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String dataNasterii;

    private String inaltime;

    private String nationalitate;

    @Enumerated
    private EnumPersonal personal;

    @Enumerated
    private EnumPost post;

    private String descriere;

    @Enumerated(EnumType.ORDINAL)
    private EnumNumeDivizie numeDivizie;

    @OneToMany(
            targetEntity = IstoricPersoana.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="id", referencedColumnName = "id")
    private List<IstoricPersoana> istoricPosturi = new ArrayList<>();

}
