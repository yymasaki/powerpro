package com.example;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * NULLおよびスペース（全角含む）のみのフォーム入力を不可とするバリデーター.
 */
public class SpaceValidator implements ConstraintValidator<NotSpace, String> {

	@Override
	public void initialize(NotSpace notFullWidthSpace) {

	}

	@Override
	public boolean isValid(String text, ConstraintValidatorContext cxt) {

		if (Objects.isNull(text)) {
			return false;
		}

		text = text.replace("　", "").trim();
		if (text.isEmpty()) {
			return false;
		}

		return true;
	}
}
