package com.eerichmond.core.domain;

import org.springframework.core.convert.converter.Converter;

/**
 * Converts from a String to a java.lang.Enum by calling {@link Enum#valueOf(Class, String)}.
 * Copied directly from SpringSource because that class has a package level modifier.
 * https://github.com/SpringSource/spring-framework/blob/master/spring-core/src/main/java/org/springframework/core/convert/support/StringToEnumConverterFactory.java
 * 
 * @param <T> the target Enum class to convert to
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StringToEnumConverter<T extends Enum> implements Converter<String, T> {

	private final Class<T> enumType;

	public StringToEnumConverter(Class<T> enumType) {
		this.enumType = enumType;
	}

	public T convert(String source) {
		if (source.length() == 0) {
			// It's an empty Enum identifier: reset the Enum value to null.
			return null;
		}
		
		return (T) Enum.valueOf(this.enumType, source.trim());
	}
}