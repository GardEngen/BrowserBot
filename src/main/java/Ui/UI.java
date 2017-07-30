package Ui;

import javax.swing.*;

/**
 * Created by Gard Engen on 29.07.2017.
 */
public class UI extends JFrame{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private JTextArea console;
    private JProgressBar progressBar;
    private JComboBox typeComboBox;
    private JButton startButton;
    private JButton imageButton;
    private JPanel mainPanel;

    public UI() {
        setTitle("Bot");
        setSize(WIDTH,HEIGHT);
        setContentPane(mainPanel);
    }

    public JTextArea getConsole() {
        return console;
    }

    public void setConsole(JTextArea console) {
        this.console = console;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public void setTypeComboBox(JComboBox typeComboBox) {
        this.typeComboBox = typeComboBox;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public JButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(JButton imageButton) {
        this.imageButton = imageButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
