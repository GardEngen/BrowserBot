package bot;

import config.Config;
import config.SystemInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

public class Bot {
    private WebDriver webDriver;
    private Config config;
    private Storage storage;
    private BotAction botAction;
    private WebElement form;
    private WebElement button;
    private List<WebElement> webElementList;
    private List<String> storedCompanies;
    private Scheduler scheduler;

    public Bot() {
        this.config = new Config();
        this.storage = new Storage();
        this.storedCompanies = storage.getStoredCompanies();
    }

//    public void startBot(int type) {
//        switch (type) {
//            case 0:
//                this.botAction = new BotAction(webDriver);
//                webDriver.get("https://app.24sevenoffice.com/login/");
//                //storeNewCompaniesList();
//                botAction.handleUntreatedInfoFile("BagID");
//                break;
//            case 1:
//                webDriver.get("https://app.24sevenoffice.com/login/");
//                loginBot();
//                //selectCompanyAccount("Hovl", true);
//                navigateToIncoming();
//                doPost();
//                doUntreated("bagId");
//                break;
//            case 2:
//                System.out.println("jeg er i case 2");
//
//                break;
//
//            default:
//
//        }
//    }

    private void setUpBot() {
        SystemInfo.setChromeDriverPhat();
        this.webDriver = new ChromeDriver();
        this.botAction = new BotAction(webDriver);
        webDriver.get("https://app.24sevenoffice.com/login/");
    }

    public void startBot() {
        setUpBot();
        loginBot();

        for (int i = 0; i < storedCompanies.size(); i++) {
            if (i > 0) {
                selectCompanyAccount(storedCompanies.get(i), true, storedCompanies.get(i - 1));
            } else {
                selectCompanyAccount(storedCompanies.get(i), true, storedCompanies.get(i));
            }
            navigateToIncoming();
            doPost();
            botAction.takeScreenshot(storedCompanies.get(i));
            doUntreated(storedCompanies.get(i));
            goToDashborad();
        }
        botAction.quitBot();
    }

    //    private String getLastCompany(){
//
//
//    }
    private void navigateToIncoming() {
        botAction.sleepBot(2000);
        webDriver.navigate().to("https://app.24sevenoffice.com/script/economy/bank/incoming/");
        webDriver.navigate().refresh();

        botAction.sleepBot(2000);
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
        botAction.sleepBot(2000);
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

    public boolean storeNewCompany(String company) {
        boolean companyIsValid = true;
        //check that the companies are valid.
        setUpBot();
        loginBot();
        System.out.println("check if company '" + company + "' is valid");
        try {
            selectCompanyAccount(company, true, company);
        } catch (NoSuchElementException e) {
            companyIsValid = false;
            botAction.quitBot();
            //display error to user
            System.out.println("company not valid");
        }
        if (!company.isEmpty() && companyIsValid) {
            storage.storeCompany(company);
            botAction.quitBot();
        }
        return companyIsValid;
    }

    private void goToDashborad() {
        webDriver.navigate().to("https://app.24sevenoffice.com/scriptaspx/dashboard/");
        webDriver.navigate().refresh();
        botAction.sleepBot(1500);
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

    private void selectCompanyAccount(String company, boolean enterCompanyInterface, String lastCompany) {
        try {
            button = webDriver.findElement(By.xpath("//div[contains(@class, 'map-name') and text()='ØKONOVA AS']"));
        } catch (NoSuchElementException e) {
            button = webDriver.findElement(By.xpath("//div[contains(@class, 'map-name') and text()='" + lastCompany + "']"));
        }
        button.click();
        botAction.sleepBot(500);
        form = webDriver.findElement(By.xpath("//input[@placeholder='Finn firmakonto']"));
        botAction.sleepBot(500);
        form.sendKeys(company);
        botAction.sleepBot(500);
        button = webDriver.findElement(By.xpath("//*[contains(text(), '" + company + "')]"));
        if (enterCompanyInterface) {
            button.click();
        }
    }

}
