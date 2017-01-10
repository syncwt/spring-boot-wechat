
package com.senthink.www.service;

import com.baomidou.framework.service.impl.ServiceImpl;
import com.senthink.www.common.DateFormatUtil;
import com.senthink.www.common.EmojiFilter;
import com.senthink.www.common.MessageUtil;
import com.senthink.www.common.WechatUtil;
import com.senthink.www.config.WechatConfig;
import com.senthink.www.dao.IAircubeUserMapper;
import com.senthink.www.domain.Constants;
import com.senthink.www.domain.po.AircubeInfo;
import com.senthink.www.domain.po.AircubeUser;
import com.senthink.www.domain.po.PageData;
import com.senthink.www.domain.po.wechat.Article;
import com.senthink.www.domain.po.wechat.NewsMessage;
import com.senthink.www.exception.BusinessException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.service
 * @Description :  微信用户业务
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午3:48
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */

@Service
public class AircubeUserService extends ServiceImpl<IAircubeUserMapper, AircubeUser> {
    @Autowired
    private IAircubeUserMapper iAircubeUserMapper;
    @Autowired
    private AircubeInfoService aircubeInfoService;
    @Autowired
    private AircubeUserService aircubeUserService;
    @Autowired
    private WechatConfig wechatConfig;


    private Logger logger = LoggerFactory.getLogger(AircubeUserService.class);

