package com.why.transportsecurity.utils.commonutils;

/**
 * @ClassName：EmptyUtils
 * @Description：todo 判空工具类
 * @Author: why
 * @DateTime：2021/12/12 21:08
 */
public class EmptyUtils {

    /**
     * 判空
     * @param args
     * @return
     */
    public static boolean isEmpty(Object... args){
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null){
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串类型判空
     * @param args
     * @return
     */
    public static boolean isStringEmpty(String... args){
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null || args[i].equals("")){
                return true;
            }
        }
        return false;
    }
}
