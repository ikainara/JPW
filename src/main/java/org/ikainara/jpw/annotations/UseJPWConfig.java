package org.ikainara.jpw.annotations;

import com.microsoft.playwright.junit.OptionsFactory;
import org.ikainara.jpw.parameterResolvers.PwPageParameterResolver;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ikainara.jpw.parameterResolvers.BrowserContextParameterResolver;
import org.ikainara.jpw.parameterResolvers.BrowserParameterResolver;
import org.ikainara.jpw.parameterResolvers.PlaywrightParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ExtendWith({PwPageParameterResolver.class, BrowserParameterResolver.class, BrowserContextParameterResolver.class, PlaywrightParameterResolver.class})
public @interface UseJPWConfig {
    Class<? extends OptionsFactory> value();
}
