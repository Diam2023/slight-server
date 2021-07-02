package top.monoliths.slight_server.kernel;

/**
 * to save response method tag message
 * 
 * this method will used to next version
 * 
 * @author monoliths
 * @since 1.1.0
 * @version 1.1.0
 */
@SuppressWarnings("unused")
public class ResponseHead {

    /**
     * request extension name of file type
     */
    private String extensionName;

    /**
     * if use binary then is true
     */
    private boolean binary;

    /**
     * encode UTF-8 or binary none
     */
    private String charset;

    /**
     * response data head
     */
    private String head;
}
