package com.senthink.www.common;

import com.senthink.www.config.WechatConfig;
import com.senthink.www.domain.Constants;
import com.senthink.www.util.SyncWechatToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.util
 * @Description :  微信工具类
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午4:56
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
@Component
public class WechatUtil {

    @Resource
    private WechatConfig wechatConfigAutoWired;
    @Resource
    private RedisUtil redisUtilAutoWired;
    @Resource
    private SyncWechatToken syncWechatTokenAutoWired;

    private static WechatConfig wechatConfig;

    private static RedisUtil redisUtil;

    private static SyncWechatToken syncWechatToken;

    static Logger logger = org.slf4j.LoggerFactory.getLogger(WechatUtil.class);

    @PostConstruct
    public void init() {
        this.wechatConfig = wechatConfigAutoWired;
        this.redisUtil = redisUtilAutoWired;
        this.syncWechatToken = syncWechatTokenAutoWired;
    }

    /**
     * 网页授权获取用户基本信息
     * 接口URL: httpUrl:https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     *
     * @param code
     * @return jsonDate{"access_token":"OezXcEiiBSKSxW0eoylIeICBNOJ0KXDxZL46prSvC5gepITJtytNUx7m8VMDWQDqxcDH_Kef1AuBXNk9fAprdjLL_EPl51ILRQhKDwkuQKYXW1wDZ1aMbc9ufvakq_hK5JnbYTx4CLOTBKHyBmQTPw"
     * ,"expires_in":7200,"refresh_token":"OezXcEiiBSKSxW0eoylIeICBNOJ0KXDxZL46prSvC5gepITJtytNUx7m8VMDWQDqF35B0BuFap3jXODLSRyalpb--0DkdjN4GKbg7O2OY0ussYVlTox7SPIbm6JS1tI_w7LTqXvD_zEenhX8QliCBA"
     * ,"openid":"oQZlcszktYhVn_4nkLn6kudgu3y8","scope":"snsapi_base"}
     */
    public static JSONObject getOpenId(String code) {
        //第三方用户唯一凭证
        String appid = wechatConfig.getConfig().getAppid();
        //第三方用户唯一凭证密钥，即appsecret
        String secret = wechatConfig.getConfig().getAppsecret();
        //网页授权获取用户信息URL
        String url = Constants.OAUTH_URL;

        url = url + "?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = proxyAuth(url);
        return jsonObject;
    }

    /**
     * 获取网页授权access_token access_token的有效期目前为2个小时 需定时刷新，
     * 重复获取将导致上次获取的access_token失效  每日调用上限/次 2000
     *
     * @return jsonData :{"access_token":"ACCESS_TOKEN","expires_in":7200} access_token:获取到的凭证  expires_in:凭证有效时间，单位：秒
     * {"access_token":"SnG458UI71l7e4BVDWrC06Vr6jbseW1ze-0UFpbSmvikHuY2ghxG2dTQMN45pWjQOp1JixcZlJHil6Bac3qTk1mgyx4n-cKeIaw91KEbtfE","expires_in":7200}
     */
    public static String getToken() {

        //第三方用户唯一凭证
        String appid = wechatConfig.getConfig().getAppid();
        //第三方用户唯一凭证密钥，即appsecret
        String secret = wechatConfig.getConfig().getAppsecret();
        String token = null;

        if (StringUtils.isNotEmpty(appid) && StringUtils.isNotEmpty(secret)) {

            String tokenUrl = Constants.TOKEN_URL + "&appid=" + appid + "&secret=" + secret;
            JSONObject jsonObject = proxyAuth(tokenUrl);

            if (null != jsonObject) {

                Map<String, String> map = JsonUtil.toMap(jsonObject);
                if (null != map && !map.isEmpty()) {
                    token = map.get("access_token");
                }
            }
        }
        return token;
    }

