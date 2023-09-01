package com.github.tkasozi.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.tkasozi.ResourceUtility;


/**
 * Annotation is used to mark methods
 * that trigger logging how long the method took to execute.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeProfile {
	/**
	 * This is the name that will show up as the type of the log on the UI
	 * Default value is TIME_PROFILE.
	 * <p>
	 * This will also be the prefix given a custom value. e.g. TIME_PROFILE_XXX
	 *
	 * @return label.
	 */
	String value() default ResourceUtility.TIME_PROFILE_PREFIX;
}
