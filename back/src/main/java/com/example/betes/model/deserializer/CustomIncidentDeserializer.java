package com.example.betes.model.deserializer;

import com.example.betes.model.Game;
import com.example.betes.model.Incident;
import com.example.betes.model.Team;
import com.example.betes.service.GameService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomIncidentDeserializer extends StdDeserializer<Incident> {
	public CustomIncidentDeserializer() {
		this(null);
		//	@Autowired
	}

	public CustomIncidentDeserializer(Class<Incident> t) {
		super(t);
	}


	@Override
	public Incident deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
		Incident incident = new Incident();

		GameService gameService = new GameService();

		ObjectCodec codec = parser.getCodec();
		JsonNode node = codec.readTree(parser);
		String type = node.get("type").asText();

		incident.setType(type);
		incident.setChange(node.get("change_type").asText());

		node = node.get("object");
		switch(type)
		{
			case "team":
				Game gameTemp = new Game();
				Team teamTemp = new Team();
				gameTemp.setIdApiExt(node.get("current_videogame").get("id").asLong());
				teamTemp.setGame(gameTemp);
				teamTemp.setIdApiExt(node.get("id").asLong());
				teamTemp.setLogo(node.get("image_url").asText());
				teamTemp.setTeamName(node.get("name").asText());
				System.out.println(incident);
				incident.setTeam(teamTemp);
				break;
			case "game":

				Game game = new Game();
				game.setName(node.get("name").asText());
				game.setNameApiExt(node.get("slug").asText());
				game.setIdApiExt(node.get("id").asLong());
				break;
			case "match":
				System.out.println("match");
				break;
			default:
				System.out.println("autre");
		}
		return incident;
	}

}
