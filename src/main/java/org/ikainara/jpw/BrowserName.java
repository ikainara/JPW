package org.ikainara.jpw;

import java.util.List;

public class BrowserName {
    public final static String CHROME = "CHROME";
    public final static String FIREFOX = "FIREFOX";
    public final static String EDGE = "EDGE";

    public static List<String> getBrowsers() {
        return List.of(CHROME, FIREFOX, EDGE);
    }
}
