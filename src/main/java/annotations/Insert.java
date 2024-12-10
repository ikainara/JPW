package annotations;

import org.junit.jupiter.api.extension.ExtendWith;
import parameterResolvers.InsertExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ExtendWith(InsertExtension.class)
public @interface Insert {
}
