package com.usv.siriusvoleiapp.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RealizariPersonale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRealizariPersonale;
}
