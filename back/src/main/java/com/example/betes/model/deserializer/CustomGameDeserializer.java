package com.example.betes.model.deserializer;

import com.example.betes.model.Game;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomGameDeserializer extends StdDeserializer<Game> {

	public CustomGameDeserializer() {
		this(null);
	}

	public CustomGameDeserializer(Class<Game> t) {
		super(t);
	}

	@Override
	public Game deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
		Game game = new Game();
		ObjectCodec codec = parser.getCodec();
		JsonNode node = codec.readTree(parser);

		// try catch block
		game.setName(node.get("name").asText());
		game.setNameApiExt(node.get("slug").asText());
//		game.setLogo(node.get("image_url").asText());
		game.setIdApiExt(node.get("id").asLong());

		return game;
	}

}
