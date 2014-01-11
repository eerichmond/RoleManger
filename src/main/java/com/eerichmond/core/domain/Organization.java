package com.eerichmond.core.domain;

import com.eerichmond.core.security.AssociationRoles;
import org.hibernate.annotations.Table;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.util.Set;

@Entity
@SecondaryTable(name="ORGANIZATIONS")
@Table(appliesTo="ORGANIZATIONS", optional=false)	// Need this for force an inner join
@Cacheable
public abstract class Organization extends Party implements Comparable<Organization> {

	private static final long serialVersionUID = 1L;
	
	@Column(table="ORGANIZATIONS")
	private String name;

	@Column(table="ORGANIZATIONS")
	private String nameSearchable;
	
	/**
	 * Empty constructor for proxy/ORM libraries.
	 */
	public Organization() { super(); }

	/**
	 * Constructor for testing. Does not load up the record. The ID is generated from the hash code of the name.
	 * @param name the name of the organization.
	 */
	public Organization(String name) {
		super((long) name.hashCode());
		this.name = name;
	}
	
	@Override
	public String getName() { return name; }

	/**
	 * Indicates if this organization can potentially have sub units associated with it. This helps to optimize the
	 * search queries.
	 * @return true if this organization class type could be a parent unit.
	 */
	public abstract HierarchyLevel getHierarchyLevel();

	/**
	 * Searched up the SUBUNIT hierarchy tree and returns the first organization of the specified type.
	 * @param clazz the class type of the parent organization to return.
	 */
	public <T extends Organization> T findParentUnitByType(Class<? extends Organization> clazz) {
		Set<T> parentUnits = findAssociationsByRole(AssociationRoles.SUBUNIT);

		for ( T org : parentUnits ) {
			if (clazz.isAssignableFrom(org.getClass())) {
				return org;
			}
		}

		for ( T org : parentUnits ) {
			T parentUnit = org.findParentUnitByType(clazz);

			if (parentUnit != null) {
				return parentUnit;
			}
		}

		return null;
	}

	@Override
	public int compareTo(Organization other) {
		if (other == null) {
			return 1;
		}

		if (this.getName() == null) {
			return -1;
		}

		return this.getName().compareTo(other.getName());
	}

}
