package com.eerichmond.core.data;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class CustomDatabaseNamingStrategy extends ImprovedNamingStrategy {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Return the unqualified class name, mixed case converted to underscores
	 */
	@Override
	public String classToTableName(String className) {
		return super.classToTableName(className).toUpperCase();
	}

	/**
	 * Return the full property path with underscore seperators, mixed case
	 * converted to underscores
	 */
	@Override
	public String propertyToColumnName(String propertyName) {
		return super.propertyToColumnName(propertyName).toUpperCase();
	}

	/**
	 * Convert mixed case to underscores
	 */
	@Override
	public String tableName(String tableName) {
		return super.tableName(tableName).toUpperCase();
	}

	/**
	 * Convert mixed case to underscores
	 */
	@Override
	public String columnName(String columnName) {
		return super.columnName(columnName).toUpperCase();
	}

	@Override
	public String collectionTableName(
			String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable,
			String propertyName
	) {
		return tableName( ownerEntityTable + "_TO_" + associatedEntityTable );
	}

}
