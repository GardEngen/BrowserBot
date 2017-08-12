package config;

import java.io.File;

/**
 * Created by Gard on 13.08.2017.
 */
public class SystemInfo {
    public static final String PROJECT_PATH = System.getProperty(("user.dir"));
    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static final String SCREENSHOT_PATH = PROJECT_PATH + File.separator + "screenshot" + File.separator;

    public static void setChromeDriverPhat() {
        if (SystemInfo.OS.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", PROJECT_PATH + "/chromedriver");

        } else {
            System.setProperty("webdriver.chrome.driver", PROJECT_PATH + "\\chromedriver.exe");
        }
    }
}
