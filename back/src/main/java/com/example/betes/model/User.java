package com.example.betes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String lastname;

    @NotNull
    private String firstname;

    @NotNull
    @UniqueElements
    private  String username;

    @NotNull
    private Date birthDate;

    @NotNull
    private String password;

    @NotNull
    private String role;

    @NotNull
    private Date inscriptionDate;

    @NotNull
    private Boolean validationInscription;

    @JsonBackReference
    @OneToMany
    private List<Bet> listBet;

}
