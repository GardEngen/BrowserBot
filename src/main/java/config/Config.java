package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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


}
