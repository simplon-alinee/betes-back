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
// nommé MATCHES car MATCH provoque une erreur en DB (certainement mot-clé côté DB)
public class Matches {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID_MATCHES")
    private Long idMatches;

    @Column(name = "GAME_NAME", length = 50)
    @NotNull
    private String gameName;

    @Column(name = "DATE_MATCH")
    private Date dateMatch;

    @Column(name = "WINNER", length = 50)
    private String winner;

    @Column(name = "ID_API_EXT", unique = true)
    @NotNull
    private Integer idApiExt;

}
