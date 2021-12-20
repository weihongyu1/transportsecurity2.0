package com.why.transportsecurity.utils.passwordutils;

import java.util.Date;
import java.util.Random;

/**
 * @ClassName：RandomUtils
 * @Description：todo
 * @Author: why
 * @DateTime：2021/12/13 19:26
 */
public class RandomUtils {
    /**
     * 产生随机数
     * @return
     */
    public static String getRandom(){
        Date date = new Date();
        Random random = new Random(date.getTime());
        int i = random.nextInt(1000000);
        return i+"";
    }
}
