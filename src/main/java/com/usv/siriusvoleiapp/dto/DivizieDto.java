package com.usv.siriusvoleiapp.dto;

import com.usv.siriusvoleiapp.declaratieEnum.EnumNumeDivizie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivizieDto {
    private Long idDivizie;

    private EnumNumeDivizie denumireDivizie;
}
