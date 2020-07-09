package com.example.betes.model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
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
	@Column(name = "ID_GAME")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long idGame;

	@Column(name = "NAME")
	@NotNull
	private String name;
// est-ce utile? j'ai un doute
	@Column(name = "SHORT_NAME")
	@NotNull
	private String shortName;
	/**
	 * correspond au slug utilisé par l'api pandascore
	 */
	@Column(name = "NAME_API_EXT")
	@NotNull
	private String nameApiExt;
	/**
	 * doit être ajouté manuellement
	 */
	@Column(name = "LOGO")
	private String logo;

	@Column(name = "ID_API_EXT", unique = true)
	@NotNull
	@UniqueElements
	private Long idApiExt;
}
