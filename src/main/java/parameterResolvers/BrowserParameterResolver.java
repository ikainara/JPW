package parameterResolvers;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static utils.ExtensionContextUtils.*;

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
            browser = createBrowser(extensionContext, playwright);
            saveObjectInContext(extensionContext, id, browser);
        }
        return browser;
    }

    private static Browser createBrowser(ExtensionContext extensionContext, Playwright playwright) {
        var config = getPwConfig(extensionContext);
        Browser browser;
        switch (config.getOptions().browserName) {
            case BrowserName.CHROME -> browser = playwright.chromium().launch(config.getOptions().launchOptions);
            case BrowserName.FIREFOX -> browser = playwright.firefox().launch(config.getOptions().launchOptions);
            case BrowserName.EDGE -> browser = playwright.webkit().launch(config.getOptions().launchOptions);
            default -> throw new PlaywrightException("Unknown browser '" + config.getOptions().browserName + "'. Known list: " + BrowserName.getBrowsers());
        }
        return browser;
    }
}
