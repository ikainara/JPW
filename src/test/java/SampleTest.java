import org.ikainara.jpw.annotations.UseJPWConfig;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;

@UseJPWConfig(MyConfig.class)
public class SampleTest {
    @Test
    public void myTest(Page page) {
        page.navigate("/");
        assert page.locator("div").all().get(0).isVisible();
    }
}
