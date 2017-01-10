package com.senthink.www.util;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.text.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 数据类型转换帮助器
 */
public final class ConvertHelper {

    // ------------------------ 数值类型数据转换 ------------------------

    /**
     * 把数据转换为布尔型（默认返回 false）
     *
     * @param value
     *            需要转换的值对象
     * @return 尔型值
     */
    public static boolean toBoolean(Object value) {
        return toBoolean(value, false);
    }

    /**
     * 把数据转换为布尔型
     *
     * @param value
     *            需要转换的值对象
     * @param defaultValue
     *            默认值
     * @return 尔型值
     */
    public static boolean toBoolean(Object value, boolean defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Boolean){
            return (Boolean)value;
        }
        else if (value instanceof Integer){
            return (Integer)value == -1;
        }
        else{
            String ret = value.toString();
            if (ret.length() == 0){
                return defaultValue;
            }
            else{
                return (ret.toLowerCase().equals("true"));
            }
        }
    }

    /**
     * 把数据转换为整形（默认返回 0）
     *
     * @param value
     *            需要转换的值对象
     * @return 整形数值
     */
    public static int toInt(Object value) {
        return toInt(value, 0, 10);
    }

    /**
     * 把数据转换为整形（值转换）
     *
     * @param value
     *            需要转换的值对象
     * @param defaultValue
     *            默认值
     * @return 整形数值
     */
    public static int toInt(Object value, int defaultValue) {
        return toInt(value, defaultValue, 10);
    }

    /**
     * 把数据转换为整形（值转换）
     *
     * @param value
     *            需要转换的值对象
     * @param defaultValue
     *            默认值
     * @param radix
     *            进制
     * @return 整形数值
     */
    public static int toInt(Object value, int defaultValue, int radix) {
        if (value == null) return defaultValue;
        if (value instanceof Integer){
            return (Integer)value;
        }
        else{
            String s = value.toString();
            if (s.length() == 0){
                return defaultValue;
            }
            else{
                try{
                    return Integer.parseInt(s, radix);
                }
                catch (Exception e) {}
                return defaultValue;
            }
        }
    }

    /**
     * 把数据转换为短整形（默认返回 0）
     *
     * @param value
     *            需要转换的值对象
     * @return 短整形数值
     */
    public static short toShort(Object value) {
        return toShort(value, (short)0, 10);
    }

    /**
     * 把数据转换为短整形（值转换）
     *
     * @param value
     *            需要转换的值对象
     * @param defaultValue
     *            默认值
     * @return 短整形数值
     */
    public static short toShort(Object value, short defaultValue) {
        return toShort(value, defaultValue, 10);
    }

    /**
     * 把数据转换为短整形（值转换）
     *
     * @param value
     *            需要转换的值对象
     * @param defaultValue
     *            默认值
     * @param radix
     *            进制
     * @return 短整形数值
     */
    public static short toShort(Object value, short defaultValue, int radix) {
        if (value == null) return defaultValue;
        if (value instanceof Short){
            return (Short)value;
        }
        else{
            String s = value.toString();
            if (s.length() == 0){
                return defaultValue;
            }
            else{
                try{
                    return Short.valueOf(s, radix);
                }
                catch (Exception e) {}
                return defaultValue;
            }
        }
    }

    /**
     * 把数据转换为长整数（默认返回 0）
     *
     * @param value
     *            源数据
     * @return long 取得的数值
     */
    public static long toLong(Object value) {
        return toLong(value, 0L);
    }

    /**
     * 把数据转换为长整数
     *
     * @param value
     *            源数据
     * @param defaultValue
     *            默认数值
     * @return long 取得的数值
     */
    public static long toLong(Object value, long defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Long){
            return (Long)value;
        }
        else{
            String s = value.toString();
            if (s.length() == 0){
                return defaultValue;
            }
            else{
                try{
                    return Double.valueOf(s).longValue();
                }
                catch (Exception e) {}
                return defaultValue;
            }
        }
    }

    /**
     * 把数据转换为浮点数（默认返回 0）
     *
     * @param value
     *            源数据
     * @return float 浮点数
     */
    public static float toFloat(Object value) {
        return toFloat(value, 0F);
    }

    /**
     * 把数据转换为浮点数
     *
     * @param value
     *            源数据
     * @param defaultValue
     *            默认数值
     * @return float 浮点数
     */
    public static float toFloat(Object value, float defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Float){
            return (Float)value;
        }
        else{
            String s = value.toString();
            if (s.length() == 0){
                return defaultValue;
            }
            else{
                try{
                    return Double.valueOf(s).floatValue();
                }
                catch (Exception e) {}
                return defaultValue;
            }
        }
    }

    /**
     * 把数据转换为双精度数（默认返回 0）
     *
     * @param value
     *            源数据
     * @return double 双精度数
     */
    public static double toDouble(Object value) {
        return toDouble(value, 0D);
    }

    /**
     * 把数据转换为双精度数
     *
     * @param value
     *            源数据
     * @param defaultValue
     *            默认数值
     * @return double 双精度数
     */
    public static double toDouble(Object value, double defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Double){
            return (Double)value;
        }
        else{
            String s = value.toString();
            if (s.length() == 0){
                return defaultValue;
            }
            else{
                try{
                    return Double.valueOf(s).doubleValue();
                }
                catch (Exception e) {}
                return defaultValue;
            }
        }
    }

    /**
     * 把数据转换为字节值（默认返回 0）
     *
     * @param value
     *            数据数值
     * @return 字节值
     */
    public static byte toByte(Object value) {
        return toByte(value, (byte)0, 10);
    }

    /**
     * 把数据转换为字节值（值转换）
     *
     * @param value
     *            数据数值
     * @param defaultValue
     *            默认值
     * @return 字节值
     */
    public static byte toByte(Object value, byte defaultValue) {
        return toByte(value, defaultValue, 10);
    }

    /**
     * 把数据转换为字节值（值转换）
     *
     * @param value
     *            数据数值
     * @param defaultValue
     *            默认值
     * @param radix
     *            进制
     * @return 字节值
     */
    public static byte toByte(Object value, byte defaultValue, int radix) {
        if (value == null) return defaultValue;
        if (value instanceof Byte || value instanceof Integer){
            return Byte.valueOf(value.toString());
        }
        else{
            String s = value.toString();
            if (s.length() == 0){
                return defaultValue;
            }
            else{
                try{
                    byte c=(byte) Integer.parseInt(s, radix);
                    return  c;
                }
                catch (Exception e) {}
                return defaultValue;
            }
        }
    }

    /**
     * 把数据转换为字节数组（数组转换）
     *
     * @param value
     *            数据数值
     * @param radix
     *            进制
     * @return 字节值
     */
    public static byte[] toByteArray(String value,int radix){
        byte[] byteArrays = new byte[value.length()/2];
        int j = 0;
        StringBuffer buf = new StringBuffer(2);
        for(int i = 0;i<value.length();i++,j++){
            buf.insert(0, value.charAt(i));
            buf.insert(1, value.charAt(i+1));
            int t = Integer.parseInt(buf.toString(),radix);
            byteArrays[j] = (byte)t;
            i++;
            buf.delete(0, 2);
        }

        return byteArrays;
    }


    /**
     * 判断两个byte数组是否完全相同
     *
     * @param firstByteArray
     *            数据数值
     * @param secondByteArray
     *            进制
     * @return 字节值
     */
    public static boolean isSameByteArray(byte[] firstByteArray,byte[] secondByteArray){
        if(Arrays.equals(firstByteArray, secondByteArray))
            return true;
        else
            return false;
    }


    public static boolean isSameByte(byte byte1,byte byte2){
        int a = byte1&0xff;
        int b = byte2&0xff;
        if(a != b)
            return false;
        else
            return true;
    }


    /**
     * 根据指定格式把日期数据转换为日期类型 （无效则返回为 null）
     *
     * @param value
     *            字符型日期
     * @param format
     *            格式（"yyyy-MM-dd HH:mm:ss.SSS"）
     * @return Date 日期
     */
    public static Date toDate(String value, String format) {
        return toDate(value, format, null);
    }

    /**
     * 根据指定格式把日期数据转换为日期类型
     *
     * @param value
     *            字符型日期
     * @param format
     *            格式（"yyyy-MM-dd HH:mm:ss.SSS"）
     * @param defaultValue
     *            默认日期
     * @return Date 日期
     */
    public static Date toDate(String value, String format, Date defaultValue) {
        try {
            final DateFormat df = new SimpleDateFormat(format);
            ParsePosition pp = new ParsePosition(0);
            return df.parse(value, pp);
        }
        catch (final Exception e) {}
        return defaultValue;
    }

    /**
     * 把日期格式化为制定格式字符串（转换失败则返回 null）
     *
     * @param date
     *            日期
     * @param format
     *            格式（yyyy-MM-dd HH:mm:ss.SSS）
     * @return String 字符型日期
     */
    public static String toString(Date date, String format) {
        try {
            if (date != null) {
                DateFormat df = new SimpleDateFormat(format);
                return df.format(date);
            }
        }
        catch (Exception e) {}
        return null;
    }

    /**
     * 把数值转换为格式化字符串
     *
     * @param number
     *            数值
     * @param format
     *            String 格式化字符串<br/>
     *            #: 表示有数字则输出数字，没有则留空；<br/>
     *            0: 表示有数字则输出数字，没有补0 对于小数，有几个＃或0，就保留几位的小数； <br/>
     *            例如：<br/>
     *            "###.00"<br/>
     *            -->表示输出的数值保留两位小数，不足两位的补0，多于两位的四舍五入 "###.0#"<br/>
     *            -->表示输出的数值可以保留一位或两位小数；整数显示为有一位小数，一位或两位小数的按原样显示，多于两位的四舍五入；<br/>
     *            "###"<br/>
     *            --->表示为整数，小数部分四舍五入 ".###" -->12.234显示为.234 "#,###.0#"<br/>
     *            -->表示整数每隔3位加一个"，";<br/>
     * @return String 格式化后的字符串
     */
    public static String toString(float number, String format) {
        return toString((double) number, format);
    }

    /**
     * 把数值转换为格式化字符串
     *
     * @param number
     *            数值
     * @param format
     *            String 格式化字符串<br/>
     *            #: 表示有数字则输出数字，没有则留空；<br/>
     *            0: 表示有数字则输出数字，没有补0 对于小数，有几个＃或0，就保留几位的小数； <br/>
     *            例如：<br/>
     *            "###.00"<br/>
     *            -->表示输出的数值保留两位小数，不足两位的补0，多于两位的四舍五入 "###.0#"<br/>
     *            -->表示输出的数值可以保留一位或两位小数；整数显示为有一位小数，一位或两位小数的按原样显示，多于两位的四舍五入；<br/>
     *            "###"<br/>
     *            --->表示为整数，小数部分四舍五入 ".###" -->12.234显示为.234 "#,###.0#"<br/>
     *            -->表示整数每隔3位加一个"，";<br/>
     * @return String 格式化后的字符串
     */
    public static String toString(int number, String format) {
        return toString((double) number, format);
    }

    /**
     * 把数值转换为格式化字符串
     *
     * @param number
     *            数值
     * @param format
     *            String 格式化字符串<br/>
     *            #: 表示有数字则输出数字，没有则留空；<br/>
     *            0: 表示有数字则输出数字，没有补0 对于小数，有几个＃或0，就保留几位的小数； <br/>
     *            例如：<br/>
     *            "###.00"<br/>
     *            -->表示输出的数值保留两位小数，不足两位的补0，多于两位的四舍五入 "###.0#"<br/>
     *            -->表示输出的数值可以保留一位或两位小数；整数显示为有一位小数，一位或两位小数的按原样显示，多于两位的四舍五入；<br/>
     *            "###"<br/>
     *            --->表示为整数，小数部分四舍五入 ".###" -->12.234显示为.234 "#,###.0#"<br/>
     *            -->表示整数每隔3位加一个"，";<br/>
     * @return String 格式化后的字符串
     */
    public static String toString(long number, String format) {
        return toString((double) number, format);
    }

    /**
     * 把列表转换为数组字符串，用逗号分隔（返回非 null 字符串）
     * @param <T>
     * @param list 列表对象（例如List&lt;Long&gt;）
     * @return
     */
    public static <T extends Object> String listToString(List<T> list){
        StringBuilder sb = new StringBuilder();
        if (list != null){
            for (int i=0,c=list.size();i<c;i++){
                if (i > 0){
                    sb.append(",");
                }
                sb.append(list.get(i));
            }
        }
        return sb.toString();
    }

    /**
     * 把列表转换为数组字符串，每个数据用引号包含，用逗号分隔（返回非 null 字符串）
     * @param <T>
     * @param list 列表对象（例如List&lt;Long&gt;）
     * @param invertedComma 引号字符串
     * @return
     */
    public static <T extends Object> String listToStringWithInvertedComma(List<T> list, String invertedComma){
        StringBuilder sb = new StringBuilder();
        if (list != null){
            for (int i=0,c=list.size();i<c;i++){
                if (i > 0){
                    sb.append(",");
                }
                sb.append(invertedComma + list.get(i) + invertedComma);
            }
        }
        return sb.toString();
    }

    /**
     * 把数组转化为字符串，用逗号分隔（返回非 null 字符串）
     * @param array 整形数组
     * @return 字符串
     */
    public static String arrayToString(int[] array){
        StringBuilder sb = new StringBuilder();
        if (array != null){
            for (int i=0,c=array.length;i<c;i++){
                if (i > 0){
                    sb.append(",");
                }
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 把数组转化为字符串，用逗号分隔（返回非 null 字符串）
     * @param array 整形数组
     * @return 字符串
     */
    public static String arrayToString(Object[] array){
        StringBuilder sb = new StringBuilder();
        if (array != null){
            for (int i=0,c=array.length;i<c;i++){
                if (i > 0){
                    sb.append(",");
                }
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 把数组转化为字符串，用逗号分隔（返回非 null 字符串）
     * @param array 长整形数组
     * @return 字符串
     */
    public static String arrayToString(long[] array){
        StringBuilder sb = new StringBuilder();
        if (array != null){
            for (int i=0,c=array.length;i<c;i++){
                if (i > 0){
                    sb.append(",");
                }
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 把用逗号分隔的字符串转换为长整形数组（返回非 null 数组）
     * @param str 字符串
     * @return 长整形数组
     */
    public static long[] stringToLongArray(String str){
        String[] array = str.split(",");
        if (array != null && array.length > 0){
            long[] rets = new long[array.length];
            for (int i=0,c=array.length;i<c;i++){
                rets[i] = toLong(array[i]);
            }
            return rets;
        }
        return new long[0];
    }

    /**
     * 把用逗号分隔的字符串转换为长整形数组（返回非 null 数组）
     * @param str 字符串
     * @return 整形数组
     */
    public static int[] stringToIntArray(String str){
        String[] array = str.split(",");
        if (array != null && array.length > 0){
            int[] rets = new int[array.length];
            for (int i=0,c=array.length;i<c;i++){
                rets[i] = toInt(array[i]);
            }
            return rets;
        }
        return new int[0];
    }

    /**
     * 把数值转换为格式化字符串
     *
     * @param number
     *            数值
     * @param format
     *            String 格式化字符串<br/>
     *            #: 表示有数字则输出数字，没有则留空；<br/>
     *            0: 表示有数字则输出数字，没有补0 对于小数，有几个＃或0，就保留几位的小数； <br/>
     *            例如：<br/>
     *            "###.00"<br/>
     *            -->表示输出的数值保留两位小数，不足两位的补0，多于两位的四舍五入 "###.0#"<br/>
     *            -->表示输出的数值可以保留一位或两位小数；整数显示为有一位小数，一位或两位小数的按原样显示，多于两位的四舍五入；<br/>
     *            "###"<br/>
     *            --->表示为整数，小数部分四舍五入 ".###" -->12.234显示为.234 "#,###.0#"<br/>
     *            -->表示整数每隔3位加一个"，";<br/>
     * @return String 格式化后的字符串
     */
    public static String toString(double number, String format) {
        String sReturn = "";
        DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(Locale
                .getDefault());
        nf.applyPattern(format);
        try {
            sReturn = nf.format(number);
        }
        catch (Exception e) {
            sReturn = nf.format(0);
        }
        return sReturn;
    }

    // ------------------------ 字符编码转换方法 ------------------------

    /**
     * 把字节数组转换为字符串
     *
     * @param bytes
     *            字节数组
     * @return 字符串
     */
    public static String bytesToUnicodeUTF8(byte[] bytes) {
        return bytesToUnicodeString(bytes, "UTF-8");
    }

    /**
     * 把字节数组转换为字符串
     *
     * @param bytes
     *            字节数组
     * @param charsetName
     *            字符编码名称（如：UTF-8）
     * @return 字符串
     */
    public static String bytesToUnicodeString(byte[] bytes, String charsetName) {
        try {
            return new String(bytes, charsetName);
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 把字符串转换为字节数组
     *
     * @param str
     *            字符串
     * @return 字节数组
     */
    public static byte[] unicodeUTF8ToBytes(String str) {
        return unicodeStringToBytes(str, "UTF-8");
    }

    /**
     * 把字符串转换为字节数组
     *
     * @param str
     *            字符串
     * @param charsetName
     *            字符编码名称（如：UTF-8）
     * @return 字节数组
     */
    public static byte[] unicodeStringToBytes(String str, String charsetName) {
        if (str == null)
            return null;
        try {
            return str.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 把整型数据转换为字节数组（从高位到低位）
     *
     * @param value
     *            数据数值
     * @return 字节值
     */
    public static byte[] intToBytes(int value) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (value & 0xff);
        ret[2] = (byte) ((value >> 8) & 0xff);
        ret[1] = (byte) ((value >> 16) & 0xff);
        ret[0] = (byte) (value >>> 24);
        return ret;
    }

    /**
     * 把长整型数据转换为字节数组（从高位到低位）
     *
     * @param value
     *            数据数值
     * @return 字节值
     */
    public static byte[] longToBytes(long value) {
        byte[] ret = new byte[8];
        for (int i = 0; i < 8; i++) {
            ret[i] = (byte) (value >>> (56 - i * 8));
        }
        return ret;
    }

    /**
     * 把浮点型数据转换为字节数组（从低位到高位）
     *
     * @param value
     *            数据数值
     * @return 字节值
     */
    public static byte[] floatToBytes(float value) {
        return floatToBytes(value, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 把浮点型数据转换为字节数组
     *
     * @param value
     *            数据数值
     * @return 字节值
     */
    public static byte[] floatToBytes(float value, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(order);
        byte[] ret = new byte [4];
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(value);
        bb.get(ret);
        return ret;
    }

    /**
     * 把双精度浮点型数据转换为字节数组（从低位到高位）
     *
     * @param value
     *            数据数值
     * @return 字节值
     */
    public static byte[] doubleToBytes(double value) {
        return doubleToBytes(value, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 把双精度浮点型数据转换为字节数组
     *
     * @param value
     *            数据数值
     * @return 字节值
     */
    public static byte[] doubleToBytes(double value, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.order(order);
        byte[] ret = new byte [8];
        DoubleBuffer fb = bb.asDoubleBuffer();
        fb.put(value);
        bb.get(ret);
        return ret;
    }

    /**
     * 把字节数组转换为整形（默认返回 0，从高位到低位）
     *
     * @param value
     *            源数据
     * @return int 整形
     */
    public static int bytesToInt(byte[] value) {
        return bytesToInt(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 把字节数组转换为整形（默认返回 0）
     *
     * @param value
     *            源数据
     * @return int 整形
     */
    public static int bytesToInt(byte[] value, ByteOrder order) {
        if (value == null || value.length == 0) return 0;
        ByteBuffer buffer = ByteBuffer.wrap(value);
        buffer.order(order);
        return buffer.getInt();
    }

    /**
     * 把字节数组转换为长整形（默认返回 0，从高位到低位）
     *
     * @param value
     *            源数据
     * @return long 长整形
     */
    public static long bytesToLong(byte[] value) {
        return bytesToLong(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * 把字节数组转换为长整形（默认返回 0）
     *
     * @param value
     *            源数据
     * @return long 长整形
     */
    public static long bytesToLong(byte[] value, ByteOrder order) {
        if (value == null || value.length == 0) return 0;
        ByteBuffer buffer = ByteBuffer.wrap(value);
        buffer.order(order);
        return buffer.getLong();
    }

    /**
     * 把字节数组转换为浮点数（默认返回 0，从低位到高位）
     *
     * @param value
     *            源数据
     * @return float 浮点数
     */
    public static float bytesToFloat(byte[] value) {
        return bytesToFloat(value, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 把字节数组转换为浮点数（默认返回 0）
     *
     * @param value
     *            源数据
     * @return float 浮点数
     */
    public static float bytesToFloat(byte[] value, ByteOrder order) {
        if (value == null || value.length == 0) return 0;
        ByteBuffer buffer = ByteBuffer.wrap(value);
        buffer.order(order);
        return formatFloat(buffer.getFloat(),3);
    }

    /**
     * 功能描述：给浮点型保留alter位小数
     * @param f 浮点型数字
     * @param alter 保留位数
     * @return 3位小数的浮点型
     */
    public static Float formatFloat(Float f,int alter){
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(alter,BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    /**
     * 把字节数组转换为双精度浮点数（默认返回 0，从低位到高位）
     *
     * @param value
     *            源数据
     * @return double 浮点数
     */
    public static double bytesToDouble(byte[] value) {
        return bytesToDouble(value, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 把字节数组转换为双精度浮点数（默认返回 0）
     *
     * @param value
     *            源数据
     * @return double 浮点数
     */
    public static double bytesToDouble(byte[] value, ByteOrder order) {
        if (value == null || value.length == 0) return 0;
        ByteBuffer buffer = ByteBuffer.wrap(value);
        buffer.order(order);
        return buffer.getDouble();
    }

    /**
     * 截取指定长度的字节数据
     * @param bytes 源字节数组
     * @param start 开始位置
     * @return byte[] 被截取的字节数组
     */
    public static byte[] subBytes(byte[] bytes, int start){
        if (bytes == null || bytes.length == 0 || start >= bytes.length) return new byte[0];
        if (start < 0) start = 0;
        byte[] ret = new byte[bytes.length - start];
        for (int i=start,index=0,c=bytes.length;i<c;i++,index++){
            ret[index] = bytes[i];
        }
        return ret;
    }

    /**
     * 截取指定长度的字节数据
     * @param bytes 源字节数组
     * @param start 开始位置
     * @param len 截取长度
     * @return byte[] 被截取的字节数组
     */
    public static byte[] subBytes(byte[] bytes, int start, int len){
        byte[] ret = new byte[len];
        if (bytes == null || bytes.length == 0) return ret;
        if (start < 0) start = 0;
        for (int i=start,index=0,c=bytes.length;index<len&&i<c;i++,index++){
            ret[index] = bytes[i];
        }
        return ret;
    }

    // ------------------------ 泛类型数据转换 ------------------------

    /**
     * 把字节数组转换为对象
     *
     * @param bytes
     *            字节数组
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T bytesToObject(byte[] bytes) {
        if (bytes == null)
            return null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        T o = null;
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(in);
            o = (T) oi.readObject();
            oi.close();
        }
        catch (Exception e) {
            if (oi != null) {
                try {
                    oi.close();
                }
                catch (IOException e1) {}
            }
            o = null;
        }
        return o;
    }

    /**
     * 把可序列化对象转换成字节数组（返回为 null 表示转换失败）
     *
     * @param obj
     *            数据对象
     * @return 字节数组
     */
    public static byte[] objectToBytes(Serializable obj) {
        if (obj == null)
            return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream ot = null;
        try {
            ot = new ObjectOutputStream(out);
            ot.writeObject(obj);
            ot.flush();
            ot.close();
            return out.toByteArray();
        }
        catch (IOException e) {
            if (ot != null) {
                try {
                    ot.close();
                }
                catch (IOException e1) {}
            }
            return null;
        }
    }

    // ------------------------ 十六进制类型数据转换 ------------------------

    /**
     * 把十六进制字符转换为字节值
     *
     * @param hexChar
     *            字符
     * @return 字节值
     */
    public static byte hexCharToByte(char hexChar) {
        return (byte) "0123456789ABCDEF".indexOf(hexChar);
    }

    /**
     * 把十六进制字符串转换成字节
     *
     * @param hexString
     *            十六进制字符串
     * @return 字节值
     */
    public static byte hexStringToByte(String hexString) {
        if (hexString.indexOf("0x") > -1) {
            hexString = hexString.substring(2,hexString.length());
        }
        return toByte(hexString, (byte) 0, 16);
    }

    /**
     * 把十六进制字符串转换成字节
     *
     * @param hexString
     *            十六进制字符串
     * @param defaultByte
     *            默认值
     * @return 字节值
     */
    public static byte hexStringToByte(String hexString, byte defaultByte) {
        return toByte(hexString, defaultByte, 16);
    }

    /**
     * 把十六进制字符串转换成字节数组（返回 null 表示失败）
     *
     * @param hexString
     *            十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        return hexStringToBytes(hexString, null);
    }

    /**
     * 把十六进制字符串转换成字节数组
     *
     * @param hexString
     *            十六进制字符串
     * @param defaultBytes
     *            默认值
     * @return 字节数组
     */
    public static byte[] hexStringToBytes(String hexString, byte[] defaultBytes) {
        int hexLen = 0;
        if (hexString == null || (hexLen = hexString.length()) == 0)
            return defaultBytes;
        if (hexLen == 1)
            hexString = "0" + hexString;
        int len = (hexLen / 2);
        byte[] result = new byte[len];
        char[] achar = hexString.toUpperCase().toCharArray();
        try {
            for (int i = 0; i < len; i++) {
                int pos = i * 2;
                result[i] = (byte) (hexCharToByte(achar[pos]) << 4 | hexCharToByte(achar[pos + 1]));
            }
            return result;
        }
        catch (Exception e) {
            return defaultBytes;
        }
    }

    /**
     * 把字节转换为十六进制字符串
     *
     * @param b
     *            字节值
     * @return String 十六进制字符串
     */
    public static String byteToHexString(int b) {
        String s = Integer.toHexString(b).toUpperCase();
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * 把字节转换为整数
     *
     * @param b
     *            字节值
     * @return int 整形
     */
    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    /**
     * 把字节转换为十六进制字符串
     *
     * @param b
     *            字节值
     * @return String 十六进制字符串
     */
    public static String byteToHexString(byte b) {
        String s = Integer.toHexString(b & 0xFF).toUpperCase();
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * 把字节数组转换成十六进制字符串（不带分隔符）
     *
     * @param bytes
     *            字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null)
            return "";
        int c = bytes.length;
        StringBuffer sb = new StringBuffer(c);
        String sTemp;
        for (int i = 0; i < c; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 把字节数组转换成十六进制字符串
     *
     * @param bytes
     *            字节数组
     * @param colons
     *            分隔符字符串，例如：“:”
     * @return 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes, String colons) {
        if (bytes == null)
            return "";
        int c = bytes.length;
        StringBuffer sb = new StringBuffer(c);
        String sTemp;
        for (int i = 0; i < c; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp);
            if (i < c - 1) {
                sb.append(colons);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 把对象转换为十六进制字符串
     *
     * @param obj
     *            序列化对象
     * @return 十六进制字符串
     * @throws IOException
     */
    public static String objectToHexString(Serializable obj) {
        return bytesToHexString(objectToBytes(obj));
    }

    /**
     * 把十六进制字符串转化为对象
     *
     * @param hexString
     *            十六进制字符
     * @return 对象
     */
    public static <T extends Object> T hexStringToObject(String hexString) {
        return bytesToObject(hexStringToBytes(hexString));
    }


    /**
     * 功能描述：boolean类型转换为char类型
     * @author sofWang
     * @param bool
     * @return
     * create Data 2012-05-24
     */
    public static char booleanToChar(boolean bool){
        if(bool){
            return 'Y';
        }else{
            return 'N';
        }
    }

    /**
     * 功能描述：char类型转换为boolean类型
     * @param c
     * @return
     */
    public static boolean charToBoolean(char c){
        if(c=='Y'){
            return true;
        }else{
            return false;
        }
    }



    /**
     * 功能描述:把一个字节的8位解析成0和1的char数组
     * @author sofWang
     * @param b 需要解析的字节。
     * @return char 数组。
     * create Date 2012-12-04
     */
    public static char[] byteToChars(byte b){
        String status_byte = Integer.toHexString(b & 0xFF)+"";
        Long status_16 = Long.parseLong(status_byte, 16);
        String status_2 = Long.toBinaryString(status_16);
        String prefix = "";
        if(status_2.length()<8){
            for (int i = 0; i <8-status_2.length(); i++) {
                prefix +="0";
            }
        }
        String statusStr = prefix+status_2;
        char[] statusArr = statusStr.toCharArray();
        char[] sortStatus = new char[statusArr.length];
        for (int i = 0; i < statusArr.length; i++) {
            sortStatus[i] = statusArr[statusArr.length-1-i];
        }
        return sortStatus;
    }

//	/**
//	 * 功能描述:把字符串转换为十六进制字符串
//	 * @author sofWang
//	 * @param str 字符串
//	 * create Date 2012-12-06
//	 */
//	public static String toHexString(String str){
//		char[] c = str.toCharArray();
//		String hexString = "";
//		for (int i = 0; i < c.length; i++) {
//			hexString += Integer.toHexString(c[i]);
//		}
//		return hexString;
//	}
//

    //byte数组转换int
    public static int bytes2Int(byte[] intByte) {
        int num = 0;
        for (int i = 0; i < intByte.length&&i<4; i++) {
            num += (intByte[i]&0xFF)*(Math.pow(256, intByte.length-1-i));
        }
        return num;
    }

    //将16进制整形转化为10进制整形。
    public static int hexIntToInt(int number){
        //将整形转为char数组
        char[] numberArray = Integer.toString(number).toCharArray();
        int result = 0;
        //遍历每个char
        for(int i = 0;i < numberArray.length;i++){
            result += Math.pow(16,numberArray.length-i-1)*(numberArray[i]-'0');
        }
        return result;
    }

    /**
     * 功能描述：去除byte数组最后面的0x00元素
     * @param b1　原数组
     * @return　格式化后的数组
     * create Date 2013-07-12 16:42:48
     */
    public static byte[] trimBytes(byte[] b1){
        if(b1[b1.length-1]!=0x00){
            return b1;
        }else{
            byte[] b2 = null;
            for (int i = 0; i < b1.length; i++) {
                if(b1[b1.length-1-i]!=0x00){
                    b2 = new byte[b1.length-i];
                    break;
                }
            }
            for (int i = 0; i < b2.length; i++) {
                b2[i] = b1[i];
            }
            return b2;
        }
    }


    public static String encodingIso88591(String string) throws UnsupportedEncodingException{
        if(string==null)return "";
        return new String(string.getBytes("ISO-8859-1"),"UTF-8");
    }


    /**
     * 字符串的拼接，最长限制为整数最大值
     * @param a 需要拼接的字符串1
     * @param b 需要拼接的字符串2
     * @return 拼接后的字符串
     */
    public static byte[] concatHexByteArr(byte[] a, byte[] b) {
        if ((Integer.MAX_VALUE - a.length) > b.length) {
            byte[] c = new byte[a.length + b.length];
            System.arraycopy(a, 0, c, 0, a.length);
            System.arraycopy(b, 0, c, a.length, b.length);
            return c;
        }
        return null;
    }

}