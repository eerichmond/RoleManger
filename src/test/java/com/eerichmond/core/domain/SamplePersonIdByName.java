package com.eerichmond.core.domain;

public class SamplePersonIdByName extends BaseObject<BusinessKey> {
	
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	
	public SamplePersonIdByName(String firstName, String lastName) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@BusinessKey
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	@BusinessKey
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
}