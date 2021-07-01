package top.monoliths.slight_server;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import top.monoliths.slight_server.kernel.ConfigData;
import top.monoliths.slight_server.server.HttpServer;

/**
 * slight server
 * @author <a href="mailto:https://monoliths-uni/github.com>monoliths</a>"
 * @version 1.0.0
 */
public final class App {

    private static final String configPath = "./web-config.json";

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try (
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(configPath)))
        ) {
            StringBuffer data = new StringBuffer();
            bufferedReader
                .lines()
                .forEach(
                    line -> {
                        // filter special characters
                        String textLine = Pattern.compile("\\s*|\t|\r|\n|　").matcher(line).replaceAll("");
                        // if line not null
                        if (textLine != null && !textLine.equals("")) {
                            // add text to list
                            data.append(textLine);
                        }
                    }
                );

            /**
             * load config data inctance
             */
            Gson gson = new Gson();
            ConfigData configData = gson.fromJson(data.toString(), ConfigData.class);
            /**
             * get HttpServer Inctance
             */
            HttpServer server = new HttpServer(configData);
            // start server
            server.start();
        } catch (Exception e) {
            // print exception
            e.printStackTrace();
        }
    }
}
