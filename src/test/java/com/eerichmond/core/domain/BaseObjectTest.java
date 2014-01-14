package com.eerichmond.core.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class BaseObjectTest {

	@Test
	public void equals_personNullIdPersonNullId_returnsFalse() {
		SamplePerson person1 = new SamplePerson();

		SamplePerson person2 = new SamplePerson();

		assertThat(person1, is(not(person2)));
	}

	@Test
	public void equals_person1Person1_returnsTrue() {
		SamplePerson person1 = new SamplePerson("1");
		
		SamplePerson person2 = new SamplePerson("1");
		
		assertThat(person1, is(person2));
	}

	@Test
	public void equals_personId1UsernameJohnPersonId1UsernameJane_returnsFalse() {
		SamplePerson person1 = new SamplePerson("1", "John");

		SamplePerson person2 = new SamplePerson("1", "Jane");

		assertThat(person1, is(not(person2)));
	}

	@Test
	public void equals_personId1UsernameJohnPersonId1UsernameJohn_returnsTrue() {
		SamplePerson person1 = new SamplePerson("1", "John");

		SamplePerson person2 = new SamplePerson("1", "John");

		assertThat(person1, is(person2));
	}
	
	@Test
	public void equals_person1Person2_returnsFalse() {
		SamplePerson person1 = new SamplePerson("1");
		
		SamplePerson person2 = new SamplePerson("2");
		
		assertThat(person1, is(not(person2)));
	}
	
	@Test
	public void equals_samplePerson1SamplePerson1_returnsTrue() {
		SamplePerson person1 = new SamplePerson("1");
		
		SamplePerson person2 = new SamplePerson("1");
		
		assertThat(person1, is(person2));
	}

	@Test
	public void equals_samplePerson1SamplePerson2_returnsFalse() {
		SamplePerson person1 = new SamplePerson("1");
		
		SamplePerson person2 = new SamplePerson("2");
		
		assertThat(person1, is(not(person2)));
	}

	@Test
	public void toString_businessKeysOnGettesr_returnsFieldNamesEqualsValues() {
		SamplePersonIdByName person = new SamplePersonIdByName("John", "Doe");
		
		assertThat(person.toString(), is("SamplePersonIdByName[firstName=John,lastName=Doe]"));
	}
	
}