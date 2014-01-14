package com.eerichmond.core.data;

import com.eerichmond.core.domain.Department;
import com.eerichmond.core.domain.PagingOptions;
import com.eerichmond.core.domain.Person;
import com.eerichmond.core.security.Association;
import com.eerichmond.core.security.AssociationRole;
import com.eerichmond.core.security.PersonToAssociations;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mysema.query.Tuple;
import org.junit.Test;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PersonRepositoryImplTest {

	private Long uid = 1L;
	private PersonRepositoryImpl repository = new PersonRepositoryImpl();

	@Test
	public void createPage_oneRecord_returnsRecordWithDept() {
		PagingOptions paging = new PagingOptions(0, 5);
		List<Tuple> records = Lists.newArrayList();

		Person person = new Person(1L, "One", "One");
		addAssociation(records, person, "Dept");

		Page<PersonToAssociations> page = repository.createPage(paging, records);

		List<PersonToAssociations> results = page.getContent();

		assertThat(results.size(), is(1));

		assertThat(results.get(0).getPerson(), is(person));
		assertThat(results.get(0).getAssociations().size(), is(1));

		Department department = Iterables.getFirst(results.get(0).getAssociations(), null).getOrganization();
		assertThat(department.getName(), is("Dept"));
	}

	@Test
	public void createPage_rowsPerPage2_startRow2_returnsRecords3to4() {
		PagingOptions paging = new PagingOptions(2,2);
		List<Tuple> records = Lists.newArrayList();

		Person person1 = new Person(1L, "One", "One");
		Person person2 = new Person(2L, "Two", "Two");
		Person person3 = new Person(3L, "Three", "Three");
		Person person4 = new Person(4L, "Four", "Four");

		addAssociation(records, person1, "HOUSE1");

		// 2 majors
		addAssociation(records, person2, "HOUSE1");
		addAssociation(records, person2, "HOUSE2");

		// 3 majors
		addAssociation(records, person3, "HOUSE1");
		addAssociation(records, person3, "HOUSE2");
		addAssociation(records, person3, "HOUSE3");

		addAssociation(records, person4, "HOUSE1");

		Page<PersonToAssociations> page = repository.createPage(paging, records);

		assertThat(page.getTotalElements(), is(4L));

		List<PersonToAssociations> results = page.getContent();

		assertThat(results.size(), is(2));
		assertThat(results.get(0).getPerson(), is(person3));
		assertThat(results.get(1).getPerson(), is(person4));
	}

	private void addAssociation(List<Tuple> records, Person person, String deptCode) {
        Department dept = new Department(deptCode);

		person.addAssociation(AssociationRole.STUDENT, dept);
		Association association = Iterables.getFirst(person.getActiveAssociations(), null);

		records.add(new TupleImpl(person, association));
	}
}
