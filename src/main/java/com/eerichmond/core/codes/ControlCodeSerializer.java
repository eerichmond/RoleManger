package com.eerichmond.core.codes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ControlCodeSerializer extends JsonSerializer<ControlCode> {

	@Override
	public void serialize(ControlCode value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("code");
		generator.writeString(value.getCode());
		generator.writeFieldName("description");
		generator.writeString(value.getDescription());
		generator.writeEndObject();
	}

}
