package com.eerichmond.core.security;

import com.eerichmond.core.domain.BaseObject;
import com.eerichmond.core.domain.BusinessKey;
import org.apache.commons.lang3.text.WordUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class RoleImpl extends BaseObject<BusinessKey> implements Role, Comparable<Role> {

	private static final long serialVersionUID = 1L;
	
	@BusinessKey
    @Column(name="role_code")
	private String code;

	/**
	 * Empty constructor for proxy/ORM/serialization libraries.
	 */
	public RoleImpl() { super(); }

	public RoleImpl(String code) { super(); this.code = code; }

	public RoleImpl(Role role) { this(role.getCode()); }

	@Override
	public String getCode() { return code; }

	@Override
	public String getDescription() { return WordUtils.capitalizeFully(code.replace("_", " ")); }

	@Override
	public int compareTo(Role other) {
		if (other == null || other.getCode() == null) {
			return 1;
		}
		
		if (this.equals(other)) {
			return 0;
		}
		
		if (getCode() == null) {
			return -1;
		}
		
		return getCode().compareTo(other.getCode());
	}
	
	@Override
	public String toString() { return getCode(); }

}
