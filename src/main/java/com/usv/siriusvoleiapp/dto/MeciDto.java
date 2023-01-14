package com.usv.siriusvoleiapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumTeren;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeciDto {
    private Long idEditie;

    @JsonFormat(pattern = "$data.configuration.format", shape = JsonFormat.Shape.STRING)
    private String data;

    private String numeAdversar;

    private String locatie;

    private String scorCSM;

    private String scorAdversar;

    private EnumTeren teren;

    private String link;
}
