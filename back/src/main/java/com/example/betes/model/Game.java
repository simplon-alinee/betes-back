package com.example.betes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Game {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
	/**
	 * correspond au slug utilisé par l'api pandascore
	 */
	@NotNull
	private String nameApiExt;
	/**
	 * doit être ajouté manuellement
	 */
	private String logo;

	@NotNull
	private Long idApiExt;

	@JsonBackReference
	@OneToMany
	private List<Team> teamList;

	@JsonBackReference
	@OneToMany
	private List<Matches> matchesList;
}
