package com.eerichmond.core.codes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation to signal to the ControlCodeConverterFactor.java that this class should use
 * the custom getCode() instead of the default enum.name() when de-serializing enums.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConvertByCode {

}
