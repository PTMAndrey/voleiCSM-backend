package com.usv.siriusvoleiapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usv.siriusvoleiapp.declaratieEnum.EnumNumeDivizie;
import com.usv.siriusvoleiapp.declaratieEnum.EnumPersonal;
import com.usv.siriusvoleiapp.declaratieEnum.EnumPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersoanaDto {
    private String imagine;

    private String nume;

    private String prenume;

    private String dataNasterii;

    private String inaltime;

    private String nationalitate;

    private EnumPersonal personal;

    private EnumPost post;

    private String descriere;

    private EnumNumeDivizie numeDivizie;
}
