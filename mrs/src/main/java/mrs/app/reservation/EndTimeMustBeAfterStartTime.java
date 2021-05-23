package mrs.app.reservation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, TYPE })
@Constraint(validatedBy = {EndTimeMustBeAfterStartTimeValidator.class})
public @interface EndTimeMustBeAfterStartTime {
	String message() default "{mrs.app.reservation.EndTimeMustBeAfterStartTime.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Documented
	@Retention(RUNTIME)
	@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
	public @interface List {
		EndTimeMustBeAfterStartTime[] value();
	}
}
