package com.eerichmond.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl extends User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String middleName;
	private String fullName;
	private String friendlyName;
	private String email;

	public UserDetailsImpl(String username, Collection<? extends GrantedAuthority> authorities) {
		super(username, "", authorities);
	}

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getMiddleName() { return middleName; }
	public void setMiddleName(String middleName) { this.middleName = middleName; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }

	public String getFriendlyName() { return friendlyName; }
	public void setFriendlyName(String friendlyName) { this.friendlyName = friendlyName; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() { return super.getAuthorities(); }
	
	@Override
	public String toString() { return String.format("%s ( %s - %s )", getFullName(), getUsername(), getEmail()); }
	
}
