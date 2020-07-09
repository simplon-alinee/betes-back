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
public class Game {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
// est-ce utile? j'ai un doute
	@NotNull
	private String shortName;
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
	@UniqueElements
	private Long idApiExt;

	@OneToMany
	private List<Team> teamList;

	@OneToMany
	private List<Matches> matchesList;
}
