package com.eerichmond.core.data;

import com.eerichmond.core.domain.BaseObject;
import com.eerichmond.core.domain.BusinessKey;
import com.eerichmond.core.domain.HierarchyLevel;
import com.eerichmond.core.domain.Organization;
import com.eerichmond.core.security.AssociationRole;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;

public class PersonSearchCriteria extends BaseObject<BusinessKey> {
	
	private static final long serialVersionUID = 1L;

	private String employeeId;
	private String studentId;
	private String firstName;
	private String lastName;
	private AssociationRole role;
	private Set<Organization> organizations = Sets.newHashSet();
	
	// If you don't know if it is a first or last name use the name fragment
	private String nameFragment;

	@BusinessKey
	public String getEmployeeId() { return employeeId; }
	public PersonSearchCriteria setEmployeeId(String employeeId) {
		if (employeeId != null){
			employeeId = employeeId.replaceAll("[^\\d]", "");

			if ("".equals(employeeId)) {
				employeeId = null;
			}
		}

		this.employeeId = employeeId;

		return this;
	}
		
	@BusinessKey
	public String getStudentId() { return studentId; }
	public PersonSearchCriteria setStudentId(String studentId) {
		if (studentId != null){
			studentId = studentId.replaceAll("[^\\d]", "");

			if ("".equals(studentId)) {
				studentId = null;
			}
		}

		this.studentId = studentId;

		return this;
	}
	
	@BusinessKey
	public String getLastName() { return lastName; }
	public PersonSearchCriteria setLastName(String lastName) { this.lastName = lastName; return this; }
	
	@BusinessKey
	public String getFirstName() { return firstName; }
	public PersonSearchCriteria setFirstName(String firstName) { this.firstName = firstName; return this; }
	
	/**
	 * If you don't know if it is a first or last name use the name fragment
	 */
	@BusinessKey
	public String getNameFragment() { return nameFragment; }
	public PersonSearchCriteria setNameFragment(String nameFragment) { this.nameFragment = nameFragment; return this; }
	
	public String getFullName() {
		if (getLastName() != null && getFirstName() != null) {
			return String.format("%s,%s", getLastName(), getFirstName());
		}
		else if (getNameFragment() != null) {
			return getNameFragment();
		}
		else if (getLastName() != null) {
			return getLastName();
		}
		else if (getFirstName() != null) {
			return getFirstName();
		}
		
		return null;
	}
	
	/**
	 * Parses the full name into first and last name in the formats "First Last" or
	 * "Last,First"
	 * @param fullName the full name to parse into first and last name
	 * @return itself for fluent programming
	 */
	public PersonSearchCriteria setFullName(String fullName) {
		fullName = fullName.trim();

		if (fullName.contains(",")) {
			String[] nameParts = fullName.split(",");

			if (nameParts.length > 1) {
				String firstName = nameParts[nameParts.length - 1];

				setFirstName(firstName.trim());

				setLastName(fullName.substring(0, fullName.length() - firstName.length() - 1).trim());
			}
			else {
				setFirstName("");
				setLastName(nameParts[0].trim());
			}
		}
		else if (fullName.contains(" ")) {
			setFirstName(fullName.substring(0, fullName.lastIndexOf(" ")));
			setLastName(fullName.substring(fullName.lastIndexOf(" ") + 1));
		}
		else {
			setLastName(fullName);
		}
		
		return this;
	}
	
	/**
	 * Parses the greedy search text into full name, student ID or employee ID.
	 * @param searchText the search text to parse
	 * @return itself for fluent programming
	 */
	public PersonSearchCriteria setNameOrId(String searchText) {
		Preconditions.checkNotNull(searchText);

		searchText = searchText.trim();

		// If the greedy search looks like a 9 digit number search by student ID or employee ID
		if (searchText.matches("^\\d{9}$")) {
			setStudentId(searchText);
			setEmployeeId(searchText);
		}
		else {
			// Check that the text contains at least one letter
			Preconditions.checkArgument(searchText.matches(".*\\w+.*"));

			this.setFullName(searchText);
		}
		
		return this;
	}

	@BusinessKey
	public AssociationRole getRole() { return role; }
	public PersonSearchCriteria setRole(AssociationRole role) { this.role = role; return this; }

	@BusinessKey
	public Set<Organization> getOrganizations() { return this.organizations; }
	public void setOrganizations(Set<Organization> organizations) { this.organizations = organizations; }
	public PersonSearchCriteria addOrganization(Organization... organization) { this.organizations.addAll(Arrays.asList(organization)); return this; };

	/**
	 * Returns the highest hierarchy level of all the associated organizations. This helps to optimize the search
	 * queries.
	 */
	public HierarchyLevel highestOrgHierarchyLevel() {
		HierarchyLevel hierarchyLevel = HierarchyLevel.LEAF;

		for (Organization org : this.organizations) {
			if (org.getHierarchyLevel().ordinal() > hierarchyLevel.ordinal()) {
				hierarchyLevel = org.getHierarchyLevel();
			}
		}

		return hierarchyLevel;
	}

	public String makeSearchable(String name) {
		if (name == null) { return null; }
		
		return name.replaceAll("[^\\w]+", "").toUpperCase();
	}

}
