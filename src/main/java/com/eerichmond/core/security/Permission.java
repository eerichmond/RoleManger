package com.eerichmond.core.security;

import com.eerichmond.core.domain.BaseObject;
import com.eerichmond.core.domain.BusinessKey;

public class Permission extends BaseObject<BusinessKey> {
	
	private static final long serialVersionUID = 1L;
	
	@BusinessKey
	private final PermissionType type;
	
	private final RoleResolver roleResolver;

	public Permission(PermissionType code, RoleResolver roleResolver) {
		super();
		
		this.type = code;
		this.roleResolver = roleResolver;
	}
	
	public String getCode() { return getType().getCode(); }
	
	public PermissionType getType() { return type; }

	/**
	 * The role resolver that evaluated to true for this permission.
	 */
	public RoleResolver getRoleResolver() { return roleResolver; }
	
	/**
	 * Returns the justification for the permission.
	 */
	public String getJustification() {
		return getRoleResolver().toString();
	}
	
}
