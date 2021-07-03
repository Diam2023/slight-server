package top.monoliths.slight_server.kernel;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.regex.Pattern;

import top.monoliths.slight_server.kernel.SlightServerApplication;
import top.monoliths.slight_server.server.HttpServer;

public class SlightServerApplication {
    /**
     * deffine config path
     */
    protected static final String configPath = "./config/web-config.json";
    protected static final String rulePath = "./config/response-rule.json";

    /**
     * initialize config data
     * 
     * @return {@code null} if not found or can not read, {@code configData} true set
     */
    public static ConfigData initializeConfig(String cp) {
        ConfigData result = null;
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(cp)))) {
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
     * @return {@code null} if not found or can not read, {@code RespomseRulesMap} true set
     */
    public static ResponseRulesMap initializeResponseMap(String rp) {
        ResponseRulesMap result = new ResponseRulesMap();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(rp)))) {
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

    public static void launch(String[] args) throws Exception {

        ConfigData configData;
        ResponseRulesMap responseRuls;

        // load rules
        responseRuls = initializeResponseMap(rulePath);
        System.out.println("-------------load rules-------------\n\r");
        System.out.println(responseRuls.toString());

        // load web config
        configData = initializeConfig(configPath);
        System.out.println("-----------load web config----------\n\r");
        System.out.println(configData.toString() + "\n\r");

        // get HttpServer Inctance
        HttpServer server = new HttpServer(configData, responseRuls);
        
        System.out.println(
                "server: http://" + configData.getLocal() + ":" + configData.getPort() + "/" + configData.getHome());
        // start server
        server.start();
    }
}
