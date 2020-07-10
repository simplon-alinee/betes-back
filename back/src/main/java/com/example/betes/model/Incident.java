package com.example.betes.model;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Incident {
	private String type;
	private Game game;
	private Team team;
	private String change;
}
