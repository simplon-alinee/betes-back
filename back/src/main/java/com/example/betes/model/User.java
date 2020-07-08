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
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Long id;

    @Column(name = "LASTNAME", length = 50)
    @NotNull
    private String lastname;

    @Column(name = "FIRSTNAME", length = 50)
    @NotNull
    private String firstname;

    @Column(name = "USERNAME", unique = true, length = 50)
    @NotNull
    private  String username;

    @Column(name = "BIRTH_DATE")
    @NotNull
    private Date birthDate;

    @Column(name = "PASSWORD", length = 50)
    @NotNull
    private String password;

    @Column(name = "ROLE", length = 20)
    @NotNull
    private String role;

    @Column(name = "INSCRIPTION_DATE")
    @NotNull
    private Date inscriptionDate;

    @Column(name = "VALIDATION_INSCRIPTION")
    @NotNull
    private Boolean validationInscription;
}
