package com.clawhub.plugin.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * <Description> IoUtils <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/4 <br>
 */
public class IoUtils {

    /**
     * Close quietly.
     *
     * @param closeable the closeable
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
