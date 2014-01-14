package com.eerichmond.core.testing;

import com.eerichmond.core.security.PermissionType;
import com.eerichmond.core.utils.CollectionUtils;
import com.google.common.collect.Sets;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class SecurityRequestPostProcessor implements RequestPostProcessor {

	private Set<String> userRoles;
	
	private SecurityRequestPostProcessor(Iterable<String> roles) {
		this.userRoles = Sets.newHashSet(roles);
	}

	@Override
	public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
		if (this.userRoles != null) {
			for (String role : this.userRoles) {
				request.addUserRole(role);	
			}
		}
		
		return request;
	}

	public static SecurityRequestPostProcessor addUserRoles(PermissionType... permissions) {
		Collection<String> permissionsAsStrings = CollectionUtils.convertToStrings(Arrays.asList(permissions));

		return new SecurityRequestPostProcessor(permissionsAsStrings);
	}

}
