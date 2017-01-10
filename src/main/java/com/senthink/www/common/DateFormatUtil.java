package com.senthink.www.common;

import com.mysql.jdbc.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.util
 * @Description :  时间转换工具类
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午5:16
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class DateFormatUtil {
    public static String getCurrentTime() {
        Date dt = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginTime = df.format(dt);
        return loginTime;
    }

    public static Date getStartTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginTime = df.format(new Date());
        try {
            return df.parse(loginTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getConversDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date ConversDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getCurrentDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String loginTime = df.format(date);
        return loginTime;
    }

    public static int getDayNum(Date addTime) {
        double longTime = System.currentTimeMillis() - addTime.getTime();
        int dayNum = (int) (((longTime / 1000) / 3600) / 24);
        return dayNum;
    }

    public static int getHoursNum(Date addTime) {
        double longTime = System.currentTimeMillis() - addTime.getTime();
        int dayNum = (int) (((longTime / 1000) / 3600));
        return dayNum;
    }

    public static String getFileName() {
        Date dt = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String loginTime = df.format(dt);
        return loginTime;
    }

    public static String gettime_start() {
        Date dt = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String loginTime = df.format(dt);
        return loginTime;
    }

    public static String getFiledir() {
        Date dt = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String loginTime = df.format(dt);
        return loginTime;
    }

    public static String gethms() {
        Date dt = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("HHmmss");
        String loginTime = df.format(dt);
        return loginTime;
    }

    /**
     * TODO 将时间单位值转换为对应的数字值
     * author: chenboda
     * createtime:2015-3-3
     * params:
     */
    public static int getUnint(String unint) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("年", Calendar.YEAR);
        map.put("月", Calendar.MONTH);
        map.put("天", Calendar.DATE);
        map.put("周", Calendar.WEDNESDAY);
        map.put("小时", Calendar.HOUR);
        map.put("分钟", Calendar.MINUTE);
        map.put("秒", Calendar.SECOND);
        return map.get(unint);
    }


    /**
     * TODO 日期及时间计算
     * author: chenboda
     * createtime:2015-3-3
     * params:date为要计算的时间   day为正数则表示加多少天 ,day为负数时表示减多少天  ，unint为计算的单位 Calendar.HOUR为小时  Calendar.DATE为天 Calendar.MONTH为月 Calendar.YEAR为年
     */
    public static String getQuarters(Date date, int day, int unint) {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gc.setTime(date);
        gc.add(unint, day);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
        return sf.format(gc.getTime());
    }

    /**
     * TODO 日期及时间计算
     * author: chenboda
     * createtime:2015-3-3
     * params:date为要计算的时间   day为正数则表示加多少天 ,day为负数时表示减多少天  ，unint为计算的单位 Calendar.HOUR为小时  Calendar.DATE为天 Calendar.MONTH为月 Calendar.YEAR为年
     */
    public static Date addDate(Date date, int day, int unint) {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gc.setTime(date);
        gc.add(unint, day);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
        return gc.getTime();
    }

    /**
     * 将秒转换为时间格式：hh:mm:ss
     *
     * @author Administrator
     */
    public static String secondTo(int time) {
        String times = null;
        SimpleDateFormat format = null;
        int num;
        if (time < 3600) {
            num = time;
            format = new SimpleDateFormat("mm:ss");
        } else {
            num = time - 8 * 3600;
            format = new SimpleDateFormat("hh:mm:ss");
        }
        times = format.format(new Date(num * 1000L));
        return times;
    }

    /**
     * 将时间字符串转为秒
     *
     * @param _str
     * @return
     */
    public static String convert(String _str) {
        String howlong = null;
        String str = _str.replace(" ", "");
        if (str.indexOf(":") >= 2) {
            howlong = str;
        } else if (str.indexOf(":") < 0) // 不存在：
        {
            if (!StringUtils.isNullOrEmpty(str)) {
                howlong = secondTo(Integer.parseInt(str));
            } else {
                howlong = secondTo(0);
            }
        } else {
            String times[] = str.split(":");
            int h = Integer.parseInt(times[0]);
            int m = Integer.parseInt(times[1]);
            int s = Integer.parseInt(times[2]);
            int time;
            if (h > 0) {
                time = h * 3600 + m * 60 + s;
            } else {
                time = m * 60 + s;
            }
            howlong = secondTo(time);
        }
        return howlong;
    }


    //将Unix时间戳转换成指定格式日期  time为unix的时间戳
    public static String UnixDateTowinodwxDate(String time) {
        if (time != null && !StringUtils.isNullOrEmpty(time)) {
            Long timestamp = Long.parseLong(time) * 1000;
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
            return date;
        }
        return "";
    }

    //将winodwx时间戳转换为uninx时间戳  date为unix的时间戳
    public static long winodwxDateToUnixDate(String date) {
        if (date != null && !StringUtils.isNullOrEmpty(date)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long time = 0l;
            try {
                time = df.parse(date).getTime() / 1000;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return time;
        }
        return 0;
    }

    public static String get6num() {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        String result = "";
        for (int i = 0; i < 6; i++)
            result = result + array[i];
        return result;
    }

    /**
     * @param day +加时间   -减时间 时间之前
     * @return
     */
    public static String getaddday(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rtime = "";
        try {
            Calendar newdate = new GregorianCalendar();
            newdate.add(Calendar.DATE, day);
            Date date = new Date(newdate.getTimeInMillis());
            rtime = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtime;
    }

    /**
     * 获取几年前的时间
     *
     * @param year
     * @return
     */
    public static String getaddyear(int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rtime = "";
        try {
            Calendar newdate = new GregorianCalendar();
            newdate.add(Calendar.YEAR, year);
            Date date = new Date(newdate.getTimeInMillis());
            rtime = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtime;
    }


    /**
     * 获取几年前的时间
     *
     * @param month
     * @return
     */
    public static String getaddmonth(int month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rtime = "";
        try {
            Calendar newdate = new GregorianCalendar();
            newdate.add(Calendar.MONTH, month);
            Date date = new Date(newdate.getTimeInMillis());
            rtime = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtime;
    }

    public static String setyearday(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rtime = "";
        try {
            Calendar newdate = new GregorianCalendar();
            //newdate.set(Calendar.MONTH, 1);
            newdate.set(Calendar.DAY_OF_YEAR, day);
            Date date = new Date(newdate.getTimeInMillis());
            rtime = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtime;
    }

    public static String setmonthday(int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rtime = "";
        try {
            Calendar newdate = new GregorianCalendar();
            newdate.set(Calendar.MONTH, month);
            newdate.set(Calendar.DAY_OF_MONTH, day);
            Date date = new Date(newdate.getTimeInMillis());
            rtime = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtime;
    }

    /**
     * 获取当前秒级unix时间戳
     *
     * @return
     */
    public static String getCurrentUnixTime() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(new Date().getTime()/1000);
        /*Date dt = new Date(System.currentTimeMillis()-7200000);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String loginTime = df.format(dt);
		System.out.println(winodwxDateToUnixDate(loginTime));*/
        //System.out.println(System.currentTimeMillis());
    /*	String dd = DateFormatUtil.setmonthday(1, 1);
         System.out.println(dd);*/
        String s = DateFormatUtil.UnixDateTowinodwxDate("1464935657");
        System.out.println(s);
    }

}
