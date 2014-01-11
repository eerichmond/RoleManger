package com.eerichmond.core.data;

import com.eerichmond.core.domain.PagingOptions;
import com.eerichmond.core.security.PersonToAssociations;
import org.springframework.data.domain.Page;

public interface PersonRepositoryCustom {

	Page<PersonToAssociations> search(PersonSearchCriteria criteria);

	Page<PersonToAssociations> search(PersonSearchCriteria criteria, PagingOptions pagingOptions);

}
