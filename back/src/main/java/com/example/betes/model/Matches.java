package com.example.betes.model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Matches {

    // nommé MATCHES car MATCH provoque une erreur en DB (certainement mot-clé côté DB)

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private Game Game;

    private Date dateMatch;

    @OneToOne
    private Team winner;

    @NotNull
    @UniqueElements
    private Integer idApiExt;

    @OneToMany
    private List<Bet> listBet;

    @OneToMany
    private List<Team> teamsParticipating;

}
