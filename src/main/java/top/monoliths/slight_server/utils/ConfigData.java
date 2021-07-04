package top.monoliths.slight_server.utils;

/**
 * ConfigData
 * 
 * @author monoliths
 * @since 1.0.0
 * @version 1.0.0
 */
public class ConfigData {
    
    /**
     * localhost
     */
    private String local;

    /**
     * web port
     */
    private int port;

    /**
     * local server web root
     */
    private String configPath;

    /**
     * request local to requery this file
     */
    private String home;

    /**
     * output
     */
    public boolean debug;

    /**
     * set configPath
     * @param configPath
     */
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    /**
     * set home
     * @param home
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * set local
     * @param local
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * set port
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * set debug
     * @param debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * @return configPath String
     */
    public String getConfigPath() {
        return configPath;
    }

    /**
     * 
     * @return home STring
     */
    public String getHome() {
        return home;
    }

    /**
     * return local
     * 
     * @return local String
     */
    public String getLocal() {
        return local;
    }

    /**
     * return port
     * @return port int
     */
    public int getPort() {
        return port;
    }

    /**
     * to set values
     * 
     * @param local
     * @param port
     * @param configPath
     * @param home
     */
    public ConfigData(String local, int port, String configPath, String home, boolean debug) {
        setConfigPath(configPath);
        setHome(home);
        setLocal(local);
        setPort(port);
        setDebug(debug);
    }
    
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("server config \n\r");
        result.append("| local: " + getLocal() + "\n\r");
        result.append("| port: " + getPort() + "\n\r");
        result.append("| configPath: " + getConfigPath() + "\n\r");
        result.append("| home: " + getHome() + "\n\r");
        return result.toString();
    }
}
