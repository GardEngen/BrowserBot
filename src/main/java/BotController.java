import Bot.Bot;
import Ui.UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Gard Engen on 29.07.2017.
 */
public class BotController {

    private UI ui;
    private Bot bot;

    private JTextArea console;
    private JProgressBar progressBar;
    private JComboBox typeComboBox;
    private JButton startButton;
    private JButton imageButton;
    private int lineCounter;

    public BotController() {
        this.lineCounter = 0;
        this.bot = new Bot();
        initComponents();
        initListeners();
        setTypesOfBot();
    }

    private void initListeners() {
        startButton.addActionListener(new StartListener());
        imageButton.addActionListener(new ImageListener());
    }

    private void initComponents() {
        this.ui = new UI();
        this.console = ui.getConsole();
        this.progressBar = ui.getProgressBar();
        this.typeComboBox = ui.getTypeComboBox();
        this.startButton = ui.getStartButton();
        this.imageButton = ui.getImageButton();
    }

    private void setTypesOfBot(){
        typeComboBox.addItem("BagID");
        typeComboBox.addItem("Test2");
        typeComboBox.addItem("sdf");
        typeComboBox.addItem("asdfeqqefewq");
        typeComboBox.addItem("sdfadf");
        typeComboBox.setSelectedIndex(1);
    }
    public void consoleOutPut(String outPut) {
        lineCounter++;
        console.append(lineCounter+". " + outPut + "\n");
    }

    public void showUi() {
        ui.setVisible(true);
    }

    private class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            consoleOutPut("Start bot");
            progressBar.setIndeterminate(true);
            bot.startBot(typeComboBox.getSelectedIndex());
            progressBar.setIndeterminate(false);
        }
    }

    private class ImageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            consoleOutPut("Go to screenshots");
            bot.openScreenshotfolder();

        }
    }
//    private class MyWorker extends SwingWorker<String, Void>{
//        protected String doInBackground() throws Exception {
//            return null;
//        }
//    }
}
