package com.eerichmond.core.data;

import com.eerichmond.core.domain.Person;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;

public interface PersonRepository extends CrudRepository<Person, Long>, PersonRepositoryCustom {
	
	/**
	 * Searches for a user by the login ID. Uses the default behavior of
	 * Spring-Data-JPA to infer the query from the method name.
	 * 
	 * We need the QueryHint FlushMode.COMMIT because this method is called by the
	 * AuditingEntityListener and with FlushMode.AUTO the AuditingEntityListener
	 * gets caught in an infinite loop.
	 * 
	 * Eg. Save of AuditableEntity -> Hibernate flushes -> calls AuditingEntityListener ->
	 * calls findByLoginIds -> triggers a flush -> calls AuditingEntityListener ->
	 * calls findByLoginIds -> triggers a flush -> ... 
	 * @param loginId the login ID to search for
	 */
	@QueryHints(@QueryHint(name="org.hibernate.flushMode", value="COMMIT"))
	Person findByLoginIds(String loginId);

	Person findByStudentId(String studentId);
	
}
