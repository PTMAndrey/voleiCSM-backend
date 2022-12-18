package com.usv.siriusvoleiapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumPost;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IstoricPosturi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIstoricPersoana;

    private UUID id;

    private EnumPost post;

    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String dataInceput;

    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String dataFinal;


}
