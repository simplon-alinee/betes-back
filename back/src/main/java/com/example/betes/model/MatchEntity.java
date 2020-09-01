package com.example.betes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class MatchEntity {

    // nommé MATCHES car MATCH provoque une erreur en DB (certainement mot-clé côté DB)

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private Game game;

    private Date dateMatch;

    @JsonManagedReference
    @OneToOne
    private Team winner;

    @NotNull
    private Integer idApiExt;

    @OneToMany
    private List<Bet> listBet;

    @JsonManagedReference
    @ManyToMany()
    private List<Team> teamsParticipating;

}
