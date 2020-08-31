package com.example.betes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
// TODO rajouter un notNull, et trouver un moyen de contourner
    @OneToOne
    private Game game;

    @NotNull
    private String teamName;

    private String logo;

    private Long idApiExt;

    @JsonBackReference
    @ManyToMany(mappedBy = "teamsParticipating")
    private List<MatchEntity> matchEntityList;

}
