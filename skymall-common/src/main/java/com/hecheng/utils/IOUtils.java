package com.hecheng.utils;

import java.io.Closeable;
import java.io.IOException;

/** 
 * 用于关闭流对象
 */
public class IOUtils {

	/** 
     * 关闭一个或多个流对象 
     *  
     * @param closeables 
     *            可关闭的流对象列表 
     * @throws IOException 
     */  
    private static void close(Closeable... closeables) throws IOException {  
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        }
    }
   
    /** 
     * 关闭一个或多个流对象 
     *  
     * @param closeables 
     *            可关闭的流对象列表 
     */  
    public static void closeQuietly(Closeable... closeables) {
        try {
        	
            close(closeables);
        } catch (IOException e) {
            // do nothing
        }
    }
    
}
