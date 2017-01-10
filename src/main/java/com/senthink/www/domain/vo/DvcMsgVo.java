package com.senthink.www.domain.vo;

/**
 *
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.domain.param
 * @Description :  主动发送指令给设备的接口参数，暂时只包括后台向
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月28日 上午9:40
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class DvcMsgVo {

    /**发送给设备的设备连接的用户openid*/
    String openid;

    /**设备id*/
    String deviceId;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
