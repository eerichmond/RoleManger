package com.eerichmond.core.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Entity
@Cacheable
public class Person extends Party {

	private static final long serialVersionUID = 1L;

	private String studentId;
	
	private String employeeId;

    @NotBlank
	private String firstName;

	private String middleName;

    @NotBlank
	private String lastName;

    @NotBlank
	private String firstNameSearchable;

    @NotBlank
	private String lastNameSearchable;

	private String loginId;

	private String email;
	
	/**
	 * Empty constructor for proxy/ORM libraries.
	 */
	public Person() { super(); }
	
	/**
	 * Constructor for testing. Does NOT load up data.
	 * @param id the unique ID for the person
	 * @param firstName	first name
	 * @param lastName last name
	 */
	public Person(Long id, String firstName, String lastName) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Constructor for testing. Does NOT load up data.
	 * @param firstName	first name
	 * @param lastName last name
	 */
	public Person(String firstName, String lastName) {
		this(firstName, null, lastName);
	}
	
	/**
	 * Constructor for testing. Does NOT load up data.
	 * @param firstName	first name
	 * @param middleName middle name
	 * @param lastName last name
	 */
	public Person(String firstName, String middleName, String lastName) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}
	
	public String getStudentId() { return studentId; }
	void setStudentId(String studentId) { this.studentId = studentId; }
	
	public String getEmployeeId() { return employeeId; }
	void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
	
	public String getFirstName() { return firstName; }
	void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getMiddleName() { return middleName; }
	void setMiddleName(String middleName) { this.middleName = middleName; }

	public String getLastName() { return lastName; }
	void setLastName(String lastName) { this.lastName = lastName; }

	/**
	 * The student's full name in the format: last, first middle
	 */
	public String getFullName() {
		return isEmpty(getMiddleName()) ? String.format("%s, %s", getLastName(), getFirstName()) :
			String.format("%s, %s %s", getLastName(), getFirstName(), getMiddleName());
	}

	/**
	 * The student's name in a friendly format: first last
	 */
	public String getFriendlyName() { return String.format("%s %s", getFirstName(), getLastName()); }

	/**
	 * Non-public first name property used to improve the search speed by
	 * hitting the Banner optimized first name field.
	 */
	String getFirstNameSearchable() { return firstNameSearchable; }
	void setFirstNameSearchable(String firstNameSearchable) { this.firstNameSearchable = firstNameSearchable; }

	/**
	 * Non-public last name property used to improve the search speed by
	 * hitting the Banner optimized last name field.
	 */
	String getLastNameSearchable() { return lastNameSearchable; }
	void setLastNameSearchable(String lastNameSearchable) { this.lastNameSearchable = lastNameSearchable; }
	
	public String getEmail() { return email; }
	void setEmail(String email) { this.email = email; }
	
	public String getLoginId() { return loginId; }
	void setLoginId(String loginId) { this.loginId = loginId; }
	
	@Override
	public String toString() { return getFullName(); }

	@Override
	public String getName() { return getFriendlyName(); }

}
