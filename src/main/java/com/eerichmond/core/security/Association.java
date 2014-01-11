package com.eerichmond.core.security;

import com.eerichmond.core.codes.ActiveStatusCode;
import com.eerichmond.core.domain.AuditableEntity;
import com.eerichmond.core.domain.Party;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name="PARTY_TO_ROLE")
public class Association extends AuditableEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "PARTY_TO_ROLE_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
		name = "PARTY_TO_ROLE_SEQ",
		strategy = "sequence",
		parameters = @Parameter(name="sequence", value="PARTY_TO_ROLE_SEQ")
	)
	private Long id;
	
	@ManyToOne @JoinColumn(name="CHILD_PARTY_ID")
	private Party childParty;
	
	@ManyToOne @JoinColumn(name="PARENT_PARTY_ID")
	private Party parentParty;
	
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
	 * @param childParty the child party in the association
	 * @param parentParty the parent party in the association
	 * @param role the role of the association
	 */
	public Association(Party childParty, Party parentParty, Role role) {
		super();
		
		Preconditions.checkNotNull(childParty);
		Preconditions.checkNotNull(parentParty);
		Preconditions.checkNotNull(role);

		this.id = (long) (childParty.hashCode() + parentParty.hashCode() + role.hashCode());
		this.childParty = childParty;
		this.parentParty = parentParty;
		this.role = new RoleImpl(role);
	}
	
	@Override
	@JsonProperty
	public Long getId() { return id; }

	@SuppressWarnings("unchecked")
	public <T extends Party> T getChildParty() { return (T)childParty; }
	public void setChildParty(Party childParty) { this.childParty = childParty; }

	@JsonProperty
	@SuppressWarnings("unchecked")
	public <T extends Party> T getParentParty() { return (T)parentParty; }
	public void setParentParty(Party parentParty) { this.parentParty = parentParty; }

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
		
		if (getParentParty() != null) {
			builder.append(" of ")
				.append(getParentParty().getName());
		}
		
		return builder.toString();
	}
	
}
