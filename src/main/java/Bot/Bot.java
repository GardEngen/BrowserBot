package Bot;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bot {
    private WebDriver webDriver;
    private String projectPath;
    private String screenshotPhat;
    private boolean isBotRunning;

    public Bot() {
        this.projectPath = System.getProperty("user.dir");
        this.screenshotPhat = projectPath + "\\screenshot\\";
    }

    public void startBot(int type) {
        switch (type) {
            case 0:
                break;
            case 1:
                initBot();
                webDriver.get("http://mammatilmichelle.blogg.no/");
                WebElement link;
                //link = webDriver.findElement(By.partialLinkText("http://mammatilmichelle.blogg.no/1501318808_dette_er_vre_nye_navn.html"));
                link = webDriver.findElement(By.className("title"));
                link.click();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                takeScreenshot();

                //WebElement seaBox;


                //seaBox = driver.findElement(By.id("searchInput"));
                //seaBox.sendKeys("Norge");


                break;

            default:

        }
    }


    private void initBot() {
        //TODO Get project path
        System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
        this.webDriver = new ChromeDriver();
    }

    public void takeScreenshot() {
        String screenshotPhat = projectPath + "\\screenshot\\";

        //Takes screenshot
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        System.out.println("Takes screenshot");

        //Checks if directory exists
        if (new File(screenshotPhat + getCurrentDate()).exists()) {
            //Stores file
            System.out.println("directory finness");
            try {
                FileUtils.copyFile(scrFile, new File(screenshotPhat + getCurrentDate() + "\\" + getCurrentTime() + ".png"));
                System.out.println("fil lagres;:" + screenshotPhat + getCurrentDate() + "\\" + getCurrentTime() + ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("directory finness");
            //make directory
            new File(projectPath + getCurrentDate()).mkdirs();
            //Stores file
            System.out.println("created dir: + " + projectPath + getCurrentDate());

            try {
                FileUtils.copyFile(scrFile, new File(screenshotPhat + getCurrentDate() + "\\" + getCurrentTime() + ".png"));
                System.out.println("fil lagres;:" + screenshotPhat + getCurrentDate() + "\\" + getCurrentTime() + ".png");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openScreenshotfolder() {
        try {
            Desktop.getDesktop().open(new File(screenshotPhat));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentDate() {
        LocalDate localDate = new LocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy");
        return localDate.toString(fmt);
    }

    private String getCurrentTime() {
        LocalTime localTime = new LocalTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH-mm-ss");
        return localTime.toString(fmt);
    }

    public void quitBot() {
        webDriver.quit();
    }
}
