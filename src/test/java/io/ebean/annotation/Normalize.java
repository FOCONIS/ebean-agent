/*
 * Licensed Materials - Property of FOCONIS AG
 * (C) Copyright FOCONIS AG.
 */

package io.ebean.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to mark a string field or a class with a Normalization.
 *
 * Multiple annotations can be specified which will all be called one after the other.
 *
 * @author Alexander Wagner, FOCONIS AG
 *
 */
@Documented
@Target({ FIELD, TYPE })
@Retention(RUNTIME)
public @interface Normalize {

	/**
	 * The Normalization class.
	 */
	Class<?>[] value();

}
