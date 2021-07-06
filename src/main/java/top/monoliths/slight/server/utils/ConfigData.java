package top.monoliths.slight.server.utils;

/**
 * config data of the server.
 *
 * @author monoliths
 * @since 1.0.0
 * @version 1.0.0
 */
public class ConfigData {

    /**
     * localhost.
     */
    private String local;

    /**
     * web port.
     */
    private int port;

    /**
     * local server web root.
     */
    private String configPath;

    /**
     * request local to requery this file.
     */
    private String home;

    /**
     * output debug message if {@boolean debug} is true.
     */
    private boolean debug;

    /**
     * set configPath.
     *
     * @param cp config path
     */
    public void setConfigPath(final String cp) {
        this.configPath = cp;
    }

    /**
     * set local host file name.
     *
     * @param hm local host file name
     */
    public void setHome(final String hm) {
        this.home = hm;
    }

    /**
     * set local host name.
     *
     * @param lc host name
     */
    public void setLocal(final String lc) {
        this.local = lc;
    }

    /**
     * set port.
     *
     * @param pt set port of server
     */
    public void setPort(final int pt) {
        this.port = pt;
    }

    /**
     * set debug.
     *
     * @param db is debug mode
     */
    public void setDebug(final boolean db) {
        this.debug = db;
    }

    /**
     * @return configPath String
     */
    public String getConfigPath() {
        return configPath;
    }

    /**
     * return home.
     *
     * @return home String
     */
    public String getHome() {
        return home;
    }

    /**
     * return local.
     *
     * @return local String
     */
    public String getLocal() {
        return local;
    }

    /**
     * return port.
     *
     * @return port int
     */
    public int getPort() {
        return port;
    }

    /**
     * return mode of setting.
     * 
     * @return if debug mode return {@value true}
     */
    public boolean getDebug() {
        return debug;
    }

    /**
     * to set values.
     *
     * @param lc host name
     * @param pt local host server listen port
     * @param cp config server position
     * @param hm the file top of config path
     * @param db debug mode
     */
    public ConfigData(final String lc, final int pt, final String cp, final String hm, final boolean db) {
        setConfigPath(cp);
        setHome(hm);
        setLocal(lc);
        setPort(pt);
        setDebug(db);
    }

    @Override
    public final String toString() {
        StringBuffer result = new StringBuffer();
        result.append("server config \n\r");
        result.append("| local: " + getLocal() + "\n\r");
        result.append("| port: " + getPort() + "\n\r");
        result.append("| configPath: " + getConfigPath() + "\n\r");
        result.append("| home: " + getHome() + "\n\r");
        result.append("| debug: " + getDebug() + "\n\r");
        return result.toString();
    }
}
