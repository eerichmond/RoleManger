package com.eerichmond.core.security;

import com.eerichmond.core.data.PersonRepository;
import com.eerichmond.core.domain.Person;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

@Service
@Transactional(readOnly=true)
public class SimpleUserDetailsService implements UserDetailsService {

	private PersonRepository personRepository;

	@Inject
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		Person person = personRepository.findByLoginId(username);

		UserDetails userDetails = new UserDetailsImpl(username, buildGrantedAuthorities(person));

		if (person != null) {
			BeanUtils.copyProperties(person, userDetails);
		}

		return userDetails;
	}

	/**
	 * Creates GrantedAuthorities for each affiliation the person has. This makes using Spring Security easier.
	 * @param person the person to build the granted authorities for.
	 */
	Collection<? extends GrantedAuthority> buildGrantedAuthorities(Person person) {
		Set<GrantedAuthority> authorities = Sets.newHashSet();

		if (person == null) {
			return authorities;
		}

		for (Association association : person.getActiveAssociations()) {
			authorities.add(new SimpleGrantedAuthority(association.getRole().getCode()));
		}

		return authorities;
	}
}
