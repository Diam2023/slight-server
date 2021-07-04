package top.monoliths.slight_server.kernel;

/**
 * this class will help to reduce file io pressure
 * 
 * @author monoliths
 */
public class PriorityBufferListPool {
    protected static final int MAX_Buffer_SIZE = 1024 * 1024 * 10;
    protected static final int MAX_POOL_SIZE = 1024 * 1024 * 1024;
    
    protected static final int MAX_SIMPLE_Buffer_SIZE = 1024 * 1024;
}
