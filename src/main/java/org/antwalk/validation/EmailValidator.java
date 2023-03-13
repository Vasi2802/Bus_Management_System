package org.antwalk.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\\.)?nrifintech\\.com$";

	@Override
	public boolean isValid(final String email, final ConstraintValidatorContext context) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		if (email == null) {
			return false;
		}
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}