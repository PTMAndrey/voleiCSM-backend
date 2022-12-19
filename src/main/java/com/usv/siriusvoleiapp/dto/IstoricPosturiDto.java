package com.usv.siriusvoleiapp.dto;

import com.usv.siriusvoleiapp.declaratieEnum.EnumPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IstoricPosturiDto {

    private EnumPost post;

    private String dataInceput;

    private String dataFinal;
}
