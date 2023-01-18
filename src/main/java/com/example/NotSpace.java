package com.example;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * NULLおよびスペース（全角含む）のみのフォーム入力を不可とするバリデーション.
 */
@Documented
@Constraint(validatedBy = SpaceValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSpace {

	String message() default "空白のみの投稿は不可です";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
