package com.eerichmond.core.security;

import com.eerichmond.core.codes.ActiveStatusCode;
import com.eerichmond.core.domain.AuditableEntity;
import com.eerichmond.core.domain.Organization;
import com.eerichmond.core.domain.Party;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class Association extends AuditableEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "ASSOCIATION_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
		name = "ASSOCIATION_SEQ",
		strategy = "sequence",
		parameters = @Parameter(name="sequence", value="ASSOCIATION_SEQ")
	)
	private Long id;
	
	@ManyToOne
	private Party member;
	
	@ManyToOne
	private Organization organization;
	
	@Embedded
	private RoleImpl role;
	
	private DateTime startDate;
	
	private DateTime endDate;
	
	@Enumerated(EnumType.STRING)
	private ActiveStatusCode activeStatusCode = ActiveStatusCode.ACTIVE;

	private String comments;
	
	/**
	 * Empty constructor for proxy/ORM/serialization libraries.
	 */
	public Association() { super(); }
	
	/**
	 * Constructor for testing. Does not load up the record.
	 * @param member the member of the association
	 * @param organization the organization that member is associated with
	 * @param role the role of the association
	 */
	public Association(Party member, Organization organization, Role role) {
		super();
		
		Preconditions.checkNotNull(member);
		Preconditions.checkNotNull(organization);
		Preconditions.checkNotNull(role);

		this.id = (long) (member.hashCode() + organization.hashCode() + role.hashCode());
		this.member = member;
		this.organization = organization;
		this.role = new RoleImpl(role);
	}
	
	@Override
	@JsonProperty
	public Long getId() { return id; }

	@SuppressWarnings("unchecked")
	public <T extends Party> T getMember() { return (T) member; }
	public void setMember(Party member) { this.member = member; }

	@JsonProperty
	@SuppressWarnings("unchecked")
	public <T extends Organization> T getOrganization() { return (T) organization; }
	public void setOrganization(Organization organization) { this.organization = organization; }

	@JsonProperty
	public Role getRole() { return role; }

	public DateTime getStartDate() { return startDate; }
	public DateTime getEndDate() { return endDate; }
	public ActiveStatusCode getActiveStatusCode() { return activeStatusCode; }
	public void setActiveStatusCode(ActiveStatusCode activeStatusCode) { this.activeStatusCode = activeStatusCode; }
	public String getComments() { return comments; }

	@Override
	public String toString() {
		if (this.role == null) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder
			.append(getRole().getDescription().substring(0,1).toUpperCase())
			.append(getRole().getDescription().substring(1).toLowerCase());
		
		if (getOrganization() != null) {
			builder.append(" of ")
				.append(getOrganization().getName());
		}
		
		return builder.toString();
	}
	
}
