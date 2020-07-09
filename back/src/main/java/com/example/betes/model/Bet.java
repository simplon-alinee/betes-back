package com.example.betes.model;

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

    @OneToOne
    private User user;

    @OneToOne
    private Matches matches;

    @OneToOne
    private Team betOnTeam;


    @NotNull
    private Date dateBet;

    private Boolean resultBet;

    private Date dateUpdate;



}
