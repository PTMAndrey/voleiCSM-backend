package com.usv.siriusvoleiapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IstoricPersoanaDto {
    private Long idPersoana;

    private String post;

    private String dataInceput;

    private String dataFinal;
}
