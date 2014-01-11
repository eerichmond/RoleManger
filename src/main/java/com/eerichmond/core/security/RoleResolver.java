package com.eerichmond.core.security;

import com.eerichmond.core.domain.BaseObject;
import com.eerichmond.core.domain.BusinessKey;
import com.google.common.base.Preconditions;

import java.util.Set;

public class RoleResolver extends BaseObject<BusinessKey> {
	
	private static final long serialVersionUID = 1L;
	
	@BusinessKey
	private final Set<Association> associations;
	
	@BusinessKey
	private final RoleExpression roleExpression;

	public RoleResolver(Set<Association> associations, RoleExpression roleExpression) {
		super();
		Preconditions.checkNotNull(associations);
		Preconditions.checkNotNull(roleExpression);
		
		this.associations = associations;
		this.roleExpression = roleExpression;
	}
	
	public boolean isTrue() {
		return this.roleExpression.matches(this.associations);
	}
	
}
