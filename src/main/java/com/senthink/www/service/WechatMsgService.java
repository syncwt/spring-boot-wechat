package com.senthink.www.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.senthink.www.common.DateFormatUtil;
import com.senthink.www.common.EmojiFilter;
import com.senthink.www.common.SecurityUtil;
import com.senthink.www.common.WechatUtil;
import com.senthink.www.config.WechatConfig;
import com.senthink.www.dao.IAircubeInfoMapper;
import com.senthink.www.domain.Constants;
import com.senthink.www.domain.po.AircubeInfo;
import com.senthink.www.domain.po.PageData;
import com.senthink.www.domain.vo.AircubeAuthorizeVo;
import com.senthink.www.domain.vo.DvcMsgVo;
import com.senthink.www.enums.EnumInfoStatus;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.service
 * @Description :  处理微信消息业务逻辑
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 上午9:53
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
@Service
public class WechatMsgService {

    private Logger logger = LoggerFactory.getLogger(WechatMsgService.class);

    @Autowired
    private WechatConfig wechatConfig;
    @Autowired
    private AircubeInfoService aircubeInfoService;
    @Autowired
    private AircubeUserService aircubeUserService;
    @Autowired
    private IAircubeInfoMapper iAircubeInfoMapper;

    /**
     * 微信服务器验证
     *
     * @param pd
     * @param response
     * @throws Exception
     */
    public void serviceVerification(PageData pd, HttpServletResponse response) throws Exception {

        String signature = pd.getString("signature");        //微信加密签名
        String timestamp = pd.getString("timestamp");        //时间戳
        String nonce = pd.getString("nonce");            //随机数
        String echostr = pd.getString("echostr");            //字符串

        List<String> list = new ArrayList<String>(3) {
            private static final long serialVersionUID = 2621444383666420433L;

            //重写toString方法，得到三个参数的拼接字符串
            public String toString() {
                return this.get(0) + this.get(1) + this.get(2);
            }
        };

        list.add(wechatConfig.getConfig().getToken());//读取Token(令牌)
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);

        String tmpStr = SecurityUtil.encode(list.toString(), SecurityUtil.SHA_1);// SHA-1加密

        if (signature.equals(tmpStr)) {
            response.getOutputStream().write(echostr.getBytes());//请求验证成功，返回随机码
        } else {
            response.getOutputStream().write("".getBytes());
        }
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 处理微信消息
     *
     * @param resultMap
     * @param request
     * @param response
     */
    public void handleMsg(Map<String, String> resultMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取微信服务器推送的消息类型
        String msgType = resultMap.get("MsgType");

        //处理文本消息
        if (msgType != null && msgType.equalsIgnoreCase("text")) {
            //对用户发送给公众号的文字进行处理
            //TODO
        }
        //处理事件消息
        else if (msgType != null && msgType.equalsIgnoreCase("event")) {

            String event = resultMap.get("Event");

            //关注事件
            if (event != null && event.equalsIgnoreCase("subscribe")) {
                aircubeUserService.handleSubscribe(resultMap, request, response);
            }
            //取消关注事件
            else if (event != null && event.equalsIgnoreCase("unsubscribe")) {
                aircubeUserService.handleUnSubscribe(resultMap);
            }
            //地理位置上报
            else if (event != null && event.equalsIgnoreCase("LOCATION")) {
                aircubeUserService.handleUpdateLocation(resultMap);
            }
            //点击菜单跳转url事件
            else if (event != null && event.equalsIgnoreCase("VIEW")) {
                aircubeUserService.handleView(resultMap);
            }
        }
        //硬件事件处理
        else if (msgType != null && msgType.equalsIgnoreCase("device_event")) {

            String event = resultMap.get("Event");

            //绑定设备事件
            if (event != null && event.equalsIgnoreCase("bind")) {
                aircubeUserService.handDvcBind(resultMap, request, response);
            }
            //设备解除绑定事件
            else if (event != null && event.equalsIgnoreCase("unbind")) {
                aircubeUserService.handleUnbind(resultMap, request, response);
            }
        }
        //接受设备消息事件处理
        else if (msgType != null && msgType.equalsIgnoreCase("device_text")) {
            aircubeUserService.handleDvcMsg(resultMap, request, response);
        }
    }

    /**
     * 处理微信硬件消息
     *
     * @param resultMap
     * @param request
     * @param response
     */
    public void handleDvcMsg(Map<String, String> resultMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取微信服务器推送的消息类型
        String msgType = resultMap.get("MsgType");

        //接受设备消息事件处理
        if (msgType != null && msgType.equalsIgnoreCase("device_text")) {
            aircubeUserService.handleDvcMsg(resultMap, request, response);
        }
    }

