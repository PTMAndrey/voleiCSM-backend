package com.usv.siriusvoleiapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiiDto {
    private String denumire;

    private String locCampionat;

    private String an;

    private Long idEditie;

    private Long idDivizie;
}
