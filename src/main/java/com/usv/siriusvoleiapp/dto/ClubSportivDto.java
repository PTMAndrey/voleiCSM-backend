package com.usv.siriusvoleiapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubSportivDto {

    private String numeClubSportiv;

    private String viziuneClubSportiv;

    private String istorieClubSportiv;
}
