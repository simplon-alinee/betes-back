package com.example.betes.model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

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

    @NotNull
    @OneToOne
    private Game game;

    @NotNull
    private String teamName;

    private String logo;

    @NotNull
    @UniqueElements
    private Integer idApiExt;

    @OneToMany
    private List<Matches> matchesList;





}
