package com.example.betes.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

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
    @Column(name = "ID_TEAM")
    private Long id;

    @Column(name = "ID_GAME", length = 20)
    @NotNull
    private Long idGame;

    @Column(name = "TEAM_NAME", length = 50)
    @NotNull
    private String teamName;

    @Column(name = "LOGO")
    private Blob logo;

    @Column(name = "ID_API_EXT", unique = true)
    @NotNull
    private Integer idApiExt;





}
