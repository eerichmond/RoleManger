package com.eerichmond.core.utils;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Component;

import javax.xml.bind.DataBindingException;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Set;

@Component
public class JsonMapper extends ObjectMapper {

	public JsonMapper() {
		super();

		registerModule(new JodaModule());
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
	}

	public void setModules(Set<Module> modules) {
		for (Module module : modules) {
			registerModule(module);
		}
	}

	public String toJson(Object value) {
		StringWriter writer = new StringWriter();

		try {
			writeValue(writer, value);
		} catch (IOException e) {
			Throwables.propagate(e);
		}

		return writer.toString();
	}

	public <T extends JsonViewType.Basic> String toJson(Object item, Class<T> clazz) {
		StringWriter sw = new StringWriter();

		try {
			writerWithView(clazz).writeValue(sw, item);
		} catch (Exception e) {
			throw new DataBindingException(e);
		}

		return sw.toString();
	}
}
