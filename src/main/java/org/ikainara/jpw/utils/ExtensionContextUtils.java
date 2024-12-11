package org.ikainara.jpw.utils;

import org.ikainara.jpw.annotations.UseJPWConfig;
import com.microsoft.playwright.junit.OptionsFactory;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ExtensionContextUtils {
    private static final ExtensionContext.Namespace JPW_NAMESPACE = ExtensionContext.Namespace.create(JPw.class);
    private static final String PW_CONFIG_ID = "pwConfig";

    public static boolean isObjectPresentInContext(ExtensionContext extensionContext, String id) {
        return extensionContext.getStore(JPW_NAMESPACE).get(id) != null;
    }

    public static void saveObjectInContext(ExtensionContext context, String id, Object object) {
        context.getStore(JPW_NAMESPACE).put(id, object);
    }

    @Nullable
    public static <T> T getObjectFromContext(ExtensionContext context, String id, Class<T> clazz) {
        return context.getStore(JPW_NAMESPACE).get(id, clazz);

    }

    @SneakyThrows
    public static OptionsFactory getPwConfig(ExtensionContext extensionContext) {
        var configuration = getObjectFromContext(extensionContext, PW_CONFIG_ID, OptionsFactory.class);
        Class<? extends OptionsFactory> configClass;
        if(configuration == null) {
            try {
                configClass = extensionContext.getRequiredTestMethod().getAnnotation(UseJPWConfig.class).value();
            } catch (Exception e) {
                configClass = extensionContext.getRequiredTestClass().getAnnotation(UseJPWConfig.class).value();
            }
            configuration = configClass.getConstructor().newInstance();
            saveObjectInContext(extensionContext, PW_CONFIG_ID, configuration);
        }
        return configuration;
    }

    private static class JPw {
    }
}
