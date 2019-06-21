package jp.co.sss.attendance.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class LoginValidator implements ConstraintValidator<Login, Object> {
//	private String userId;
	private String pw;

	@Override
	public void initialize(Login annotation) {
//		this.userId = annotation.fieldUserId();
		this.pw = annotation.fieldPw();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);

//		String userId = (String) beanWrapper.getPropertyValue(this.userId);
		String pw = (String) beanWrapper.getPropertyValue(this.pw);
		if (pw.contains("jona")) {
			return true;
		} else {
			return false;
		}
	}

}
