package com.eerichmond.core.data;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-data-test.xml"})
@Transactional
@Ignore
public class BaseIntegrationTest {
	@PersistenceContext
	protected EntityManager em;
}
