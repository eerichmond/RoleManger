package com.eerichmond.core.data;

import com.eerichmond.core.domain.SoftwareSystem;
import org.springframework.data.repository.Repository;

public interface SoftwareSystemRepository extends Repository<SoftwareSystem, Long>, SoftwareSystemRepositoryCustom {
}