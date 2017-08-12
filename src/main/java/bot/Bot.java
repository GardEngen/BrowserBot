package bot;

import config.Config;
import config.SystemInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Bot {
    private WebDriver webDriver;
    private Config config;
    private BotAction botAction;


    public Bot() {
        this.config = new Config();
    }

    public void startBot(int type) {
        switch (type) {
            case 0:
                break;
            case 1:
                setupWebDriver();
                this.botAction = new BotAction(webDriver);
                webDriver.get("https://app.24sevenoffice.com/login/");
                loginBot();
                break;

            default:

        }
    }


    private void setupWebDriver() {
        SystemInfo.setChromeDriverPhat();
        this.webDriver = new ChromeDriver();
    }

    private void loginBot(){
        WebElement form;
        WebElement button;
       botAction.sleepBot(2000);
        form = webDriver.findElement(By.name("username"));
        form.sendKeys(config.getValue("username"));
        form = webDriver.findElement(By.name("password"));
        form.sendKeys(config.getValue("password"));
        button = webDriver.findElement(By.id("btnLogin"));
        button.click();
        botAction.sleepBot(4000);
    }

    private void selectCompanyaccount(){
        //        botAction.sleepBot(5000);
//        button = webDriver.findElement(By.className("map-name"));
//        button.click();
    }
}