    /**
     * 处理用户关注事件
     *
     * @param resultMap
     * @param request
     * @param response
     */
    public void handleSubscribe(Map<String, String> resultMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("------------------进入用户关注事件处理------------------");

        PageData pd = new PageData();
        String openid = resultMap.get("FromUserName");
        String accessToken = WechatUtil.getAccessToken();

        AircubeUser aircubeUser = new AircubeUser();
        aircubeUser.setOpenid(openid);
        aircubeUser = iAircubeUserMapper.selectUserInfoByOpenid(aircubeUser);
        pd.put("openid", openid);

        //首次关注
        if (aircubeUser == null) {
            Map<String, String> userMap = WechatUtil.getUserInfoByOpenid(accessToken, openid);

            userMap.put("gmt_subscribe", DateFormatUtil.UnixDateTowinodwxDate(userMap.get("subscribe_time")));
            userMap.put("gmt_regist", DateFormatUtil.getCurrentTime());
            //解决微信用户昵称含emoji表情兼容性,直接使用mysql的to_base64()方法

            pd.putAll(userMap);
            boolean isSuccess = iAircubeUserMapper.saveUserInfo(pd);
            if (!isSuccess) {
                logger.error("用户关注插入数据异常,openid:" + openid);
            }
        }
        //取关后再次关注,更新用户基本信息
        else {
            Map<String, String> userMap = WechatUtil.getUserInfoByOpenid(accessToken, openid);
            userMap.put("gmt_subscribe", DateFormatUtil.UnixDateTowinodwxDate(userMap.get("subscribe_time")));

            pd.putAll(userMap);
            int effectRows = 0;
            effectRows = iAircubeUserMapper.updateUserInfo(pd);
            if (effectRows == 0) {
                logger.error("用户重新关注更新数据异常,openid:" + openid);
            }

            logger.info("------------------用户关注事件处理结束------------------");
        }

        request.getSession().setAttribute("user", pd);
        request.getSession().setAttribute("openid", openid);
        logger.info("-----------session中新加入的openid:" + request.getSession().getAttribute("openid"));

        //给关注用户发送图文消息
        Article article = new Article();
        article.setTitle(Constants.DESCRIBE_NEWS_TITLE);
        article.setDescription(Constants.DESCRIBE_NEWS_DESCRIPTION);
        article.setPicUrl(Constants.DESCRIBE_NEWS_PIC_URL);
        article.setUrl(Constants.DESCRIBE_NEWS_URL + "?appid=" + wechatConfig.getConfig().getAppid() + "&redirect_uri=" + Constants.AIRCUBE_INDEX_URL
                + "&response_type=code&scope=snsapi_userinfo&state=senthink#wechat_redirect");
        String content = sendNewsMessage(resultMap, article);
        response.getOutputStream().write(content.getBytes());

        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 取关处理
     *
     * @param resultMap
     */
    public void handleUnSubscribe(Map<String, String> resultMap) {

        logger.info("------------------进入用户取消关注事件处理------------------");

        PageData pd = new PageData();
        pd.put("openid", resultMap.get("FromUserName"));
        pd.put("is_subscribe", "0");

        int effectRows = 0;
        effectRows = iAircubeUserMapper.updateUserSubsuribe(pd);
        if (effectRows != 1) {
            logger.error("用户重新关注更新数据异常,openid:" + pd.getString("openid"));
        }

        logger.info("------------------用户取消关注事件处理结束------------------");
    }

    /**
     * 向新关注微信用户推送图文消息
     *
     * @param resultMap
     * @return
     */
    public String sendNewsMessage(Map<String, String> resultMap, Article article) {

        NewsMessage newsMsg = new NewsMessage();
        newsMsg.setToUserName(resultMap.get("FromUserName").toString());
        newsMsg.setFromUserName(resultMap.get("ToUserName").toString());
        newsMsg.setCreateTime(System.currentTimeMillis() / 1000);
        newsMsg.setMsgType("news");

        List<Article> articles = new ArrayList<Article>();
        articles.add(article);

        newsMsg.setArticles(articles);
        newsMsg.setArticleCount(articles.size());
        return MessageUtil.messageToXml(newsMsg);
    }

    /**
     * 处理设备绑定事件
     *
     * @param resultMap
     * @param request
     * @param response
     */
    @Transactional
    public void handDvcBind(Map<String, String> resultMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("------------------进入用户绑定设备事件处理------------------");
        String deviceId = resultMap.get("DeviceID");
        String openId = resultMap.get("OpenID");

        // 查询是否存在魔方信息
        AircubeInfo air = new AircubeInfo();
        air.setDeviceId(deviceId);
        air = aircubeInfoService.getCube(air);

        if (air != null) {
            // do something
        }

        //第三方服务器数据导入异常,在这里重新导入
        else {
            // do something

        }

        //查询该魔方是否以经绑定某个用户
        // do something

        //如果已经绑定，强制解绑
        // do something

        //更新用户设备数量，从地址表中查询修改
        int dvcNum = this.updateUserDvcNums(openId);

        //查询绑定设备用户是否属于绑定多个设备，且绑定信息未完善
        //若是，推送完善信息链接
        if (dvcNum > 1) {
            //给用户发送完善信息图文消息
            Article article = new Article();
            article.setTitle(Constants.BIND_NEWS_TITLE);
            article.setDescription(Constants.BIND_NEWS_DESCRIPTION);
            article.setPicUrl(Constants.BIND_NEWS_PIC_URL);
            article.setUrl(Constants.BIND_NEWS_URL + "?appid=" + wechatConfig.getConfig().getAppid() + "&redirect_uri=" + Constants.AIRCUBE_INDEX_URL
                    + "&response_type=code&scope=snsapi_userinfo&state=senthink#wechat_redirect");
            String content = sendNewsMessage(resultMap, article);
            response.getOutputStream().write(content.getBytes());
        }

        //保存成功后,回复微信绑定事件
        JSONObject replyJson = new JSONObject();
        replyJson.put("ToUserName", resultMap.get("ToUserName"));
        replyJson.put("FromUserName", resultMap.get("FromUserName"));
        replyJson.put("CreateTime", DateFormatUtil.getCurrentUnixTime());
        replyJson.put("MsgType", "device_text");
        replyJson.put("Event", resultMap.get("Event"));
        replyJson.put("DeviceType", resultMap.get("DeviceType"));
        replyJson.put("DeviceID", deviceId);

        //绑定时不给设备发指令
        replyJson.put("Content", "");
        replyJson.put("SessionID", resultMap.get("SessionID"));

        response.getOutputStream().write(MessageUtil.jsonToXml(replyJson).getBytes());

        response.getOutputStream().flush();
        response.getOutputStream().close();

        logger.info("------------------用户绑定设备事件处理结束------------------");
    }

    /**
     * 处理设备解绑事件
     *
     * @param resultMap
     * @param request
     * @param response
     * @throws BusinessException
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void handleUnbind(Map<String, String> resultMap, HttpServletRequest request, HttpServletResponse response)
            throws IOException, BusinessException {

        logger.info("------------------进入用户解除绑定设备事件处理------------------");
        String deviceId = resultMap.get("DeviceID");
        String openid = resultMap.get("OpenId");

        // 根据deviceId 更新魔方信息表
        // do something

        //更新用户设备数量
        this.updateUserDvcNums(openid);

        //保存成功后,回复微信解除绑定事件
        JSONObject replyJson = new JSONObject();
        replyJson.put("ToUserName", resultMap.get("ToUserName"));
        replyJson.put("FromUserName", resultMap.get("FromUserName"));
        replyJson.put("CreateTime", DateFormatUtil.getCurrentUnixTime());
        replyJson.put("MsgType", "device_text");
        replyJson.put("Event", resultMap.get("Event"));
        replyJson.put("DeviceType", resultMap.get("DeviceType"));
        replyJson.put("DeviceID", deviceId);

        //解除绑定时不给设备发指令
        replyJson.put("Content", "");
        replyJson.put("SessionID", resultMap.get("SessionID"));

        response.getOutputStream().write(MessageUtil.jsonToXml(replyJson).getBytes());

        response.getOutputStream().flush();
        response.getOutputStream().close();

        logger.info("------------------用户解除绑定设备事件处理结束------------------");
    }

    /**
     * 处理设备推送过来的消息
     *
     * @param resultMap
     * @param request
     * @param response
     */
    public void handleDvcMsg(Map<String, String> resultMap, HttpServletRequest request, HttpServletResponse response) throws IOException, BusinessException {
        logger.info("------------------进入设备消息事件处理------------------");
        //获取消息内容
        byte[] contentGet = EmojiFilter.decode(resultMap.get("Content"));
        String deviceId = resultMap.get("DeviceID");

        //根据deviceId 更新该魔方mongodb中历史数据
        // do something

        //保存成功后,回复微信绑定事件
        JSONObject replyJson = new JSONObject();
        replyJson.put("ToUserName", resultMap.get("ToUserName"));
        replyJson.put("FromUserName", resultMap.get("FromUserName"));
        replyJson.put("CreateTime", resultMap.get("CreateTime"));
        replyJson.put("MsgType", "device_text");
        replyJson.put("DeviceType", resultMap.get("DeviceType"));
        replyJson.put("DeviceID", deviceId);

        //想要发送给设备的指令信息等,用BASE64编码
        // do something
//        byte[] content = {(byte) 0xEC, 0x02, 0x01,0x02,0x05, 0x68};
//        replyJson.put("Content", EmojiFilter.encode(content));
        replyJson.put("Content", "");

        response.getOutputStream().write(MessageUtil.jsonToXml(replyJson).getBytes());

        response.getOutputStream().flush();
        response.getOutputStream().close();

        logger.info("------------------设备消息事件处理结束------------------");
    }

    /**
     * 处理位置信息保存更新
     *
     * @param resultMap
     */
    public void handleUpdateLocation(Map<String, String> resultMap) {

        logger.info("------------------进入用户关注上报位置信息处理------------------");

        String openid = resultMap.get("FromUserName");

        AircubeUser aircubeUser = new AircubeUser();
        aircubeUser.setOpenid(openid);
        aircubeUser = iAircubeUserMapper.selectUserInfoByOpenid(aircubeUser);

        // 已经处理用户关注事件
        if (aircubeUser != null) {
            String gps = resultMap.get("Latitude") + "," + resultMap.get("Longitude");

            aircubeUser.setGps(gps);
            int effectRows = iAircubeUserMapper.updateById(aircubeUser);
            if (effectRows != 1) {
                logger.error("用户位置信息数据更新异常,openid:" + openid);
            }
        }
        // 用户关注处理还未结束,用户信息没有保存
        else {
            logger.error("用户数据还未保存,openid:" + openid);
        }

        logger.info("------------------用户上报位置信息事件处理结束------------------");
    }

    /**
     * 根据openId查询userId
     *
     * @param openId
     * @return
     * @time 2016年12月26日上午11:31:49
     */
    public Integer getUserIdByOpenId(String openId) {
        return iAircubeUserMapper.getUserIdByOpenId(openId);
    }

    /**
     * 根据用户地址表中的绑定关系更新该用户的用户表设备数量
     * 返回该用户设备数
     *
     * @param openid
     */
    public int updateUserDvcNums(String openid) {
        //获取绑定设备数量
        Integer userId = this.getUserIdByOpenId(openid);
        //需要连接魔方表查询魔方状态>=2即表示已绑定
        int dvcNum = iAircubeUserMapper.selectDvcNums(openid);
        //更新用户表数据
        AircubeUser au = new AircubeUser();
        au.setId(userId);
        au.setDvcNum(dvcNum);
        this.updateById(au);

        return dvcNum;
    }

    /**
     * 用户点击按钮跳转url事件推送
     * 暂不做处理
     *
     * @param resultMap
     */
    public void handleView(Map<String, String> resultMap) {
        //跳转链接
        String url = resultMap.get("EventKey");

        String openid = resultMap.get("FromUserName");
    }

    /**
     * 通过用户openid获取该用户绑定的设备列表（本公众号绑定的设备）
     *
     * @return
     */
    public List<String> findUserDvcList(String openid) {
        return iAircubeUserMapper.findUserDvcList(openid);
    }
}
