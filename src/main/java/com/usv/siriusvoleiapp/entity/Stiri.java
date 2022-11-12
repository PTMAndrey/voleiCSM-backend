package com.usv.siriusvoleiapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusStire;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Stiri {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String titlu;

    @NotBlank(message = "Name is mandatory")
    private String descriere;

    @Enumerated
    @NotBlank(message = "Name is mandatory")
    private EnumStatusStire status;

    @NotBlank(message = "Name is mandatory")
    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String dataPublicarii;

    private String imagini;

    @ElementCollection(targetClass=String.class)
    private List<String> imaginiURL;

}
