package com.usv.siriusvoleiapp.entity;

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
public class Editie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEditie;

}
