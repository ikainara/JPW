package org.ikainara.jpw.parameterResolvers;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static org.ikainara.jpw.utils.ExtensionContextUtils.*;

public class PwPageParameterResolver implements ParameterResolver {
    final static String id = "page";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var parameterType = parameterContext.getParameter().getType();
        return parameterType.equals(Page.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getPage(extensionContext);
    }

    public static Page getPage(ExtensionContext extensionContext) {
        var page = getObjectFromContext(extensionContext, id, Page.class);
        if (page == null) {
            var browserContext = BrowserContextParameterResolver.getBrowserContext(extensionContext);
            page = browserContext.newPage();
            saveObjectInContext(extensionContext, id, page);
        }
        return page;
    }
}
