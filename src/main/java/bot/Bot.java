package bot;

import config.Config;
import config.SystemInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Bot {
    private WebDriver webDriver;
    private Config config;
    private BotAction botAction;
    private WebElement form;
    private WebElement button;


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
                selectCompanyAccount("BagID AS");
                break;

            default:

        }
    }


    private void setupWebDriver() {
        SystemInfo.setChromeDriverPhat();
        this.webDriver = new ChromeDriver();
    }

    private void loginBot(){
       botAction.sleepBot(2000);
        form = webDriver.findElement(By.name("username"));
        form.sendKeys(config.getValue("username"));
        form = webDriver.findElement(By.name("password"));
        form.sendKeys(config.getValue("password"));
        button = webDriver.findElement(By.id("btnLogin"));
        button.click();

    }

    private void selectCompanyAccount(String company){
        botAction.sleepBot(1000);
        button = webDriver.findElement(By.xpath("//div[contains(@class, 'map-name') and text()='ØKONOVA AS']"));
        botAction.sleepBot(1000);
        button.click();
        botAction.sleepBot(2000);
        form = webDriver.findElement(By.id("textfield-1064-inputEl"));
        botAction.sleepBot(1000);
        form.sendKeys(company);
        botAction.sleepBot(2000);
        button = webDriver.findElement(By.xpath("//*[contains(text(), '"+company+"')]"));
        button.click();
    }

    private void handleIncoming(){
        webDriver.navigate().to("https://app.24sevenoffice.com/script/economy/bank/incoming/");
        //Bokfør



        //Ubehandlet


    }
}
