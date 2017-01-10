package com.senthink.www.domain;

/**
 * Created by Jason on 2016/11/7.
 */
public class Constants {

    public static String CACHE_KEY_PREFIX = "reids_";

    //获取公众号的全局唯一票据access_token
    public static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    //获取微信账号用户信息URL
    public static String USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

    //网页授权获取用户信息URL
    public static String OAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    //拉取用户信息(需scope为 snsapi_userinfo)网页授权获取
    public static String HTTP_WEB_GET_USER_ERL = "https://api.weixin.qq.com/sns/userinfo";

    //根据openid获取用户基本信息
    public static String HTTP_GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

    //获取deviceid和二维码URL
    public static String QRCODE_URL = "https://api.weixin.qq.com/device/getqrcode";

    //第三方公众账号通过设备id从公众平台批量获取设备二维码
    public static String CREAT_QRCODE_URL = "https://api.weixin.qq.com/device/create_qrcode";

    //利用deviceid更新设备属性URL
    public static String AUTHORIZE_URL = "https://api.weixin.qq.com/device/authorize_device";

    //主动发消息给设备URL
    public static String POST_TO_DEVICE_URL = "https://api.weixin.qq.com/device/transmsg";

    //第三方公众账号通过设备id查询该id的状态（三种状态：未授权、已授权、已绑定）
    public static String GET_DEVICE_STATE_URL = "https://api.weixin.qq.com/device/get_stat";

    //第三方公众账号通过获取设备二维码的api得到ticket后，可以通过该api拿到对应的设备属性
    public static String GET_DEVICE_INFO_URL = "https://api.weixin.qq.com/device/verify_qrcode";

    //通过device type和device id获取设备主人的openid
    public static String GET_DVC_OWNER_OPENID_URL = "https://api.weixin.qq.com/device/get_openid";

    //通过openid获取用户在当前devicetype下绑定的deviceid列表
    public static String GET_USER_DEVICE_LIST_URL = "https://api.weixin.qq.com/device/get_bind_device";

    //第三方强制解绑用户和设备。
    public static String FORCE_TO_UNBIND_URL = "https://api.weixin.qq.com/device/compel_unbind";

    //第三方强制绑定用户和设备
    public static String FORCE_TO_BIND_URL = "https://api.weixin.qq.com/device/compel_bind";

    //获取微信jsapi ticket
    public static String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    //创建微信自定义菜单请求url
    public static String CRAET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";

    //微信公众号授权页面跳转
    public static String WECHAT_MENU_CONFIG_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    //魔方首页跳转链接
    public static String AIRCUBE_INDEX_URL = "http://10.18.11.208:9010/";

    //调用模版消息发送给用户
    public static String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    //ARTICLE1 用户关注公众号时推送
    public static String DESCRIBE_NEWS_TITLE = "欢迎使用智能空气魔方";
    public static String DESCRIBE_NEWS_DESCRIPTION = "点击查看您的空气质量";
    public static String DESCRIBE_NEWS_PIC_URL = "https://mmbiz.qlogo.cn/mmbiz_jpg/593icgD43icyrLJ612fWaPkUficL9gmeC7ork0qdvJmZv9PHjbhDJAYtajfcNvCrb0euNmlMyNpn74hJv6tBvM2qA/0?wx_fmt=jpeg";
    public static String DESCRIBE_NEWS_URL = "http://10.18.11.208:9010/";

    //ARTICLE2 用户绑定第二个及以上设备时推送提示完善设备消息
    public static String BIND_NEWS_TITLE = "请完善您的魔方信息";
    public static String BIND_NEWS_DESCRIPTION = "点击完善您的魔方信息";
    public static String BIND_NEWS_PIC_URL = "https://mmbiz.qlogo.cn/mmbiz_jpg/593icgD43icyrLJ612fWaPkUficL9gmeC7ork0qdvJmZv9PHjbhDJAYtajfcNvCrb0euNmlMyNpn74hJv6tBvM2qA/0?wx_fmt=jpeg";
    public static String BIND_NEWS_URL = "http://10.18.11.208:9010/";
}
