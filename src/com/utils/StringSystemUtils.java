package com.utils;

/**
 *  @author linxiaobai
 * @date 2020
 */
public class StringSystemUtils {
    /**
     * 字符串不为null的时候去空格,为null就返回null
     */
    public static String strNotNullTrim(String str) {
        return  str==null ? null : str.trim();
    }
}
