package parameterResolvers;

import annotations.Insert;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class InsertExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        var testInstance = context.getRequiredTestInstance();
        var fields = context.getRequiredTestClass().getDeclaredFields();
        var annotatedFields = Arrays.stream(fields).filter(f -> f.isAnnotationPresent(Insert.class)).toList();
        for (var field : annotatedFields) {
            Constructor constructor;
            try {
                constructor = field.getType().getConstructor(Page.class);
            } catch (Exception e) {
                throw new RuntimeException("Field '" + field.getName() + "' annotated with Insert annotation, but it class "
                        + field.getType().getTypeName() + "has no public constructor with Page.class parameter");
            }
            field.setAccessible(true);
            var page = PwPageParameterResolver.getPage(context);
            field.set(testInstance, constructor.newInstance(page));
        }
    }
}