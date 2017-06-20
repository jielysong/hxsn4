package com.hxsn.library.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ************************************************************************************************************
 *
 * @version 0.1
 *          *************************************************************************************************************
 * @author：jiely Date：2015-2-4
 * Description: 异常工具类，所有异常都调用该类的方法，方便与维护
 * Company：中交讯通
 */
public class ExceptionUtil {

    public static void handle(Exception e) {
        //String detail=e.getMessage();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        //显示在logcat上，
        e.printStackTrace();

        //把异常发送http,socket,邮件给开发人员
        //发邮件有三种方法，推荐用javamail
    }

}
