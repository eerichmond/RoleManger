package com.eerichmond.core.codes;

import com.eerichmond.core.domain.StringToEnumConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Converts from a string to a java.lang.Enum by calling the standard StringToEnumConverter or
 * if the Enum has the ConvertByCode annotation it converts using the ControlCode.getCode() method.
 */
public class ControlCodeConverterFactory implements ConverterFactory<String, Enum<?>> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
		if (ControlCode.class.isAssignableFrom(targetType) &&
				targetType.isAnnotationPresent(ConvertByCode.class)) {
			
			return new ControlCodeConverterByCode(targetType);
		}
		
		// Default to the standard StringToEnumConverter which was copied from SpringSource.
		return new StringToEnumConverter(targetType);
	}

	/**
	 * Converts the String to the ControlCode Enum using ControlCode.getCode() instead of the normal Enum.name().
	 * @param <T> the target Enum/ControlCode class to convert to
	 */
	class ControlCodeConverterByCode<T extends Enum<?> & ControlCode> implements Converter<String, T> {
		
		private final Class<T> enumType;
		
		public ControlCodeConverterByCode(Class<T> enumType) {
			this.enumType = enumType;
		}
		
		public T convert(String code) {
			for (T instance : enumType.getEnumConstants()) {
				if (code.equalsIgnoreCase(instance.getCode())) {
					return instance;
				}
			}
			
			return null;
		}

	}
	
}