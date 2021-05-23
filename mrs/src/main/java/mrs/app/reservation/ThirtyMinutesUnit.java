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
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@Constraint(validatedBy = {ThirtyMinutesUnitValidator.class})
public @interface ThirtyMinutesUnit {
	String message() default "{mrs.app.reservation.ThirtyMinutesUnit.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		ThirtyMinutesUnit[] value();
	}
}
