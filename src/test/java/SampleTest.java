import annotations.UseJPWConfig;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;

@UseJPWConfig(MyConfig.class)
public class SampleTest {
    @Test
    public void myTest(Page page) {
        page.navigate("/");
    }
}