package com.eerichmond.core.security;

import com.eerichmond.core.domain.Department;
import com.eerichmond.core.domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PersonToAssociationsTest {

	@Test
	public void jsonSerialize_serializesPersonAndAssociations() throws IOException {
		Person person = new Person(1L, "John", "Doe");
		person.addAssociation(AssociationRole.EMPLOYEE, new Department("DEPT"));
		PersonToAssociations personToAssociations = new PersonToAssociations(person, person.getActiveAssociations());

		StringWriter writer = new StringWriter();

		new ObjectMapper().writeValue(writer, personToAssociations);

		String json = writer.toString();

		assertThat(JsonPath.<String>read(json, "$.person"), is(notNullValue()));
		assertThat(JsonPath.<String>read(json, "$.associations[0]"), is(notNullValue()));
	}

}
