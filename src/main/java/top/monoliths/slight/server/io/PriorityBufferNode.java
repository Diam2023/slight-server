package top.monoliths.slight.server.io;

import io.netty.buffer.ByteBuf;
import top.monoliths.slight.server.utils.ResponseRule;

/**
 * to save info and data of file.
 *
 * @author monoliths
 */
public class PriorityBufferNode {
    /**
     * buffer information.
     */
    private ResponseRule bufferInfo;

    /**
     * content of Buffer data.
     */
    private ByteBuf content;

    /**
     * config file information.
     *
     * @param info to initialize info
     */
    public void setBufferInfo(final ResponseRule info) {
        this.bufferInfo = info;
    }

    /**
     * initialize data.
     *
     * @param data to initialize buffer
     */
    public void setContent(final ByteBuf data) {
        this.content = data;
    }

    /**
     * get data info.
     *
     * @return ByteBuf content
     */
    public ResponseRule getBufferInfo() {
        return bufferInfo;
    }

    /**
     * return data.
     *
     * @return ByteBuf content
     */
    public ByteBuf getContent() {
        return content.copy();
    }

    /**
     * set information and data file.
     * 
     * @param info file information
     * @param data file buffer
     */
    PriorityBufferNode(final ResponseRule info, final ByteBuf data) {
        setBufferInfo(info);
        setContent(data);
    }
}
