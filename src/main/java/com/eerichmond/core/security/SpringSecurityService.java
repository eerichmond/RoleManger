package com.eerichmond.core.security;

import com.eerichmond.core.data.PersonRepository;
import com.eerichmond.core.domain.Person;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SpringSecurityService implements AuditorAware<Person> {

	private PersonRepository personRepository;

	public SpringSecurityService() { }

	@Inject
	public SpringSecurityService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public Person getCurrentAuditor() {
		Authentication auth = getAuthentication();
		
		return auth != null ? personRepository.findByLoginIds(auth.getName()) : null;
	}

	public Person getCurrentUser() { return getCurrentAuditor(); }

	public String getCurrentUsername() {
		return getUserDetails() != null ? getUserDetails().getUsername() : null;
	}
	
	public UserDetails getUserDetails() {
		Authentication auth = getAuthentication();

		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			return (UserDetails) auth.getPrincipal();
		}

		return null;
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}