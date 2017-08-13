package bot;

import config.SystemInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Gard on 13.08.2017.
 */
public class Storage {
    private static final String COMPANY_LIST_KEY = "Company List";

    public Storage() {
    }

    public void storeCompanies(List companies) {
        JSONObject obj = new JSONObject();
        JSONArray companyList = new JSONArray();
        companyList.addAll(companies);
        obj.put(COMPANY_LIST_KEY, companyList);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(SystemInfo.STORAGE_PATH)) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStoredCompanies() {
        JSONParser parser = new JSONParser();
        List<String> returnListOfCompanies;

        try {
            Object obj = parser.parse(new FileReader(SystemInfo.STORAGE_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray companyList = (JSONArray) jsonObject.get(COMPANY_LIST_KEY);
            returnListOfCompanies = (List<String>) companyList;
            for (Object object : companyList) {
                System.out.println((String) object);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return returnListOfCompanies;
    }
}
