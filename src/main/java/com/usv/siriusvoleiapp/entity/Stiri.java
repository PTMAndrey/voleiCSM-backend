package com.usv.siriusvoleiapp.entity;

import lombok.*;
import org.springframework.stereotype.Service;

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
public class Stiri {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStiri;
}
