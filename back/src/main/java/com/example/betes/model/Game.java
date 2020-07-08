package com.example.betes.model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

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
public class Game {

	@Id
	@Column(name = "ID_GAME")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long idGame;

	@Column(name = "NAME")
	@NotNull
	private String name;

	@Column(name = "SHORT_NAME")
	@NotNull
	private String shortName;

	@Column(name = "NAME_API_EXT")
	@NotNull
	private String nameApiExt;

	@Column(name = "LOGO")
	@Lob
	private Byte[] logo;

	@Column(name = "ID_API_EXT", unique = true)
	@NotNull
	@UniqueElements
	private Long idApiExt;
}
