package com.eerichmond.core.data;

import com.eerichmond.core.domain.PagingOptions;
import com.eerichmond.core.domain.Person;
import com.eerichmond.core.security.Association;
import com.eerichmond.core.security.AssociationRole;
import com.eerichmond.core.security.PersonToAssociations;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.springframework.data.domain.Page;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class PersonRepositoryImplIT extends BaseIntegrationTest {

	@Inject
	PersonRepository repository;

	@Test
	public void findByLoginIds_loginId_returnsRecord() {
		String loginId = "hpotter";
		
		assertThat(repository.findByLoginId(loginId).getLoginId(), is(loginId));
	}

	@Test
	public void search_roleStudentNameOrIdPotte_returnsOneRecord() {
		String lastNameFragment = "Potte";

		PersonSearchCriteria criteria = new PersonSearchCriteria()
			.setRole(AssociationRole.STUDENT)
			.setNameOrId(lastNameFragment);

		Page<PersonToAssociations> results = repository.search(criteria);

		assertThat(results.getTotalElements(), is(1L));

		for (PersonToAssociations result : results.getContent()) {
			assertThat(result.getPerson().getLastName(), startsWith(lastNameFragment));
		}
	}

	@Test
	public void search_nameOrIdSearchOfPotterWithPageSizeOf2_returnsOnePage() {
		PersonSearchCriteria search = new PersonSearchCriteria()
			.setNameOrId("Potter");
		
		Page<PersonToAssociations> results = repository
			.search(search, new PagingOptions(0, 2));

		assertThat(results.getNumberOfElements(), is(1));
		assertThat(results.getNumber(), is(0));
		assertThat(results.getTotalElements(), is(1L));
		assertThat(results.getContent().size(), is(1));
	}

	@Test
	public void search_nameOrIdPotterCommaHarry_returnsRecord() throws IOException {
		Page<PersonToAssociations> results = repository
			.search(new PersonSearchCriteria().setNameOrId("Potter, Harry"));

		assertThat(results.getTotalElements(), is(1L));

		Person firstStudent = results.getContent().get(0).getPerson();
		assertThat(firstStudent.getFullName(), is("Potter, Harry"));
	}

	@Test
	public void search_nameOrIdCommaThenGranger_searchesByFirstName() {
		Page<PersonToAssociations> results = repository
			.search(new PersonSearchCriteria().setNameOrId(",Hermione"));

		assertThat(results.getTotalElements(), is(1L));

		Person firstStudent = results.getContent().get(0).getPerson();
		assertThat(firstStudent.getFirstName(), is("Hermione"));
	}

	@Test
	public void search_nameOrIdOfStudentId_returnsRecordsForStudentId() {
		Page<PersonToAssociations> results = repository
			.search(new PersonSearchCriteria().setNameOrId("123456789"));

		assertThat(results.getTotalElements(), is(1L));

		Person firstStudent = results.getContent().get(0).getPerson();
		assertThat(firstStudent.getStudentId(), is("123456789"));
	}

	@Test
	public void search_nameOrIdOfEmployeeId_returnsRecordsForEmployeeId() {
		Page<PersonToAssociations> results = repository
			.search(new PersonSearchCriteria().setNameOrId("987654321"));

		assertThat(results.getTotalElements(), is(1L));

		Person firstStudent = results.getContent().get(0).getPerson();
		assertThat(firstStudent.getEmployeeId(), is("987654321"));
	}

	@Test
	public void search_roleStudentForStudentEmployee_returnsOnlyStudentAssociations() {
		AssociationRole role = AssociationRole.STUDENT;

		PersonSearchCriteria criteria = new PersonSearchCriteria()
			.setRole(role)
			.setStudentId("123456789");

		// Act
		Page<PersonToAssociations> results = repository.search(criteria);

		// Assert
		assertThat(results.getTotalElements(), is(1L));

		Person person = results.getContent().get(0).getPerson();
		assertThat(person.getStudentId(), is("123456789"));

		Collection<Association> associations = results.getContent().get(0).getAssociations();
		assertThat(associations.size(), is(1));

		Association association = Iterables.getFirst(associations, null);
		assertThat(association.getRole().getCode(), is(role.getCode()));
	}

	@Test
	public void search_nameOrIdInvalidEmployeeId_returnsZeroRecords() {
		Page<PersonToAssociations> results = repository
			.search(new PersonSearchCriteria().setNameOrId("0"));

		assertThat(results.getTotalElements(), is(0L));
	}

}
