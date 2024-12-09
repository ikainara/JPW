package utils;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class AnnotationUtils {
    public static boolean isAnnotationPresentForClassOrMethod(ExtensionContext extensionContext, Class<? extends Annotation> clazz) {
        return extensionContext.getRequiredTestClass().isAnnotationPresent(clazz) ||
                extensionContext.getRequiredTestMethod().isAnnotationPresent(clazz);
    }

    @Nullable
    public static AnnotatedElement getAnnotatedElement(ExtensionContext extensionContext, Class<? extends Annotation> clazz) {
        if(extensionContext.getRequiredTestMethod().isAnnotationPresent(clazz)) {
            return extensionContext.getRequiredTestMethod();
        } else if(extensionContext.getRequiredTestClass().isAnnotationPresent(clazz)) {
            return extensionContext.getRequiredTestClass();
        }
        return null;
    }
}
