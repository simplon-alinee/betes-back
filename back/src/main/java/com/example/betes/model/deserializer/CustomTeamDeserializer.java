package com.example.betes.model.deserializer;

import com.example.betes.model.Game;
import com.example.betes.model.Team;
import com.example.betes.repository.GameRepository;
import com.example.betes.service.GameService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

public class CustomTeamDeserializer extends StdDeserializer<Team> {
	public CustomTeamDeserializer() {
		this(null);
		//	@Autowired
	}

	public CustomTeamDeserializer(Class<Team> t) {
		super(t);
	}


	@Override
	public Team deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
		GameService gameService = new GameService();
		Team team = new Team();
		ObjectCodec codec = parser.getCodec();
		JsonNode node = codec.readTree(parser);


		// try catch block
//		team.setName(node.get("name").asText());
//		team.setNameApiExt(node.get("slug").asText());
////		game.setLogo(node.get("image_url").asText());
//		team.setIdApiExt(node.get("id").asLong());

		Game gameTemp = new Game();
//		System.out.println(gameT);
		String test = node.get("acronym").asText();
//		Long idGame = ;
		gameTemp.setIdApiExt(node.get("current_videogame").get("id").asLong());
		team.setGame(gameTemp);
		team.setIdApiExt(node.get("id").asLong());
		team.setLogo(node.get("image_url").asText());
		team.setTeamName(node.get("name").asText());
//		Game game = gameRepository.findGameByIdApiExtEquals(id);
//		team.setGame(this.gameRepository.findByIdApiExt(Long.parseLong(node.get("current_videogame").get("id")) ));
		System.out.println(team);
		System.out.println('t');
		return team;
	}

}
