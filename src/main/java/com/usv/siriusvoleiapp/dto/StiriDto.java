package com.usv.siriusvoleiapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumStatusStire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StiriDto {
    private String titlu;

    private String autor;

    private String descriere;

    private String hashtag;

    private EnumStatusStire status;

    private String dataPublicarii;

    private String imagine;

    private String videoclipuri;
}
