package org.ikainara.jpw.parameterResolvers;

import com.microsoft.playwright.PlaywrightException;
import org.ikainara.jpw.annotations.UseJPWConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.ikainara.jpw.utils.AnnotationUtils;
import org.ikainara.jpw.utils.ExtensionContextUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class PlaywrightParameterResolver implements ParameterResolver {
    final static String id = "playwright";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var parameterType = parameterContext.getParameter().getType();
        return AnnotationUtils.isAnnotationPresentForClassOrMethod(extensionContext, UseJPWConfig.class) && parameterType.equals(Browser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getOrCreatePlaywright(extensionContext);
    }

    public static Playwright getOrCreatePlaywright(ExtensionContext extensionContext) {
        var playwright = ExtensionContextUtils.getObjectFromContext(extensionContext, id, Playwright.class);
        if (playwright == null) {
            playwright = createPlaywright(extensionContext);
            ExtensionContextUtils.saveObjectInContext(extensionContext, id, playwright);
        }
        return playwright;
    }

    private static Playwright createPlaywright(ExtensionContext extensionContext) {
        try {
            var pwConfig = ExtensionContextUtils.getPwConfig(extensionContext);
            return Playwright.create(pwConfig.getOptions().playwrightCreateOptions);
        } catch (Exception exception) {
            throw new PlaywrightException("\nCan't create Playwright: \n" + exception.getMessage());
        }
    }
}
