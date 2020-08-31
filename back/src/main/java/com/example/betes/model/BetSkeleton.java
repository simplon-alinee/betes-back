package com.example.betes.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BetSkeleton {
	private Long userId;
	private Long matchId;
	private Long betOnTeamId;
}
