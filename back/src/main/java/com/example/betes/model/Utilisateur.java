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
    private Date dateNaissance;
    private String password;
    private String role;
    private Date dateInscription;
    private Boolean validationInscription;
}
