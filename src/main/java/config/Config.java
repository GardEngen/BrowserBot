package config;

import java.io.*;
import java.util.Properties;

/**
 * This class is used to read from config.prop
 */
public class Config {
    private Properties prop;
    private InputStream input = null;

    public Config() {
        this.prop = new Properties();
        try {
            this.input = new FileInputStream("config.properties");
            prop.load(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getValue(String key){
        return prop.getProperty(key);
    }

    public void writeToProp(String key, String value){
        prop.setProperty(key,value);
        try {
            prop.store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
