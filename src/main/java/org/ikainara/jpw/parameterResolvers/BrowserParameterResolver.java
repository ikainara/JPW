package org.ikainara.jpw.parameterResolvers;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import org.ikainara.jpw.BrowserName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static org.ikainara.jpw.utils.ExtensionContextUtils.*;

public class BrowserParameterResolver implements ParameterResolver {
    final static String id = "browser";
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var parameterType = parameterContext.getParameter().getType();
        return parameterType.equals(Browser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getBrowser(extensionContext);
    }

    public static Browser getBrowser(ExtensionContext extensionContext) {
        var browser = getObjectFromContext(extensionContext, id, Browser.class);
        if(browser == null) {
            var playwright  = PlaywrightParameterResolver.getOrCreatePlaywright(extensionContext);
            try {
                browser = createBrowser(extensionContext, playwright);
            } catch (Exception e) {
                throw new PlaywrightException("\nCan't create Playwright browser: \n" + e.getMessage());
            }
            saveObjectInContext(extensionContext, id, browser);
        }
        return browser;
    }

    private static Browser createBrowser(ExtensionContext extensionContext, Playwright playwright) {
        var config = getPwConfig(extensionContext);
        try {
            Browser browser;
            switch (config.getOptions().browserName) {
                case BrowserName.CHROME -> browser = playwright.chromium().launch(config.getOptions().launchOptions);
                case BrowserName.FIREFOX -> browser = playwright.firefox().launch(config.getOptions().launchOptions);
                case BrowserName.EDGE -> browser = playwright.webkit().launch(config.getOptions().launchOptions);
                default -> throw new PlaywrightException("Unknown browser '" + config.getOptions().browserName + "'. Known list: " + BrowserName.getBrowsers());
            }
            return browser;
        } catch (Exception e) {
            throw new RuntimeException("\nNo browser name is specified in config. \n" + e.getMessage());
        }
    }
}
