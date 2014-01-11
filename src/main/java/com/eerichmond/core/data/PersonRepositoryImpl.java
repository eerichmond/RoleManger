package com.eerichmond.core.data;

import com.eerichmond.core.domain.PagingOptions;
import com.eerichmond.core.domain.Person;
import com.eerichmond.core.security.Association;
import com.eerichmond.core.security.PersonToAssociations;
import com.google.common.collect.Lists;
import com.mysema.query.Tuple;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

	private static final Logger LOG = LoggerFactory.getLogger(PersonRepositoryImpl.class);

	@PersistenceContext
    private EntityManager em;

	@Override
	public Page<PersonToAssociations> search(PersonSearchCriteria criteria) {
		return search(criteria, null);
	}

	@Override
	public Page<PersonToAssociations> search(PersonSearchCriteria criteria, PagingOptions pagingOptions) {
		DateTime start = DateTime.now();

		PersonQueryBuilder queryBuilder = new PersonQueryBuilder(criteria);

		List<Tuple> records = queryBuilder
			.query(em)
			.list(PersonQueryBuilder.Q_PERSON, PersonQueryBuilder.ORG_ASSOCIATION);

		LOG.debug("Retrieve records took {} ms", new Duration(start, DateTime.now()).getMillis());
		start = DateTime.now();

		return createPage(pagingOptions, records);
	}

	/**
	 * Converts a list of objects and the pagingOptions specifications into paged collection. Package level security to allow
	 * for testing.
	 * @param pagingOptions the pagingOptions specifications.
	 * @param records a list to turn into a paged collection.
	 * @return a paged collection.
	 */
	Page<PersonToAssociations> createPage(PagingOptions pagingOptions, List<Tuple> records) {
		DateTime start = DateTime.now();

		List<PersonToAssociations> people = Lists.newArrayList();

		Person prevPerson = null;
		PersonToAssociations personToAssociations = null;
		int personCounter = 0;

		for (Tuple record : records) {
			Person person = record.get(0, Person.class);

			if (!person.equals(prevPerson)) {
				personCounter++;
			}

			// Do in memory pagingOptions since it is slower to do pagingOptions on the server side as a person could have multiple
			// associations which skews the rownum.
			if (pagingOptions == null || (personCounter - 1 >= pagingOptions.getStartRow() && personCounter - 1 < (pagingOptions.getEndRow()))) {
				if (!person.equals(prevPerson)) {
					personToAssociations = new PersonToAssociations(person);

					people.add(personToAssociations);
				}

				personToAssociations.getAssociations().add(record.get(1, Association.class));
			}

			prevPerson = person;
		}

		LOG.debug("Constructing list took {} ms", new Duration(start, DateTime.now()).getMillis());

		Pageable pageable = pagingOptions != null ? pagingOptions.toPageable() : null;

		return new PageImpl<>(people, pageable, personCounter);
	}

}
