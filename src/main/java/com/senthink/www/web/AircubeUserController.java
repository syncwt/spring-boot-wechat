package com.senthink.www.web;

import com.senthink.www.common.WechatUtil;
import com.senthink.www.domain.po.AircubeInfo;
import com.senthink.www.domain.vo.AircubeAuthorizeVo;
import com.senthink.www.domain.vo.DvcMsgVo;
import com.senthink.www.exception.BusinessException;
import com.senthink.www.response.Message;
import com.senthink.www.service.AircubeInfoService;
import com.senthink.www.service.AircubeUserService;
import com.senthink.www.service.WechatMsgService;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AircubeUser 控制层
 */
@Api(value = "/user", description = "interface to front and dvc")
@RestController
@RequestMapping(value = "/user")
public class AircubeUserController {

    @Autowired
    private WechatMsgService wechatMsgService;
    @Autowired
    private AircubeUserService aircubeUserService;
    @Autowired
    private AircubeInfoService aircubeInfoService;

    /**
     * jsapi签名认证
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "/sign4JsApi/{url}/{code}/{state}")
    public Message sign4JsApi(@PathVariable String url, @PathVariable String code, @PathVariable String state) {
        url = url + "/?code=" + code + "&state=" + state;
        Map<String, String> result = wechatMsgService.jsSign(url);
        return new Message(result);
    }

    /**
     * 通过Mac地址添加设备信息及微信认证
     * 工厂生产设备时烧制Mac地址后调用该接口获取二维码
     * 该接口只接受同一型号产品的批量认证
     *
     * @param aircubeAuthorizeVo
     * @return
     */
    @RequestMapping(value = "/registAndAuthorizeDvc")
    public Message registAndAuthorizeDvc(@RequestBody AircubeAuthorizeVo aircubeAuthorizeVo) {

        Map<String, List<Map<String, String>>> result = wechatMsgService.handleDvcInit(aircubeAuthorizeVo);
        return new Message(result);
    }

    /**
     * 主动发消息给设备的接口
     * 参数包括device_id(设备id),open_id(微信用户openid)
     * 默认发送给设备请求历史数据指令，返回通道为微信服务器
     *
     * @param dvcMsg
     * @return
     */
    @RequestMapping(value = "/sendMsgToDvc")
    public Message sendMsgToDvc(@RequestBody DvcMsgVo dvcMsg) {

        JSONObject resultJson = wechatMsgService.sendMsgToDvc(dvcMsg);
        return new Message(resultJson);
    }

    /**
     * 微信内页面跳转项目页面时返回的code用以获取该用户基本信息
     * 包括该用户绑定的设备列表
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/getUserOpenid/{code}")
    public Message getUserOpenid(@PathVariable String code) {
        JSONObject resultJson = WechatUtil.getOpenId(code);
        String openid = (String) resultJson.get("openid");
        if (openid != null) {
            resultJson.put("device_id", aircubeUserService.findUserDvcList(openid));
        }
        return new Message(resultJson);
    }

    /**
     * 通过Mac获取二维码信息
     *
     * @param mac
     * @return
     */
    @RequestMapping(value = "/air/getTicket")
    public Message getTicketByMac(@RequestParam String mac) {
        JSONObject jsonObject = aircubeInfoService.findTicket(mac);
        return new Message(jsonObject);
    }

    /**
     * 根据openid获取所属魔方
     * @param openId
     * @return
     */
    @RequestMapping("air/getAir/{openId}")
    public Message getAirByOprnId(@PathVariable String openId) {
        Integer userId = aircubeUserService.getUserIdByOpenId(openId);
        List<AircubeInfo> list = aircubeInfoService.getAirByuserId(userId);
        return new Message(BusinessException.CODE_SUCCESS, list);
    }

}