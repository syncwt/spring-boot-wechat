package com.senthink.www.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 *
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.util
 * @Description :  Http客户端工具类,内部调用类
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午5:41
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);//运行日志

    /**
     * 向服务器发送get请求
     * @param httpUrl
     * @return
     */
    public static JSONObject sendGetRequest(String httpUrl)
    {
        JSONObject jsonObject = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            String jsonData = in.readLine().toString();
            if(StringUtils.isNotEmpty(jsonData)&& (jsonData.indexOf("errcode")== -1))
            {
                jsonObject = JSONObject.fromObject(jsonData);
            }
            urlConnection.disconnect();
        } catch (Exception e)
        {
            logger.error("HttpURLConnection 向服务器发送请求失败:",e);
        }
        return jsonObject;
    }

	/**
	 * 向服务器发送get请求
	 * 
	 * @param httpUrl
	 * @return
	 */
	public static JSONObject mySendGetRequest(String httpUrl, String apikey) {
		JSONObject jsonObject = null;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("apikey", apikey);
			urlConnection.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			urlConnection.setRequestProperty("charset", "utf-8");
			urlConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String jsonData = in.readLine().toString();
			if (StringUtils.isNotEmpty(jsonData) && (jsonData.indexOf("errcode") == -1)) {
				jsonObject = JSONObject.fromObject(jsonData);
			}
			urlConnection.disconnect();
		} catch (Exception e) {
			logger.error("HttpURLConnection 向服务器发送请求失败:", e);
		}
		return jsonObject;
	}

    /**
     *  向服务器发送post请求
     * @param httpurl
     * @param xmlData
     * @return
     * @throws Exception
     */
    public static Map<String, String> sendPostRequestXml(String httpurl, String xmlData) throws Exception
    {
        Map<String, String> map = null;
        try
        {
            URL url = new URL(httpurl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","text/xml;charset=utf-8");
            urlConnection.setDoOutput(true);// post请求参数要放在http正文内，顾设置成true，默认是false
            urlConnection.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
            urlConnection.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
            //urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            //加入数据
            OutputStreamWriter out = new OutputStreamWriter(
                    urlConnection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(xmlData);
            out.flush();
            out.close();
	        /*BufferedOutputStream buffOutStr = new BufferedOutputStream(urlConnection.getOutputStream());
	        buffOutStr.write(xmlData.getBytes());
	        buffOutStr.flush();
	        buffOutStr.close();  */
            map = MessageUtil.parseXml(urlConnection);
            urlConnection.disconnect();
        }
        catch (Exception e)
        {
            logger.error(DateFormatUtil.getCurrentTime()+"====接口异常"+e+"httpurl:"+httpurl);
        }
        return map;
    }

    /**
     * HttpURLConnection 向服务器发送post请求
     * author chenboda
     * createTime 2016-06-21
     * @param httpurl 服务器接口地址 jsonData:json格式的参数信息
     * @return 返回Map格式数据
     */
    public static JSONObject sendPostRequestJson(String httpurl, String jsonData) throws Exception
    {
        JSONObject jsonObject = null;
        try
        {
            URL url = new URL(httpurl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);// post请求参数要放在http正文内，顾设置成true，默认是false
            urlConnection.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
            urlConnection.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    urlConnection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(jsonData);
            out.flush();
            out.close();
            //加入数据
	       /* DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
	        out.writeBytes(jsonData);
	        out.flush();
	        out.close();  */
            //读取响应
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            jsonObject = JSONObject.fromObject(in.readLine().toString());
            urlConnection.disconnect();
        }
        catch (Exception e)
        {
            logger.error(DateFormatUtil.getCurrentTime()+"====接口异常"+e+"httpurl:"+httpurl);
        }
        return jsonObject;
    }
}
