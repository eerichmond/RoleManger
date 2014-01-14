package com.eerichmond.core.data;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class PersonSearchCriteriaTest {

	@Test
	public void setFullName_noComma_returnsLastNameOnly() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setFullName("Potter");
		
		assertEquals("Potter", criteria.getLastName());
		assertEquals("Potter", criteria.getFullName());
		assertThat(criteria.getFirstName(), is(nullValue()));
		assertThat(criteria.getNameFragment(), is(nullValue()));
	}

	@Test
	public void setStudentId_null_returnsNull() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		criteria.setStudentId(null);

		assertThat(criteria.getStudentId(), is(nullValue()));
	}

	@Test
	public void setStudentId_empty_returnsNull() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		criteria.setStudentId("");

		assertThat(criteria.getStudentId(), is(nullValue()));
	}

	@Test
	public void setStudentId_nonDigits_returnsNull() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		criteria.setStudentId(" adfd ");

		assertThat(criteria.getStudentId(), is(nullValue()));
	}

	@Test
	public void setStudentId_extraWhiteSpace_returnsJustDigits() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		criteria.setStudentId(" 1234 ");

		assertThat(criteria.getStudentId(), is("1234"));
	}

	@Test
	public void setFullName_lastNameAndFirst_returnsFirstAndLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setFullName("Potter, Harry");
		
		assertEquals("Harry", criteria.getFirstName());
		assertEquals("Potter", criteria.getLastName());
	}
	
	@Test
	public void setFullName_emptyLastNameAndFirstName_returnsFirstNameAndEmptyLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setFullName(",Harry");
		
		assertEquals("Harry", criteria.getFirstName());
		assertEquals("", criteria.getLastName());
	}
	
	@Test
	public void setFullName_lastNameAndEmptyFirstName_returnsLastNameAndEmptyFirstName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setFullName("Potter,");
		
		assertEquals("", criteria.getFirstName());
		assertEquals("Potter", criteria.getLastName());
	}
	
	@Test
	public void setFullName_lastFirstAndMiddleName_returnsFirstNameAndLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setFullName("Potter,Harry James");
		
		assertEquals("Harry James", criteria.getFirstName());
		assertEquals("Potter", criteria.getLastName());
	}
	
	@Test
	public void setFullName_doubleLastNameAndFirstName_returnsFirstNameAndLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setFullName("James Potter,Harry");
		
		assertEquals("Harry", criteria.getFirstName());
		assertEquals("James Potter", criteria.getLastName());
	}
	
	@Test
	public void setFullName_lastNameCommaMiddleNameCommaFirstName_returnsMiddleNameWithLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setFullName("Potter,Harry,James");
		
		assertEquals("James", criteria.getFirstName());
		assertEquals("Potter,Harry", criteria.getLastName());
	}

	@Test
	public void setFullName_firstSpaceLast_returnsFirstAndLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();

		criteria.setFullName("Harry Potter");

		assertEquals("Harry", criteria.getFirstName());
		assertEquals("Potter", criteria.getLastName());
	}

	@Test
	public void setFullName_firstSpaceMiddleSpaceLast_returnsMiddleNameWithFirstName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();

		criteria.setFullName("Harry James Potter");

		assertEquals("Harry James", criteria.getFirstName());
		assertEquals("Potter", criteria.getLastName());
	}

	@Test
	public void setNameOrId_lessThen9DigitNumber_returnsNullStudentIdAndEmployeeId() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setNameOrId("12345678");

		assertThat(criteria.getStudentId(), is(nullValue()));
		assertThat(criteria.getEmployeeId(), is(nullValue()));
        assertThat(criteria.getLastName(), is(notNullValue()));
	}
	
	@Test
	public void setNameOrId_9DigitNumber_returnsNullPidmAndStudentIdAndEmployeeId() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setNameOrId("123456789");
		
		assertThat(criteria.getFullName(), is(nullValue()));
		assertEquals("123456789", criteria.getStudentId());
		assertEquals("123456789", criteria.getEmployeeId());
	}

	@Test
	public void setNameOrId_9DigitNumberWithWhiteSpace_returnsNullPidmAndStudentIdAndEmployeeId() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();

		criteria.setNameOrId(" 123456789 ");

		assertThat(criteria.getFullName(), is(nullValue()));
		assertEquals("123456789", criteria.getStudentId());
		assertEquals("123456789", criteria.getEmployeeId());
	}
	
	@Test
	public void setNameOrId_lettersOnly_returnsNullStudentIdAndFullName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();
		
		criteria.setNameOrId("Potter");
		
		assertEquals("Potter", criteria.getFullName());
		assertThat(criteria.getStudentId(), is(nullValue()));
	}

	@Test
	public void setNameOrId_lastCommaFirst_returnsFirstAndLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();

		criteria.setNameOrId("Potter,Harry");

		assertEquals("Potter", criteria.getLastName());
		assertEquals("Harry", criteria.getFirstName());
	}

	@Test
	public void setNameOrId_firstSpaceLastNameWithApostrophe_returnsFirstAndLastName() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();

		criteria.setNameOrId("Harry O'Potter");

		assertEquals("Harry", criteria.getFirstName());
		assertEquals("O'Potter", criteria.getLastName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNameOrId_invalidCharacters_throwsException() {
		PersonSearchCriteria criteria = new PersonSearchCriteria();

		criteria.setNameOrId("',");
	}
}
