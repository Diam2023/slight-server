package top.monoliths.slight.server.kernel;

import com.google.gson.Gson;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import top.monoliths.slight.server.server.HttpServer;
import top.monoliths.slight.server.utils.ConfigData;
import top.monoliths.slight.server.utils.ResponseRule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * load config and rules and <br>
 * to start http server.
 * 
 * @author monoliths
 */
public class SlightServerApplication {
    /**
     * get LOG.
     */
    private static final Log LOG = LogFactory.getLog(SlightServerApplication.class);

    /**
     * deffine config path.
     */
    protected static final String DEFAULT_CONFIG_PATH = "./config/web-config.json";

    /**
     * deffine rules path.
     */
    protected static final String DEFAULT_RULES_PATH = "./config/response-rule.json";

    protected static final String CONFIG_COMMAND = "-c";

    protected static final String RULES_COMMAND = "-r";

    /**
     * pattern.
     */
    protected static final Pattern PT = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * config data
     */
    public static ConfigData configData;

    /**
     * response rules data.
     */
    public static ResponseRulesMap responseRules;

    /**
     * initialize config data.
     *
     * @param cp config path
     * @return {@code null} if not found or can not read<br>
     *         else {@code configData} true
     */
    public static ConfigData initializeConfig(final String cp) {
        ConfigData result = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cp)))) {
            StringBuffer data = new StringBuffer();
            bufferedReader.lines().forEach(line -> {
                // filter special characters
                final String textLine;
                textLine = PT.matcher(line).replaceAll("");
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
     * initialize responses map.
     *
     * @param rp rules path
     * @return {@code null} if not found or can not read<br>
     *         {@code RespomseRulesMap}
     */
    public static ResponseRulesMap initializeResponseMap(final String rp) {

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

                    LOG.warn("\n\r ignore rule:" + responseHead.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * launch slight server.
     * 
     * @param args The arguments of the program.
     * @throws Exception Exception
     */
    public static void launch(final String[] args) throws Exception {

        String rulesPath = DEFAULT_RULES_PATH;
        String configPath = DEFAULT_CONFIG_PATH;

        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));

        Iterator<String> argsIterator = argsList.iterator();

        while (argsIterator.hasNext()) {
            String arg = argsIterator.next();
            if (CONFIG_COMMAND.equals(arg) == true) {
                String path = argsIterator.next();
                if ("" != path) {
                    rulesPath = path;
                } else {
                    throw new IllegalArgumentException("web-config.json can not null!");
                }
            } else if (RULES_COMMAND.equals(arg) == true) {
                String path = argsIterator.next();
                if ("" != path) {
                    rulesPath = path;
                } else {
                    throw new IllegalArgumentException("responsse-rule.json can not null!");
                }
            }
        }

        // if exist
        File rulesFile = new File(rulesPath);
        File configFile = new File(configPath);

        if (!rulesFile.exists() || !configFile.exists()) {
            throw new IllegalArgumentException("rules file or config file not exists.");
        }

        // load rules
        responseRules = initializeResponseMap(rulesPath);

        // load web config
        configData = initializeConfig(configPath);

        if (configData.getDebug()) {

            LOG.debug("\n\r -------------load rules------------- \n" + responseRules.toString());

            LOG.debug("\n\r -----------load web config----------- \n" + configData.toString());

        }
        // get HttpServer Inctance
        HttpServer server = new HttpServer(configData, responseRules);

        // output LOGO
        LOG.info("\n\r \n------------------------------------------------\n"
                + "---------------- SLIGHT  SERVER ----------------\n"
                + "------------------------------------------------\n");

        LOG.info("\n\r \n ------------------------------------------------ \n" + "server: http://"
                + configData.getLocal() + ":" + configData.getPort() + "/" + configData.getHome()
                + "\n ------------------------------------------------");
        // start server
        server.start();
    }
}
