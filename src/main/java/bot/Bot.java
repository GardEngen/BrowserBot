package bot;

import config.Config;
import config.SystemInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

public class Bot {
    private WebDriver webDriver;
    private Config config;
    private Storage storage;
    private BotAction botAction;
    private WebElement form;
    private WebElement button;
    private List<WebElement> webElementList;
    private Scheduler scheduler;

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
                //storeNewCompaniesList();
                botAction.handleUntreatedInfoFile("BagID");
                break;
            case 1:
                setupWebDriver();
                this.botAction = new BotAction(webDriver);
                webDriver.get("https://app.24sevenoffice.com/login/");
                loginBot();
                selectCompanyAccount("Hovl", true);
                navigateToIncoming();
                doPost();
                doUntreated("bagId");
                break;
            case 2:
                System.out.println("jeg er i case 2");

                break;

            default:

        }
    }

    private void navigateToIncoming() {
        botAction.sleepBot(2000);
        webDriver.navigate().to("https://app.24sevenoffice.com/script/economy/bank/incoming/");
        webDriver.navigate().refresh();

        botAction.sleepBot(9000);
    }

    private void doPost() {
        try {
            webElementList = webDriver.findElements(By.className("x-grid-group-collapsed"));
            for (int i = 0; i < webElementList.size(); i++) {
                webElementList.get(i).click();
                botAction.sleepBot(2000);
            }

            webElementList = webDriver.findElements(By.className("x-grid-group-body"));
            if (webElementList.isEmpty()) {
                System.out.println("There is nothing to do here");
                return;
            }
            for (int i = 0; i < webElementList.size(); i++) {

                webElementList.get(i).click();
                botAction.sleepBot(2000);
            }
        } catch (NoSuchElementException e) {
            System.out.println(e);
        } catch (StaleElementReferenceException e) {
            System.out.println("element is not attached to the page document");
        }

    }

    private void doUntreated(String company) {
        botAction.sleepBot(2000);
        button = webDriver.findElement(By.id("ext-comp-1011__tabpane-unknown"));
        button.click();

        webElementList = webDriver.findElements(By.className("x-grid-group-body"));
        if (webElementList.isEmpty()) {
            System.out.println("There is nothing to do here");
            return;
        } else {
            botAction.handleUntreatedInfoFile(company);
        }

    }

    private void initQuartzScheduler() throws Exception {
        JobDetail job = JobBuilder.newJob(ScheduledBotJob.class)//mention the Job Class Name here
                .build();

        //create schedule builder
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * 1/1 * ? *");

        //create trigger which the schedule Builder
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(scheduleBuilder)
                .build();

        //create scheduler
        this.scheduler = new StdSchedulerFactory().getScheduler();

        // start your scheduler
        scheduler.start();

        // let the scheduler call the Job using trigger
        scheduler.scheduleJob(job, trigger);

    }

    public void stopScheduleJob() {
        try {
            scheduler.standby();
        } catch (SchedulerException e) {
            e.printStackTrace();
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
        for (String company : listOfCompanies) {
            System.out.println("check if company '" + company + "' is valid");
            try {
                selectCompanyAccount(company, false);
            } catch (NoSuchElementException e) {
                listIsValid = false;
                //display error to user
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
        botAction.sleepBot(9000);

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
