package com.example.betes.model;

import lombok.*;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Bet {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Matches matches;


    @NotNull
    private Date dateBet;

    private Boolean resultBet;

    private Date dateUpdate;



}
