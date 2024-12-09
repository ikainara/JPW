package annotations;

import com.microsoft.playwright.junit.OptionsFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import parameterResolvers.BrowserContextParameterResolver;
import parameterResolvers.BrowserParameterResolver;
import parameterResolvers.PlaywrightParameterResolver;
import parameterResolvers.PwPageParameterResolver;

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
