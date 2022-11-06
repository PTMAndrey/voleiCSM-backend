package com.usv.siriusvoleiapp.entity;

import com.usv.siriusvoleiapp.declaratieEnum.EnumNumeDivizie;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Divizie {
    @Id
    private Long idDivizie;

    @Enumerated(EnumType.ORDINAL)
    private EnumNumeDivizie denumireDivizie;

    @ManyToMany(mappedBy = "divizii")
    private List<ClubSportiv> cluburiSportive= new ArrayList<>();
}
