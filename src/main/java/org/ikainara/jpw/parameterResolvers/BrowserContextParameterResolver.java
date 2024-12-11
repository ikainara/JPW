package org.ikainara.jpw.parameterResolvers;

import com.microsoft.playwright.BrowserContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static org.ikainara.jpw.utils.ExtensionContextUtils.*;
public class BrowserContextParameterResolver implements ParameterResolver {
    final static String id = "browserContext";
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var parameterType = parameterContext.getParameter().getType();
        return parameterType.equals(BrowserContext.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getBrowserContext(extensionContext);
    }

    public static BrowserContext getBrowserContext(ExtensionContext extensionContext) {
        var browserContext = getObjectFromContext(extensionContext, id, BrowserContext.class);
        if(browserContext == null) {
            var browser = BrowserParameterResolver.getBrowser(extensionContext);
            var config = getPwConfig(extensionContext);
            browserContext = browser.newContext(config.getOptions().contextOptions);
            saveObjectInContext(extensionContext, id, browserContext);
        }
        return browserContext;
    }
}
