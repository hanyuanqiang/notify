package com.app.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ExceptionUtil {
    public static String getStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer,true));
        return writer.toString();
    }
}
