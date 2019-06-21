package jp.co.sss.attendance.util;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class EndBeforeStartValidator implements ConstraintValidator<EndBeforeStart, Object> {
	private String startTime;
	private String endTime;

	@Override
	public void initialize(EndBeforeStart annotation) {
		this.startTime = annotation.fieldStartTime();
		this.endTime = annotation.fieldEndTime();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		String startTime = (String) beanWrapper.getPropertyValue(this.startTime);
		String endTime = (String) beanWrapper.getPropertyValue(this.endTime);
		try {
			LocalTime LTstartTime = LocalTime.parse(startTime);
			LocalTime LTendTime = LocalTime.parse(endTime);
			return !LTstartTime.isAfter(LTendTime);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

	}
}
