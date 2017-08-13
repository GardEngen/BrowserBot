package bot;

import com.thoughtworks.selenium.SeleniumException;
import config.Config;
import config.SystemInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;

public class Bot {
    private WebDriver webDriver;
    private Config config;
    private Storage storage;
    private BotAction botAction;
    private WebElement form;
    private WebElement button;

    public Bot() {
        this.config = new Config();
        this.storage = new Storage();
    }

    public void startBot(int type) {
        switch (type) {
            case 0:
                setupWebDriver();
                this.botAction = new BotAction(webDriver);
                webDriver.get("https://app.24sevenoffice.com/login/");
                storeNewCompaniesList();

                break;
            case 1:
                setupWebDriver();
                this.botAction = new BotAction(webDriver);
                webDriver.get("https://app.24sevenoffice.com/login/");
//                loginBot();
//                selectCompanyAccount("BagID AS",true);
                break;

            default:

        }
    }

    private void storeNewCompaniesList() {
        ArrayList<String> listOfCompanies = new ArrayList<String>() {{
            add("FEEEEEIIILL");
//            add("Lingua");
//            add("Puro");
        }};
        boolean listIsValid = true;
        //check that the companies are valid.
        loginBot();
        for(String company : listOfCompanies){
            System.out.println("check if company '" + company + "' is valid");
            try {
                selectCompanyAccount(company, false);
            } catch (NoSuchElementException e){
                listIsValid = false;
                System.out.println("company not valid");
                return;
            }
        }
        if (listOfCompanies != null || listIsValid) {
            storage.storeCompanies(listOfCompanies);
        }
    }

    private void setupWebDriver() {
        SystemInfo.setChromeDriverPhat();
        this.webDriver = new ChromeDriver();
    }

    private void goToDashborad() {
        webDriver.navigate().to("https://app.24sevenoffice.com/scriptaspx/dashboard/");
    }

    private void loginBot() {
        botAction.sleepBot(2000);
        form = webDriver.findElement(By.name("username"));
        form.sendKeys(config.getValue("username"));
        form = webDriver.findElement(By.name("password"));
        form.sendKeys(config.getValue("password"));
        button = webDriver.findElement(By.id("btnLogin"));
        button.click();
        botAction.sleepBot(6000);

    }

    private void selectCompanyAccount(String company, boolean enterCompanyInterface) {
        button = webDriver.findElement(By.xpath("//div[contains(@class, 'map-name') and text()='ØKONOVA AS']"));
        botAction.sleepBot(1000);
        button.click();
        botAction.sleepBot(2000);
        form = webDriver.findElement(By.id("textfield-1064-inputEl"));
        botAction.sleepBot(1000);
        form.sendKeys(company);
        botAction.sleepBot(2000);
        button = webDriver.findElement(By.xpath("//*[contains(text(), '" + company + "')]"));
        if (enterCompanyInterface) {
            button.click();
        }
    }
}
