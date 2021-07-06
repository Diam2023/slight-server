package top.monoliths.slight.server.kernel;

import top.monoliths.slight.server.io.PriorityBufferNode;

/**
 * this class will help to reduce file io pressure.
 *
 * @author monoliths
 * @version 1.2.0
 * @since 1.2.0
 */
public class PriorityBufferListPool {
    /**
     * the maximun buffer size of the pool.
     */
    protected static final int MAX_BUFFER_SIZE = 1024 * 1024 * 10;
    /**
     * the minimum pool size.
     */
    protected static final int MAX_POOL_SIZE = 1024 * 1024 * 1024;
    /**
     * the maximum simple file size.
     */
    protected static final int MAX_SIMPLE_BUFFER_SIZE = 1024 * 1024;
    /**
     *
     */
    @SuppressWarnings("unused")
    private PriorityBufferNode[] buffers;

    /**
     *
     */
    public PriorityBufferListPool() {

    }
}
