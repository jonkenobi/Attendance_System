package jp.co.sss.attendance.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({java.lang.annotation.ElementType.TYPE,
	java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy= {EndBeforeStartValidator.class})
public @interface EndBeforeStart {
	String message()default"終了時刻には開始時刻以降を指定してください";
	Class<?>[]groups()default{};
	Class<?extends Payload>[]payload()default{};
	
	String fieldStartTime()default"startTime";
	String fieldEndTime()default "EndTime";
}
