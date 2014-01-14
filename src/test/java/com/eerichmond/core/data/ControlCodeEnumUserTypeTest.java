package com.eerichmond.core.data;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ControlCodeEnumUserTypeTest {

	private ControlCodeEnumUserType userType;
	
	@Before
	public void setUp() {
		this.userType = new ControlCodeEnumUserType();
	}
	
	@Test
	public void equals_null_null_returnsTrue() {
		assertThat(userType.equals(null, null), is(true));
	}
	
	@Test
	public void equals_null_notNull_returnsFalse() {
		assertThat(userType.equals(null, new Object()), is(false));
	}
	
	@Test
	public void equals_notNull_null_returnsFalse() {
		assertThat(userType.equals(new Object(), null), is(false));
	}
	
	@Test
	public void equals_sameObject_returnsTrue() {
		Object obj = new Object();
		
		assertThat(userType.equals(obj, obj), is(true));
	}
	
	/**
	 * Hibernate needs to use reference equals not equality.
	 */
	@Test
	public void equals_differentObjectButEqual_returnsFalse() {
		SamplePerson person1 = new SamplePerson(1);
		SamplePerson person1Also = new SamplePerson(1);
		
		assertEquals(person1, person1Also);
		assertThat(userType.equals(person1, person1Also), is(false));
	}

}
