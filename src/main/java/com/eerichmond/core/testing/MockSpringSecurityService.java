package com.eerichmond.core.testing;


import com.eerichmond.core.domain.Person;
import com.eerichmond.core.security.SpringSecurityService;

public class MockSpringSecurityService extends SpringSecurityService {

	public MockSpringSecurityService() { }

	@Override
	public Person getCurrentAuditor() {
		return new Person("First", "Last");
	}

}