    /**
     * 通过Mac地址添加设备信息及微信认证
     *
     * @param aa
     */
    public Map handleDvcInit(AircubeAuthorizeVo aa) {

        List<String> macList = aa.getMacList();
        String productId = aa.getProductId();

        AircubeInfo aircubeInfo = new AircubeInfo();

        //返回结果
        Map<String, List<Map<String, String>>> returnMap = new HashMap<>();

        //添加新的设备信息
        macList.forEach((mac) -> {
            aircubeInfo.setMac(mac);
            aircubeInfo.setDeviceId("LSD" + mac);
            aircubeInfo.setCreateTime(DateFormatUtil.getStartTime());
            aircubeInfo.setProductId(productId);
            //生产但暂未授权
            aircubeInfo.setStatus(EnumInfoStatus.unauthorize.getCode());
            iAircubeInfoMapper.insert(aircubeInfo);
        });


        //微信授权
        JSONObject postAuthirizeJson = new JSONObject();
        List<JSONObject> deviceInfoList = new ArrayList<>();

        String connectProtocol = wechatConfig.getBluetoothCfg().getConnectProtocol();
        String authKey = wechatConfig.getBluetoothCfg().getAuthKey();
        String closeStrategy = wechatConfig.getBluetoothCfg().getCloseStrategy();
        String connStrategy = wechatConfig.getBluetoothCfg().getConnStrategy();
        String cryptMethod = wechatConfig.getBluetoothCfg().getCryptMethod();
        String authVer = wechatConfig.getBluetoothCfg().getAuthVer();
        String manuMacPos = wechatConfig.getBluetoothCfg().getManuMacPos();
        String serMacPos = wechatConfig.getBluetoothCfg().getSerMacPos();

        macList.forEach((mac) -> {
            JSONObject dvcJson = new JSONObject();
            dvcJson.put("id", "LSD" + mac);
            dvcJson.put("mac", mac);
            dvcJson.put("connect_protocol", connectProtocol);
            dvcJson.put("auth_key", authKey);
            dvcJson.put("close_strategy", closeStrategy);
            dvcJson.put("conn_strategy", connStrategy);
            dvcJson.put("crypt_method", cryptMethod);
            dvcJson.put("auth_ver", authVer);
            dvcJson.put("manu_mac_pos", manuMacPos);
            dvcJson.put("ser_mac_pos", serMacPos);

            deviceInfoList.add(dvcJson);
        });

        postAuthirizeJson.put("device_num", macList.size());
        postAuthirizeJson.put("device_list", deviceInfoList);
        postAuthirizeJson.put("product_id", productId);
        postAuthirizeJson.put("op_type", "0");

        String accessToken = WechatUtil.getAccessToken();

        try {
            JSONObject resultMap = WechatUtil.dvcAuthorize(accessToken, postAuthirizeJson);

            //对结果进行分析处理
            List<JSONObject> resultJsonList = (List<JSONObject>) resultMap.get("resp");

            resultJsonList.forEach((resultJson) -> {

                String deviceId = (String) ((JSONObject) resultJson.get("base_info")).get("device_id");

                List<Map<String, String>> list = new ArrayList<Map<String, String>>();

                Map<String, String> errmsgMap = new HashMap<String, String>();
                Map<String, String> errcodeMap = new HashMap<String, String>();
                Map<String, String> deviceIdMap = new HashMap<String, String>();
                Map<String, String> ticketMap = new HashMap<String, String>();

                errmsgMap.put("errmsg", (String) resultJson.get("errmsg"));
                errcodeMap.put("errcode", resultJson.get("errcode") + "");
                deviceIdMap.put("device_id", deviceId);

                //返回结果为失败
                if ((int) resultJson.get("errcode") != 0 || !resultJson.get("errmsg").equals("ok")) {
                    logger.error("设备认证失败,device_id为:" + deviceId);
                } else {
                    //更新设备状态
                    AircubeInfo air = new AircubeInfo();
                    air.setDeviceId(deviceId);
                    air = aircubeInfoService.getCube(air);
                    air.setStatus(EnumInfoStatus.unbind.getCode());
                    aircubeInfoService.updateById(air);

                    //对授权成功的设备生成ticket(单一授权)
                    JSONObject getTicketJson = new JSONObject();
                    getTicketJson.put("device_num", "1");

                    List<String> deviceIdList = new ArrayList<String>();
                    deviceIdList.add(deviceId);
                    getTicketJson.put("device_id_list", deviceIdList);
                    JSONObject resultTicketMap = WechatUtil.getDvcTicket(accessToken, getTicketJson);
                    //返回结果处理
                    if ((int) resultTicketMap.get("errcode") != 0 || !resultTicketMap.get("errmsg").equals("ok")) {
                        logger.error("设备二维码获取失败,device_id为:" + deviceId);
                    }
                    //成功获得二维码
                    else {
                        //插入设备二维码信息
                        String ticket = (String) ((List<JSONObject>) resultTicketMap.get("code_list")).get(0).get("ticket");
                        air.setQrticket(ticket);
                        aircubeInfoService.updateById(air);
                        //将二维码加入到返回信息
                        ticketMap.put("ticket", ticket);
                    }
                }

                list.add(errmsgMap);
                list.add(errcodeMap);
                list.add(deviceIdMap);
                list.add(ticketMap);
                returnMap.put(deviceId, list);
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return returnMap;
    }

    /**
     * 主动发消息给设备
     *
     * @param dvcMsg
     * @return
     */
    public JSONObject sendMsgToDvc(DvcMsgVo dvcMsg) {

        //传入参数
        JSONObject json = new JSONObject();
        json.put("device_id", dvcMsg.getDeviceId());
        json.put("open_id", dvcMsg.getOpenid());

        //该设备4个小时内不重复请求历史数据。
        AircubeInfo aircubeInfo = aircubeInfoService.selectOne(new EntityWrapper<>(new AircubeInfo().setDeviceId(dvcMsg.getDeviceId())));
        if (!aircubeInfo.getBackTime().before(DateFormatUtil.addDate(new Date(), -4, Calendar.HOUR))) {
            return json;
        }

        //返回结果
        JSONObject resultJson = new JSONObject();
        String accessToken = WechatUtil.getAccessToken();

        //上报历史数据（微信硬件云）
        byte[] content = {(byte) 0xEC, 0x02, 0x01, 0x02, 0x05, 0x68};
        json.put("content", EmojiFilter.encode(content));

        //将设备类型(目前为公众号原始id)参数加入post
        json.put("device_type", wechatConfig.getConfig().getWechatId());
        resultJson = WechatUtil.postToDvc(accessToken, json);

        //防止空指针异常
        try {
            if ((int) resultJson.get("ret") != 0) {
                logger.error("主动发送消息给设备失败！\n 设备ID：" + json.get("device_id") + "\n 微信账户openid:" + json.get("open_id"));
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return resultJson;
    }

    /**
     * js api签名
     *
     * @param url
     */
    public Map jsSign(String url) {

        Map<String, String> result = new HashMap<>();

        //准备js sdk相关参数
        String accessToken = WechatUtil.getAccessToken();
        JSONObject jsonObject = WechatUtil.getJSAPITicket(accessToken);

        String noncestr = DateFormatUtil.createNonceStr();
        String timestamp = DateFormatUtil.getCurrentUnixTime();
        String jsApiTicket = (String) jsonObject.get("ticket");

        String string = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        String signatureJsApi = SecurityUtil.encode(string, SecurityUtil.SHA_1);
//            logger.info("signatureJsApi:" + signatureJsApi + "\njsapi_ticket:" + jsApiTicket
//                    + "\nnoncestr:" + noncestr + "\ntimestamp:" + timestamp);
        result.put("url", url);
        result.put("jsapi_ticket", jsApiTicket);
        result.put("nonceStr", noncestr);
        result.put("timestamp", timestamp);
        result.put("signature", signatureJsApi);
        return result;
    }

    /**
     * 创建自定义菜单
     */
    public Map<String, String> creatMenu() {

        Map<String, String> result = null;
        //请求创建菜单值
        JSONObject jsonPost = new JSONObject();
        List<JSONObject> buttonList = new JSONArray();

        //按钮一
        JSONObject button1 = new JSONObject();
        //跳转URL
        button1.put("type", "view");
        button1.put("name", "我的魔方");
        String wechatMenuUrl = Constants.WECHAT_MENU_CONFIG_URL;
        //跳转页面可以获取用户基本信息
        wechatMenuUrl += "?appid=" + wechatConfig.getConfig().getAppid() + "&redirect_uri=" + Constants.AIRCUBE_INDEX_URL
                + "&response_type=code&scope=snsapi_userinfo&state=senthink#wechat_redirect";
        button1.put("url", wechatMenuUrl);

        buttonList.add(button1);
        jsonPost.put("button", buttonList);

        String accessToken = WechatUtil.getAccessToken();
        result = WechatUtil.creatMenu(accessToken, jsonPost);
        return result;
    }
}
