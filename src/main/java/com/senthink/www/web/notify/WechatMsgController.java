package com.senthink.www.web.notify;

import com.senthink.www.response.Message;
import com.senthink.www.common.JsonUtil;
import com.senthink.www.common.XMLUtil;
import com.senthink.www.domain.po.PageData;
import com.senthink.www.exception.BusinessException;
import com.senthink.www.service.WechatMsgService;
import com.senthink.www.web.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.web
 * @Description :  接收和处理微信服务器消息
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 上午9:49
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
@Api(value = "/reply", description = "receive and respons messages from wechat server")
@RestController
@RequestMapping(value = "/reply")
public class WechatMsgController extends BaseController {

    @Autowired
    private WechatMsgService wechatMsgService;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(WechatMsgController.class);

    /**
     * 接收微信服务器发送的关于公众号的消息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/receiveMsg")
    public Message receiveMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //set charactrt to utf-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PageData pd = new PageData();
        pd = this.getPageData();

        //wechat doc at "https://mp.weixin.qq.com/wiki/8/f9a0b8382e0b77d87b3bcc1ce6fbc104.html"
        //微信加密签名
        String signature = pd.getString("signature");
        //时间戳
        String timestamp = pd.getString("timestamp");
        //随机数
        String nonce = pd.getString("nonce");
        //字符串
        String echostr = pd.getString("echostr");

        //verifiy identity
        if (null != signature && null != timestamp && null != nonce && null != echostr) {
            wechatMsgService.serviceVerification(pd, response);
//            logger.info("signature:" + signature + "\ntimestamp:" + timestamp + "\nnonce:" + nonce + "\nechostr:" + echostr);
        }
        //message handle
        else {
            response.reset();
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            //获取微信服务器推送的消息
            Map<String, String> resultMap = XMLUtil.parseXml(request);
            if (resultMap != null && !resultMap.isEmpty()) {
                wechatMsgService.handleMsg(resultMap, request, response);
            }
        }
        return new Message(BusinessException.CODE_SUCCESS);

    }

    /**
     * 如果已在公众平台－设备功能－设置中启动并设置服务器配置
     * 设备消息转发到该接口
     * 接口数据为json不是xml
     * 我们公众号暂未开启该服务
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/receiveDvcMsg")
    public Message receiveDvcMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //set charactrt to utf-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PageData pd = new PageData();
        pd = this.getPageData();

        //wechat doc at "https://mp.weixin.qq.com/wiki/8/f9a0b8382e0b77d87b3bcc1ce6fbc104.html"
        //微信加密签名
        String signature = pd.getString("signature");
        //时间戳
        String timestamp = pd.getString("timestamp");
        //随机数
        String nonce = pd.getString("nonce");
        //字符串
        String echostr = pd.getString("echostr");

        //verifiy identity
        if (null != signature && null != timestamp && null != nonce && null != echostr) {
            wechatMsgService.serviceVerification(pd, response);
        }
        //message handle
        else {
            response.reset();
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            //获取微信服务器推送的消息(Json)
            Map<String, String> resultMap = JsonUtil.parseJson(request);
            if (resultMap != null && !resultMap.isEmpty()) {
                //handle message
                wechatMsgService.handleDvcMsg(resultMap, request, response);
            }
        }

        return new Message(BusinessException.CODE_SUCCESS);
    }

    /**
     * 创建自定义菜单
     * 仅发布前使用，接口不对外
     *
     * @return
     */
    @RequestMapping(value = "/creatMenu")
    public Message creatMenu() {
        Map<String, String> resultMap = wechatMsgService.creatMenu();
        return new Message(resultMap);
    }
}
