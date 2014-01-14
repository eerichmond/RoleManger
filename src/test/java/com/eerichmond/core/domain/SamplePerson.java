package com.eerichmond.core.domain;


import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;

public class SamplePerson extends BaseObject<BusinessKey> {

	private static final long serialVersionUID = 1L;
	private String userId;
	private Set<String> usernames = Sets.newHashSet();
	
	public SamplePerson() { super(); }
	
	public SamplePerson(String userId) {
		super();
		this.userId = userId;
	}

	public SamplePerson(String userId, String... usernames) {
		this.userId = userId;
		this.usernames.addAll(Arrays.asList(usernames));
	}

	@BusinessKey
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	@BusinessKey
	public Set<String> getUsernames() { return usernames; }
}
