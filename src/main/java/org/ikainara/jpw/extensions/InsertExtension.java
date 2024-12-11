package org.ikainara.jpw.extensions;

import org.ikainara.jpw.annotations.Insert;
import com.microsoft.playwright.Page;
import org.ikainara.jpw.parameterResolvers.PwPageParameterResolver;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;

public class InsertExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        var testInstance = context.getRequiredTestInstance();
        var fields = context.getRequiredTestClass().getDeclaredFields();
        var annotatedFields = Arrays.stream(fields).filter(f -> f.isAnnotationPresent(Insert.class)).toList();
        for (var field : annotatedFields) {
            try {
                var constructor = field.getType().getConstructor(Page.class);
                field.setAccessible(true);
                var page = PwPageParameterResolver.getPage(context);
                field.set(testInstance, constructor.newInstance(page));
            } catch (Exception e) {
                throw new RuntimeException("Field '" + field.getName() + "' annotated with Insert annotation, but it class "
                        + field.getType().getTypeName() + " has no public constructor with Page.class parameter");
            }
        }
    }
}
