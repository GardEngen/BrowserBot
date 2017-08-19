package bot;

import config.SystemInfo;
import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.*;

public class BotAction {
    private WebDriver webDriver;

    public BotAction(WebDriver webDriver) {

        this.webDriver = webDriver;
    }

    public void sleepBot(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshot() {

        //Takes screenshot
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        System.out.println("Takes screenshot");

        //Checks if directory exists
        if (new File(SystemInfo.SCREENSHOT_PATH + getCurrentDate()).exists()) {
            //Stores file
            System.out.println("directory finness");
            try {
                FileUtils.copyFile(scrFile, new File(SystemInfo.SCREENSHOT_PATH + getCurrentDate() + File.separator + getCurrentTime() + ".png"));
                System.out.println("fil lagres;:" + SystemInfo.SCREENSHOT_PATH + getCurrentDate() + File.separator + getCurrentTime() + ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("directory finness");
            //make directory
            //new File(SystemInfo.PROJECT_PATH + getCurrentDate()).mkdirs();
            //Stores file
            //System.out.println("created dir: + " + SystemInfo.PROJECT_PATH + getCurrentDate());

            try {
                FileUtils.copyFile(scrFile, new File(SystemInfo.SCREENSHOT_PATH + getCurrentDate() + File.separator + getCurrentTime() + ".png"));
                System.out.println("fil lagres;:" + SystemInfo.SCREENSHOT_PATH + getCurrentDate() + File.separator + getCurrentTime() + ".png");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleUntreatedInfoFile(String company) {
        try (FileWriter fw = new FileWriter(SystemInfo.UNTREATED_PATH + getCurrentDate() + ".txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("Det ligger ubehandla innbetalinger på selskap: "+ company);
            out.println(" ");
        } catch (IOException e) {
        }
    }

    public void openScreenshotFolder() {
        try {
            Desktop.getDesktop().open(new File(SystemInfo.SCREENSHOT_PATH));
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
