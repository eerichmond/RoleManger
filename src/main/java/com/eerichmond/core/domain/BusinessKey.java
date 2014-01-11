package com.eerichmond.core.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marker interface to indicate that a property is part of the natural id of the object.
 */
@Target( { METHOD, FIELD } )
@Retention( RUNTIME )
public @interface BusinessKey { }
