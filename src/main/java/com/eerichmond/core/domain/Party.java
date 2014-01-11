package com.eerichmond.core.domain;

import com.eerichmond.core.codes.ActiveStatusCode;
import com.eerichmond.core.security.Association;
import com.eerichmond.core.security.Role;
import com.eerichmond.core.security.RoleExpression;
import com.eerichmond.core.security.RoleResolver;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance
@DiscriminatorColumn(name="party_type")
public abstract class Party extends AuditableEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty
	private Long id;
	
	@OneToMany(mappedBy="member")
	private Set<Association> associations = Sets.newHashSet();

	@JsonProperty
	public abstract String getName();
	
	/**
	 * Empty constructor for subclasses.
	 */
	public Party() { super(); }
	
	/**
	 * Constructor for testing. Does NOT load up the record.
	 * @param id the unique identifier for the party
	 */
	public Party(Long id) {
		super();
		this.id = id;
	}
	
	@Override
	public Long getId() { return id; }

	/**
	 * Returns the active associations.
	 */
	public Set<Association> getActiveAssociations() {
		Set<Association> activeAssociations = Sets.newHashSet();

		for(Association association : this.associations) {
			if (association.getActiveStatusCode() == ActiveStatusCode.ACTIVE) {
				activeAssociations.add(association);
			}
		}

		return activeAssociations;
	}

	public Party addAssociation(Role role, Organization... organizations) {
		for (Organization org : organizations) {
			this.associations.add(new Association(this, org, role));
		}

		return this;
	}

	/**
	 * Takes in a Role interface and returns the parties for any associations that match. Uses the getCode()
	 * method on the Role interface because role can be any number of implementations which are not necessarily equal.
	 * @param rolesToSearchFor the rolesToSearchFor to find the parties for
	 * @throws ClassCastException if the associated party doesn't match type T
	 * @return a set of parties cast as type T
	 */
	public <T extends Organization> Set<T> findAssociationsByRole(Role... rolesToSearchFor) {
		return findAssociationsByTypeAndRole(null, rolesToSearchFor);
	}

	/**
	 * Takes the return type class and a Role interface and returns the organizations for any associations that match. Uses
	 * the getCode() method on the Role interface because role can be any number of implementations which are not
	 * necessarily equal.
	 * @param returnType the return type T or null if the return type doesn't matter
	 * @param rolesToSearchFor the rolesToSearchFor to find the parties for
	 * @throws ClassCastException if the returnType is null and the matching parties don't match the return value
	 * @return a set of parties cast as type T
	 */
	@SuppressWarnings("unchecked")
	public <T extends Organization> Set<T> findAssociationsByTypeAndRole(Class<T> returnType, Role... rolesToSearchFor) {
		Preconditions.checkNotNull(rolesToSearchFor);

		Set<String> roleCodesToSearchFor = Sets.newHashSet();
		
		for (Role role : rolesToSearchFor) {
			roleCodesToSearchFor.add(role.getCode());
		}
		
		Set<T> parties = Sets.newHashSet();

		for (Association association : getActiveAssociations()) {
			String roleCode = association.getRole().getCode();

			if (roleCodesToSearchFor.contains(roleCode)) {
                Organization organization = association.getOrganization();

				// If a return type was specified make sure the parent party matches the class of the return value
				// otherwise return any matching parties.
				if ( returnType == null || returnType.isAssignableFrom(organization.getClass()) ) {
					T org = (T) organization;

					parties.add(org);
				}
			}
		}
		
		return parties;
	}
	
	/**
	 * Returns true if this person's role associations match the specified
	 * role expression.
	 * @param roleExpression the role expression to evaluate.
	 * @return a role resolver to evaluate.
	 */
	public RoleResolver isA(RoleExpression roleExpression) {
		return new RoleResolver(getActiveAssociations(), roleExpression);
	}
	
}
