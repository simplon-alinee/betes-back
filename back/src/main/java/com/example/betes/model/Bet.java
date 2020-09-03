package com.example.betes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Bet {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToOne
    private User user;

    /*
    matchId because matches causes a bug with jpa
     */

    @OneToOne
    private MatchEntity matchEntity;

    @OneToOne
    private Team betOnTeam;


    @NotNull
    private Date dateBet;

    private Boolean resultBet;

    private Date dateUpdate;



}
