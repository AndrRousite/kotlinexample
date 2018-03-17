package me.letion.geetionlib.vender.log;

import android.text.TextUtils;
import android.util.Log;

import me.letion.geetionlib.BuildConfig;

/**
 * Created by liu-feng on 2017/6/6.
 */
public class L {

    public static void json(String json) {
        json(Log.DEBUG, null, json);
    }

    public static void json(int logLevel, String tag, String json) {
        if (BuildConfig.DEBUG) {
            String formatJson = LogFormat.formatBorder(new String[]{LogFormat.formatJson(json)});
            LogPrinter.println(logLevel, TextUtils.isEmpty(tag) ? BuildConfig.LOG_TAG : tag, formatJson);
        }
    }

    public static void xml(String xml) {
        xml(Log.DEBUG, null, xml);
    }


    public static void xml(int logLevel, String tag, String xml) {
        if (BuildConfig.DEBUG) {
            String formatXml = LogFormat.formatBorder(new String[]{LogFormat.formatXml(xml)});
            LogPrinter.println(logLevel, TextUtils.isEmpty(tag) ? BuildConfig.LOG_TAG : tag, formatXml);
        }
    }

    public static void error(Throwable throwable) {
        error(null, throwable);
    }

    public static void error(String tag, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            String formatError = LogFormat.formatBorder(new String[]{LogFormat.formatThrowable(throwable)});
            LogPrinter.println(Log.ERROR, TextUtils.isEmpty(tag) ? BuildConfig.LOG_TAG : tag, formatError);
        }
    }

    private static void msg(int logLevel, String tag, String format, Object... args) {
        if (BuildConfig.DEBUG) {
            String formatMsg = LogFormat.formatBorder(new String[]{LogFormat.formatArgs(format, args)});
            LogPrinter.println(logLevel, TextUtils.isEmpty(tag) ? BuildConfig.LOG_TAG : tag, formatMsg);
        }
    }

    public static void d(String msg, Object... args) {
        msg(Log.DEBUG, null, msg, args);
    }

    public static void d(String tag, String msg, Object... args) {
        msg(Log.DEBUG, tag, msg, args);
    }

    public static void e(String msg, Object... args) {
        msg(Log.ERROR, null, msg, args);
    }

    public static void e(String tag, String msg, Object... args) {
        msg(Log.ERROR, tag, msg, args);
    }
}
