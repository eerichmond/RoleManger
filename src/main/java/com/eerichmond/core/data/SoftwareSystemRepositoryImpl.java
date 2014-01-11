package com.eerichmond.core.data;

import com.eerichmond.core.domain.SoftwareSystem;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SoftwareSystemRepositoryImpl implements SoftwareSystemRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
	
	@Value("${app.name}")
	private String thisAppName;
	
	@Value("${banner.name}")
	private String bannerSystemName;
	
	@Override
	public SoftwareSystem findThisApp() {
		return (SoftwareSystem) em.createQuery("from SoftwareSystem where name = :name")
			.setParameter("name", thisAppName)
			.getSingleResult();
	}
}
