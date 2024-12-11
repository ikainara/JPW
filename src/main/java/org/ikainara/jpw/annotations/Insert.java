package org.ikainara.jpw.annotations;

import org.ikainara.jpw.extensions.InsertExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ExtendWith(InsertExtension.class)
public @interface Insert {
}