    /**
     * HttpURLConnection 向服务器发送GET请求
     *
     * @param httpUrl
     * @return
     */
    public static JSONObject proxyAuth(String httpUrl) {
        JSONObject jsonObject = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
            urlConnection.setRequestProperty("contentType", "UTF-8");
            urlConnection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String jsonData = in.readLine().toString();
            if (StringUtils.isNotEmpty(jsonData)) {
                jsonObject = JSONObject.fromObject(jsonData);
            }
        } catch (Exception e) {
            logger.error("HttpURLConnection 向服务器发送请求失败:", e);
        }
        return jsonObject;
    }

    /**
     * HttpURLConnection 向服务器发送POST请求
     *
     * @param httpUrl
     * @param paramObj 看接口需求,可能是参数拼接,也可能是jsonobject.toString()
     * @return
     */
    public static JSONObject proxyAuthPost(String httpUrl, Object paramObj) {
        String param = buildPostParameters(paramObj);
        JSONObject jsonObject = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
//            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
            urlConnection.setRequestProperty("charset", "UTF-8");
            urlConnection.connect();
//            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
//            out.print(param);
//            out.close();
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(urlConnection
                    .getOutputStream());
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String jsonData = in.readLine().toString();
            if (StringUtils.isNotEmpty(jsonData)) {
                jsonObject = JSONObject.fromObject(jsonData);
            }
            in.close();
        } catch (Exception e) {
            logger.error("HttpURLConnection 向服务器发送请求失败:", e);
        }
        return jsonObject;
    }

    /**
     * 构建post对象
     *
     * @param content
     * @return
     */
    public static String buildPostParameters(Object content) {
        String output = null;
        if ((content instanceof String) ||
                (content instanceof JSONObject) ||
                (content instanceof JSONArray)) {
            output = content.toString();
        } else if (content instanceof Map) {
            StringBuffer buf = new StringBuffer();
            HashMap hashMap = (HashMap) content;
            if (hashMap != null) {
                Iterator entries = hashMap.entrySet().iterator();
                while (entries.hasNext()) {
                    buf.append(buf.length() == 0 ? "" : "&");
                    Map.Entry entry = (Map.Entry) entries.next();
                    buf.append(entry.getKey().toString()).append("=").append(entry.getValue().toString());
                    entries.remove(); // avoids a ConcurrentModificationException
                }
                output = buf.toString();
            }
        }

        return output;
    }

    /**
     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * 获取微信账号用户信息
     *
     * @param access_token 网页授权接口调用凭证
     * @param openid       普通用户的标识，对当前公众号唯一
     * @return
     */
    public static Map<String, String> getUserInfo(String access_token, String openid) {
        Map<String, String> map = null;
        //从系统缓存中获取token
        if (StringUtils.isNotEmpty(access_token) && StringUtils.isNotEmpty(openid)) {
            String userUrl = Constants.HTTP_GET_USER_URL + "?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
            JSONObject jsonObject = proxyAuth(userUrl);
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMap(jsonObject);
                    map.put("gmt_regist", DateFormatUtil.getCurrentTime());
                } catch (Exception e) {
                    logger.error("获取微信账号用户信息转换异常", e);
                }
            }
        }
        return map;
    }

    /**
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * 根据openid获取用户基本信息
     *
     * @param access_token
     * @param openid       普通用户的标识，对当前公众号唯一
     * @return
     */
    public static Map<String, String> getUserInfoByOpenid(String access_token, String openid) {
        Map<String, String> map = null;
        //从系统缓存中获取token
        if (StringUtils.isNotEmpty(access_token) && StringUtils.isNotEmpty(openid)) {
            //默认返回简体中文
            String userUrl = Constants.HTTP_GET_USER_URL + "?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
            JSONObject jsonObject = proxyAuth(userUrl);
            if (jsonObject == null) {
                jsonObject = proxyAuth(userUrl);
            }
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMap(jsonObject);
                } catch (Exception e) {
                    logger.error("获取微信账号用户信息转换异常", e);
                }
            }

        }
        return map;
    }

    /**
     * https://api.weixin.qq.com/device/getqrcode?access_token=ACCESS_TOKEN&product_id=PRODUCT_ID
     * 第三方获取deviceid和设备二维码
     *
     * @param accessToken
     * @param productId
     * @return
     */
    public static Map<Object, Object> getDvcIdAndQrcode(String accessToken, String productId) {
        Map<Object, Object> map = null;
        //从系统缓存中获取token
        if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(productId)) {
            //当product_id 为‘1’时，不要填写product_id 字段（会引起不必要错误）；
            //当product_id 不为‘1’时，必须填写 product_id 字段；
            productId = productId.equals("1") ? "" : "&product_id=" + productId;
            String url = Constants.QRCODE_URL + "?access_token=" + accessToken + productId;
            JSONObject jsonObject = proxyAuth(url);
            if (jsonObject == null) {
                logger.error("获取deviceid和设备二维码失败!");
            }
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMapObj(jsonObject);
                } catch (Exception e) {
                    logger.error("获取设备及二维码信息转换异常", e);
                }
            }

        }
        return map;
    }

    /**
     * 第三方公众账号将device id及其属性信息提交公众平台进行授权。(新接口,未启用)
     * https://api.weixin.qq.com/device/authorize_device?access_token=ACCESS_TOKEN
     *
     * @param accessToken
     * @param dvcInfoList 必须是JsonObject格式,不支持map
     * @return
     */
    public static Map<Object, Object> dvcAuthorizeNew(String accessToken, JSONObject dvcInfoList) {
        Map<Object, Object> map = null;
        //从系统缓存中获取token
        if (StringUtils.isNotEmpty(accessToken) && dvcInfoList != null) {
            String postUrl = Constants.AUTHORIZE_URL + "?access_token=" + accessToken;
            JSONObject jsonObject = proxyAuthPost(postUrl, dvcInfoList);
            if (jsonObject == null) {
                logger.error("设备授权失败!");
            }
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMapObj(jsonObject);
                } catch (Exception e) {
                    logger.error("获取授权结果转换异常", e);
                }
            }

        }
        return map;
    }

    /**
     * 设备授权接口
     * https://api.weixin.qq.com/device/authorize_device?access_token=ACCESS_TOKEN
     *
     * @param accessToken
     * @param dvcInfoList
     * @return
     */
    public static JSONObject dvcAuthorize(String accessToken, JSONObject dvcInfoList) {
        JSONObject jsonObject = null;
        //从系统缓存中获取token
        if (StringUtils.isNotEmpty(accessToken) && dvcInfoList != null) {
            String postUrl = Constants.AUTHORIZE_URL + "?access_token=" + accessToken;
            jsonObject = proxyAuthPost(postUrl, dvcInfoList);
            if (jsonObject == null) {
                logger.error("设备授权失败!");
            }
        }
        return jsonObject;
    }

    /**
     * 第三方公众账号通过设备id从公众平台批量获取设备二维码
     *
     * @param accessToken
     * @param dvcInfoList
     * @return
     */
    public static JSONObject getDvcTicket(String accessToken, JSONObject dvcInfoList) {
        JSONObject jsonObject = null;
        //从系统缓存中获取token
        if (StringUtils.isNotEmpty(accessToken) && dvcInfoList != null) {
            String postUrl = Constants.CREAT_QRCODE_URL + "?access_token=" + accessToken;
            jsonObject = proxyAuthPost(postUrl, dvcInfoList);
            if (jsonObject == null) {
                logger.error("获取二维码失败!");
            }
        }
        return jsonObject;
    }

    /**
     * 主动发消息给设备
     * https://api.weixin.qq.com/device/transmsg?access_token=ACCESS_TOKEN
     *
     * @param accessToken
     * @param jsonPost
     * @return
     */
    public static JSONObject postToDvc(String accessToken, JSONObject jsonPost) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(accessToken) && jsonPost != null) {
            String postUrl = Constants.POST_TO_DEVICE_URL + "?access_token=" + accessToken;
            jsonObject = proxyAuthPost(postUrl, jsonPost);
            if (jsonObject == null) {
                logger.error("主动发送数据给设备失败!");
            }
        }
        return jsonObject;
    }

    /**
     * 第三方公众账号通过设备id查询该id的状态（三种状态：未授权、已授权、已绑定）
     * https://api.weixin.qq.com/device/get_stat?access_token=ACCESS_TOKEN&device_id=DEVICE_ID
     *
     * @param accessToken
     * @param deviceId
     * @return
     */
    public static Map<String, String> getDvcState(String accessToken, String deviceId) {
        Map<String, String> map = null;
        if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(deviceId)) {
            String url = Constants.GET_DEVICE_STATE_URL + "?access_token=" + accessToken + "&device_id=" + deviceId;
            JSONObject jsonObject = proxyAuth(url);
            if (jsonObject == null) {
                logger.error("设备状态查询失败!");
            }
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMap(jsonObject);
                } catch (Exception e) {
                    logger.error("获取设备状态结果转换异常", e);
                }
            }

        }
        return map;
    }

    /**
     * 第三方公众账号通过获取设备二维码的api得到ticket后，可以通过该api拿到对应的设备属性。
     * https://api.weixin.qq.com/device/verify_qrcode?access_token=ACCESS_TOKEN
     *
     * @param accessToken
     * @param jsonPost
     * @return
     */
    public static Map<String, String> getDvcInfoByQrcode(String accessToken, JSONObject jsonPost) {
        Map<String, String> map = null;
        if (StringUtils.isNotEmpty(accessToken) && jsonPost != null) {
            String postUrl = Constants.GET_DEVICE_INFO_URL + "?access_token=" + accessToken;
            JSONObject jsonObject = proxyAuthPost(postUrl, jsonPost);
            if (jsonObject == null) {
                logger.error("获取设备信息失败!");
            }
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMap(jsonObject);
                } catch (Exception e) {
                    logger.error("获取设备信息结果转换异常", e);
                }
            }

        }
        return map;
    }

    /**
     * 通过device type和device id获取设备主人的openid
     * https://api.weixin.qq.com/device/get_openid?access_token=ACCESS_TOKEN&device_type=DEVICE_TYPE&device_id=DEVICE_ID
     *
     * @param accessToken
     * @param deviceType
     * @param deviceId
     * @return
     */
    public static Map<String, String> getDvcOwnerOpenid(String accessToken, String deviceType, String deviceId) {
        Map<String, String> map = null;
        if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(deviceId) && StringUtils.isNotEmpty(deviceId)) {
            String url = Constants.GET_DVC_OWNER_OPENID_URL + "?access_token=" + accessToken + "&device_type=" + deviceType + "&device_id=" + deviceId;
            JSONObject jsonObject = proxyAuth(url);
            if (jsonObject == null) {
                logger.error("获取设备主人的openid失败!");
            }
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMap(jsonObject);
                } catch (Exception e) {
                    logger.error("获取设备主人的openid结果转换异常", e);
                }
            }

        }
        return map;
    }

    /**
     * 通过openid获取用户在当前devicetype下绑定的deviceid列表
     * https://api.weixin.qq.com/device/get_bind_device?access_token=ACCESS_TOKEN&openid=OPENID
     *
     * @param accessToken
     * @param openid
     * @return
     */
    public static JSONObject getUserDvcList(String accessToken, String openid) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(openid)) {
            String url = Constants.GET_USER_DEVICE_LIST_URL + "?access_token=" + accessToken + "&openid=" + openid;
            jsonObject = proxyAuth(url);
            if (jsonObject == null) {
                logger.error("获取用户绑定设备列表失败!");
            }
        }
        return jsonObject;
    }

    /**
     * https://api.weixin.qq.com/device/compel_unbind?access_token=ACCESS_TOKEN
     * 强制解绑用户和设备
     *
     * @param accessToken
     * @param jsonPost
     * @return
     */
    public static JSONObject forceToUnbindDvc(String accessToken, JSONObject jsonPost) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(accessToken) && jsonPost != null) {
            String postUrl = Constants.FORCE_TO_UNBIND_URL + "?access_token=" + accessToken;
            jsonObject = proxyAuthPost(postUrl, jsonPost);
            if (jsonObject == null) {
                logger.error("强制绑定设备失败!");
            }
        }
        return jsonObject;
    }

    /**
     * 获取jsapi ticket
     *
     * @param accessToken
     * @return
     */
    public static JSONObject getJSAPITicket(String accessToken) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(accessToken)) {
            String url = Constants.GET_JSAPI_TICKET_URL + "?access_token=" + accessToken + "&type=jsapi";
            jsonObject = proxyAuth(url);
            if (jsonObject == null) {
                logger.error("获取用户绑定设备列表失败!");
            }
        }
        return jsonObject;
    }

    /**
     * 创建自定义菜单
     *
     * @param accessToken
     */
    public static Map<String, String> creatMenu(String accessToken, JSONObject jsonPost) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(accessToken) && jsonPost != null) {
            String postUrl = Constants.CRAET_MENU_URL + "?access_token=" + accessToken;
            JSONObject jsonObject = proxyAuthPost(postUrl, jsonPost);
            if (jsonObject == null) {
                logger.error("创建自定义菜单失败!");
            }
            if (null != jsonObject) {
                try {
                    map = JsonUtil.toMap(jsonObject);
                } catch (Exception e) {
                    logger.error("创建自定义菜单结果转换异常", e);
                }
            }
        }
        return map;
    }

    /**
     * 从微信服务接口获取access_token
     *
     * @return
     */
    public static String getAccessTokenByWechat() {
        String appid = wechatConfig.getConfig().getAppid();
        String secret = wechatConfig.getConfig().getAppsecret();
        String tokenUrl = Constants.TOKEN_URL + "&appid=" + appid + "&secret=" + secret;

        JSONObject json = HttpClientUtil.sendGetRequest(tokenUrl);
        return (String) json.get("access_token");
    }

    /**
     * 获取access_token
     *
     * @return
     */
    public static String getAccessToken() {

        String accessToken = (String) redisUtil.get("access_token");

        //判断是否为空
        if (null == accessToken) {
            accessToken = WechatUtil.getAccessTokenByWechat();
            //持久化,有效期为两小时
            redisUtil.set("access_token", accessToken, (long) 2 * 60 * 60);
        }

        //异步检查token是否快过期，如果过期时间小于十分钟则刷新token
        syncWechatToken.syncToken();

        return accessToken;
    }

    /**
     * 发送模板消息
     *
     * @param accessToken
     * @param jsonPost
     * @return
     */
    public static JSONObject sendMessage(String accessToken, JSONObject jsonPost) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(accessToken) && jsonPost != null) {
            String postUrl = Constants.SEND_TEMPLATE_MSG_URL + "?access_token=" + accessToken;
            jsonObject = proxyAuthPost(postUrl, jsonPost);
            if (jsonObject == null) {
                logger.error("发送模版消息失败!");
            }
        }
        return jsonObject;
    }
}
