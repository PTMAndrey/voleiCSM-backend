package com.usv.siriusvoleiapp.entity;

import lombok.*;

import javax.persistence.*;

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

    private String numeEditie;

    private String perioada;

    private String participanti;
}
