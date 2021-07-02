package top.monoliths.slight_server;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.regex.Pattern;

import top.monoliths.slight_server.kernel.ConfigData;
import top.monoliths.slight_server.kernel.ResponseRule;
import top.monoliths.slight_server.kernel.ResponseRulesMap;
import top.monoliths.slight_server.server.HttpServer;

/**
 * slight server
 *
 * @author monoliths
 * @version 1.0.0
 */
public final class App {

    private static final String configPath = "./config/web-config.json";
    private static final String rulePath = "./config/response-rule.json";

    public static ConfigData initializeConfig() {
        ConfigData result = null;
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(configPath)))) {
            StringBuffer data = new StringBuffer();
            bufferedReader.lines().forEach(line -> {
                // filter special characters
                String textLine = Pattern.compile("\\s*|\t|\r|\n|　").matcher(line).replaceAll("");
                // if line not null
                if (textLine != null && !textLine.equals("")) {
                    // add text to list
                    data.append(textLine);
                }
            });

            /**
             * load config data inctance
             */
            Gson gson = new Gson();
            result = gson.fromJson(data.toString(), ConfigData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * initialize responses map
     * 
     * @return
     */
    public static ResponseRulesMap initializeResponseMap() {
        ResponseRulesMap result = new ResponseRulesMap();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(rulePath)))) {
            StringBuffer data = new StringBuffer();
            bufferedReader.lines().forEach(line -> {
                // if line not null
                if (line != null && !line.equals("")) {
                    // add data to list
                    data.append(line);
                }
            });

            /**
             * load config data inctance
             */
            Gson gson = new Gson();

            ResponseRule[] responseHeads = gson.fromJson(data.toString(), ResponseRule[].class);
            // traverse head data
            for (ResponseRule responseHead : responseHeads) {
                if (responseHead != null && responseHead.getExtensionName() != null) {
                    result.put(responseHead.getExtensionName(), responseHead);
                } else {
                    System.out.println("ignore rule:" + responseHead.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try {
            ConfigData configData;
            ResponseRulesMap responseRuls;

            // load rules
            responseRuls = initializeResponseMap();
            System.out.println("-------------load rules-------------\n\r");
            System.out.println(responseRuls.toString());

            // load web config
            configData = initializeConfig();
            System.out.println("-----------load web config----------\n\r");
            System.out.println(configData.toString() + "\n\r");

            // get HttpServer Inctance
            HttpServer server = new HttpServer(configData, responseRuls);
            // start server
            server.start();

        } catch (Exception e) {
            // print exception
            e.printStackTrace();
        }
    }
}
