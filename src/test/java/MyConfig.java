import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

public class MyConfig implements OptionsFactory {
    @Override
    public Options getOptions() {
        Options options = new Options();
        options.setLaunchOptions(new BrowserType.LaunchOptions().setHeadless(false));
        options.setContextOptions(new Browser.NewContextOptions().setBaseURL("http://google.com"));
        options.setBrowserName("CHROME");
        return options;
    }
}
