package com.eerichmond.core.data;

import com.eerichmond.core.domain.Person;
import com.eerichmond.core.security.Association;
import com.google.common.collect.Iterables;
import org.junit.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AssociationIT extends BaseIntegrationTest {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private PersonRepository personRepository;
	
	@Test
	public void find_role_returnsRecord() {
		Person person = personRepository.findByLoginId("hpotter");
		
		Association firstAssociation = Iterables.getFirst(person.getActiveAssociations(), null);
		Long id = firstAssociation.getId();
		
		em.detach(firstAssociation);
		em.detach(person);

        Association associationFromDb = em.find(Association.class, id);
		
		assertThat(associationFromDb, is(notNullValue()));
	}

}
