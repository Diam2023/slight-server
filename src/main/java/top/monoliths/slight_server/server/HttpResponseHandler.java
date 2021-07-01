package top.monoliths.slight_server.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * to handle request
 *
 * @author <a href="mailto:https://monoliths-uni/github.com"
 * @version 1.0.0
 */
public class HttpResponseHandler {
    
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    public static FullHttpResponse response(FullHttpRequest req) {
        String head;
        String charset;
        ByteBuf body;
        HttpResponseStatus status;
        FullHttpResponse response;

        // default set
        charset = "; charset=UTF-8";
        head = "text/html" + charset;
        body = Unpooled.buffer();
        status = HttpResponseStatus.SERVICE_UNAVAILABLE;
        try {
            String path =
                (
                    ((req.uri()).equals("/"))
                        ? (HttpServer.configData.getConfigPath() + "/" + HttpServer.configData.getHome())
                        : (HttpServer.configData.getConfigPath() + URLDecoder.decode(req.uri(), "UTF-8"))
                );

            switch (path.substring(path.lastIndexOf("."))) {
                // byte
                case ".jpg":
                    head = "image/jpeg";
                    body = getFileByteBufByByte(path);
                    status = HttpResponseStatus.OK;
                    break;
                case ".png":
                    head = "image/png";
                    body = getFileByteBufByByte(path);
                    status = HttpResponseStatus.OK;
                    break;
                case ".ico":
                    // wait to updata
                    head = "image/x-icon";
                    body = getFileByteBufByByte(path);
                    status = HttpResponseStatus.OK;
                    break;
                // utf8
                case ".html":
                    charset = "; charset=UTF-8";
                    head = "text/html" + charset;
                    body = getFileByteBufByUTF8(path);
                    status = HttpResponseStatus.OK;
                    break;
                case ".css":
                    charset = "; charset=UTF-8";
                    head = "text/css" + charset;
                    body = getFileByteBufByUTF8(path);
                    status = HttpResponseStatus.OK;
                    break;
                case ".js":
                    charset = "; charset=UTF-8";
                    head = "application/javascript" + charset;
                    body = getFileByteBufByUTF8(path);
                    status = HttpResponseStatus.OK;
                    break;
                case ".json":
                    charset = "; charset=UTF-8";
                    head = "application/json" + charset;
                    body = getFileByteBufByUTF8(path);
                    status = HttpResponseStatus.OK;
                    break;
                default:
                    charset = "; charset=UTF-8";
                    head = "text/plain" + charset;
                    body = Unpooled.buffer();
                    status = HttpResponseStatus.SERVICE_UNAVAILABLE;
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            status = HttpResponseStatus.SERVICE_UNAVAILABLE;
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            status = HttpResponseStatus.NOT_FOUND;
            e.printStackTrace();
        } catch (EOFException e) {
            status = HttpResponseStatus.SERVICE_UNAVAILABLE;
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            status = HttpResponseStatus.SERVICE_UNAVAILABLE;
            e.printStackTrace();
        } catch (IOException e) {
            status = HttpResponseStatus.SERVICE_UNAVAILABLE;
            e.printStackTrace();
        }

        response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, body);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, head);

        return response;
    }

    
    /**
     * read data use utf-8 encode
     * 
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws EOFException
     * @throws IOException
     * @throws OutOfMemoryError
     */
    public static ByteBuf getFileByteBufByUTF8(String path)
            throws FileNotFoundException, EOFException, IOException, OutOfMemoryError {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("File " + path + "not found");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuffer data = new StringBuffer(bufferedReader.readLine());
        String line = new String();
        while ((line = bufferedReader.readLine()) != null) {
            data.append(line + "\n");
        }
        ByteBuf result = Unpooled.copiedBuffer(data.toString(), CharsetUtil.UTF_8);
        line = null;
        bufferedReader.close();
        return result;
    }

    /**
     * to response file data with ByteBuf
     * 
     * @param path file path
     * @return
     * @throws FileNotFoundException
     * @throws EOFException
     * @throws IOException
     * @throws OutOfMemoryError
     */
    public static ByteBuf getFileByteBufByByte(String path)
            throws FileNotFoundException, EOFException, IOException, OutOfMemoryError {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("File: " + path + " not found");
        }
        
        byte[] data = null;
        List<byte[]> bufs = null;
        byte[] byteResult = null;
        int total = 0;
        int remaining = Integer.MAX_VALUE;
        int n;

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));) {
            do {
                byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
                int nread = 0;
    
                // read to EOF which may read more or less than buffer size
                while ((n = bufferedInputStream.read(buf, nread,
                        Math.min(buf.length - nread, remaining))) > 0) {
                    nread += n;
                    remaining -= n;
                }
    
                if (nread > 0) {
                    if (MAX_BUFFER_SIZE - total < nread) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (nread < buf.length) {
                        buf = Arrays.copyOfRange(buf, 0, nread);
                    }
                    total += nread;
                    if (byteResult == null) {
                        byteResult = buf;
                    } else {
                        if (bufs == null) {
                            bufs = new ArrayList<>();
                            bufs.add(byteResult);
                        }
                        bufs.add(buf);
                    }
                }
                // if the last call to read returned -1 or the number of bytes
                // requested have been read then break
            } while (n >= 0 && remaining > 0);
    
            
            if (bufs == null) {
                if (byteResult == null) {
                    return Unpooled.copiedBuffer(new byte[0]);
                }
                return Unpooled.copiedBuffer(byteResult.length == total ? byteResult : Arrays.copyOf(byteResult, total));
            }
    
            byteResult = new byte[total];
            int offset = 0;
            remaining = total;
            for (byte[] b : bufs) {
                int count = Math.min(b.length, remaining);
                System.arraycopy(b, 0, byteResult, offset, count);
                offset += count;
                remaining -= count;
            }
            data = byteResult;

        } catch (Exception e) {
            throw e;
        }
        
        ByteBuf result = Unpooled.copiedBuffer(data);            
        return result;
    }

}
