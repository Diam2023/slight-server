package top.monoliths.slight_server.kernel;

import com.google.gson.Gson;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.regex.Pattern;

import top.monoliths.slight_server.kernel.SlightServerApplication;
import top.monoliths.slight_server.server.HttpServer;

public class SlightServerApplication {

    private static final Log LOG = LogFactory.getLog(SlightServerApplication.class);

    /**
     * deffine config path
     */
    protected static final String CONFIGPATH = "./config/web-config.json";
    protected static final String RULEPATH = "./config/response-rule.json";

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
     * @param rp
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
                    LOG.info("\n\r ignore rule:" + responseHead.toString());
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
        responseRuls = initializeResponseMap(RULEPATH);
        LOG.info("\n\r -------------load rules------------- \n" + responseRuls.toString());

        // load web config
        configData = initializeConfig(CONFIGPATH);
        LOG.info("\n\r -----------load web config----------- \n" + configData.toString());
 
        // get HttpServer Inctance
        HttpServer server = new HttpServer(configData, responseRuls);
        
        LOG.info("\n\r server: http://" + configData.getLocal() + ":" + configData.getPort() + "/" + configData.getHome());
        // start server
        server.start();
    }
}
