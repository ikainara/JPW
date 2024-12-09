package parameterResolvers;

import annotations.UseJPWConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static utils.ExtensionContextUtils.*;
import static utils.AnnotationUtils.*;

public class PlaywrightParameterResolver implements ParameterResolver {
    final static String id = "playwright";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var parameterType = parameterContext.getParameter().getType();
        return isAnnotationPresentForClassOrMethod(extensionContext, UseJPWConfig.class) && parameterType.equals(Browser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getOrCreatePlaywright(extensionContext);
    }

    public static Playwright getOrCreatePlaywright(ExtensionContext extensionContext) {
        var playwright = getObjectFromContext(extensionContext, id, Playwright.class);
        if (playwright == null) {
            playwright = createPlaywright(extensionContext);
            saveObjectInContext(extensionContext, id, playwright);
        }
        return playwright;
    }

    private static Playwright createPlaywright(ExtensionContext extensionContext) {
        var pwConfig = getPwConfig(extensionContext);
        return Playwright.create(pwConfig.getOptions().playwrightCreateOptions);
    }
}
