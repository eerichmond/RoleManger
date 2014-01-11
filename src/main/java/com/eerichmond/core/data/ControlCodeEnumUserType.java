package com.eerichmond.core.data;

import com.eerichmond.core.codes.ControlCode;
import org.hibernate.HibernateException;
import org.hibernate.annotations.common.util.ReflectHelper;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class ControlCodeEnumUserType implements UserType, ParameterizedType {
	private final static String ENUM = "enumClass";
	private Class<? extends Enum<?>> enumClass;
	private final static int SQL_TYPE = Types.VARCHAR;
	
	@SuppressWarnings("unchecked")
	public void setParameterValues(Properties parameters) {
		try {
			enumClass = ReflectHelper.classForName(
					parameters.getProperty(ENUM), this.getClass()).asSubclass(Enum.class);
		} catch (ClassNotFoundException exception) {
			throw new HibernateException("Enum class not found", exception);
		}
	}

	public int[] sqlTypes() {
		return new int[] { SQL_TYPE };
	}

	
	public Class<? extends Enum<?>> returnedClass() {
		return enumClass;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor sessionImpl, Object owner)
			throws HibernateException, SQLException {
		
		boolean isDbValueNull = rs.getObject(names[0]) == null;
		
		String dbValue = (String) rs.getObject(names[0]);
		
		try {
			for (Enum<?> instance : returnedClass().getEnumConstants()) {
				String enumCode = ((ControlCode)instance).getCode();
				
				// If the database value is NULL, see if one of the ControlCode instances has
				// a code of "NULL"
				if (isDbValueNull) {
					if ("NULL".equals(enumCode)) {
						return instance;
					}
				}
				else if (enumCode.equalsIgnoreCase(dbValue)) {
					return instance;
				}
			}
			
			// If the database value is NULL and none of the ControlCode instances matched the
			// code of "NULL", return null
			if (isDbValueNull) {
				return null;
			}
			
		} catch (Exception e) {
			throw new HibernateException("Custom mapping EnumByCodeType failed", e);
		}

		throw new IllegalArgumentException("Unknown code: " + dbValue + " for enum: " + enumClass);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor sessionImpl)
			throws HibernateException, SQLException {
		if (value == null) {
			// Binding null to parameter
			st.setNull(index, SQL_TYPE);
		} else {
			try {
				ControlCode control = (ControlCode)value;
				st.setObject(index, control.getCode(), SQL_TYPE);
			} catch (Exception e) {
				throw new HibernateException(
						"Custom mapping EnumByCodeType failed", e);
			}
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}
	
}