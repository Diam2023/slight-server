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

    public String getCharset() {
        return charset;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public String getHead() {
        return head;
    }

    public void setBinary(boolean binary) {
        this.binary = binary;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();

        result.append("extension name: " + getExtensionName());
        result.append("head: " + getHead());
        result.append("charset: " + getCharset());

        return result.toString();
    }
}
