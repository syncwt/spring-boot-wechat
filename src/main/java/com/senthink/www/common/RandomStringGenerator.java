package com.senthink.www.common;

import java.util.Random;

/**
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.util
 * @Description : 获取随机长度的字符串
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午5:41
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class RandomStringGenerator {

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
