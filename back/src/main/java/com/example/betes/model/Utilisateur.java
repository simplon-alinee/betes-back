package com.example.betes.model;

import lombok.*;
import sun.security.util.Password;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Utilisateur {

    @Id
    @Column(name = "ID_UTILISATEUR")
    private Integer id;

    private String nom;
    private String prenom;
    private  String pseudo;

    @Column(name = "date_naissance")
    private Date dateNaissance;
    private String password;
    private String role;

    @Column(name = "date_inscription")
    private Date dateInscription;

    @Column(name = "validation_inscription")
    private Boolean validationInscription;
}
