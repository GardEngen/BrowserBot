package bot;

import config.SystemInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Gard on 13.08.2017.
 */
public class Storage {
    public Storage() {
    }

    public void storeCompanies(List companies){
        JSONObject obj = new JSONObject();
        JSONArray companiesJSON = new JSONArray();
        companiesJSON.addAll(companies);
        obj.put("Company List", companiesJSON);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(SystemInfo.STORAGE_PATH)) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
