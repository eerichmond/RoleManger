package com.eerichmond.core.domain;

import com.eerichmond.core.security.AssociationRole;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OrganizationTest {

	@Test
	public void findParentUnitByType_noParentOfType_returnsNull() throws Exception {
		Department dept = new Department();

		assertThat(dept.findParentUnitByType(Department.class), is(nullValue()));
	}

	@Test
	public void findParentUnitByType_hasDirectParentOfType_returnsDirectParent() throws Exception {
		Department parentDept = new Department("parentDept");
		Department dept = new Department("dept");

		dept.addAssociation(AssociationRole.SUBUNIT, parentDept);

		// Act
		Department actualParentUnit = dept.findParentUnitByType(Department.class);

		// Assert
		assertThat(actualParentUnit, is(parentDept));
	}

	@Test
	public void findParentUnitByType_hasTwoParentsOfSameType_returnsFirstOne() throws Exception {
		Department parentDept1 = new Department("parentDept1");
		Department parentDept2 = new Department("parentDept2");

		Department dept = new Department("dept");
		dept.addAssociation(AssociationRole.SUBUNIT, parentDept1);
		dept.addAssociation(AssociationRole.SUBUNIT, parentDept2);

		// Act
		Department actualParentUnit = dept.findParentUnitByType(Department.class);

		// Assert
		assertThat(actualParentUnit, is(parentDept1));
	}

	@Test
	public void findParentUnitByType_hasTwoParentsOfDifferentTypes_returnsCorrectOne() throws Exception {
        Institution institution = new Institution("Institution");
		Department parentDept2 = new Department("parentDept2");

		Department dept = new Department("dept");
		dept.addAssociation(AssociationRole.SUBUNIT, institution);
		dept.addAssociation(AssociationRole.SUBUNIT, parentDept2);

		// Act
		Department actualParentUnit = dept.findParentUnitByType(Department.class);

		// Assert
		assertThat(actualParentUnit, is(parentDept2));
	}

	@Test
	public void findParentUnitByType_hasGrandParentOfType_returnsGrandParent() throws Exception {
        Institution institution = new Institution("Institution");

		Department parentDept = new Department("parentDept");
		parentDept.addAssociation(AssociationRole.SUBUNIT, institution);

		Department dept = new Department("dept");
		dept.addAssociation(AssociationRole.SUBUNIT, parentDept);

		// Act
        Institution actualParentUnit = dept.findParentUnitByType(Institution.class);

		// Assert
		assertThat(actualParentUnit, is(institution));
	}
}
