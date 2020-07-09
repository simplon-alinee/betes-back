package com.example.betes.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class DataLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Date lastModifDate;

	private String comments;
	// UPDATE, ADD, DELETE, something short
	private String goal;
	// SUCCESS FAILURE FORFEIT
	private String result;

}
