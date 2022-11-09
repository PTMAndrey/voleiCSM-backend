package com.usv.siriusvoleiapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusStire;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Stiri {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStiri;

    private String titlu;

    private String descriere;

    @Enumerated
    private EnumStatusStire status;

    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String dataPublicarii;

    private String imagini;

    @ElementCollection(targetClass=String.class)
    private List<String> imaginiURL;

}
