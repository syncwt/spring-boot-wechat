package com.senthink.www.service;

import com.baomidou.framework.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.senthink.www.common.WechatUtil;
import com.senthink.www.dao.IAircubeInfoMapper;
import com.senthink.www.domain.po.AircubeInfo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * AircubeInfo 表数据服务层接口实现类
 */
@Service
public class AircubeInfoService extends ServiceImpl<IAircubeInfoMapper, AircubeInfo> {

    private Logger logger = LoggerFactory.getLogger(AircubeInfoService.class);
    @Autowired
    private IAircubeInfoMapper iAircubeInfoMapper;


    public AircubeInfo getCube(AircubeInfo air) {
        return this.selectOne(new EntityWrapper<>(air));
    }

    public String getOpenIdBydvcId(String devId) {
        String openid = iAircubeInfoMapper.getOpenIdBydvcId(devId);
        return openid;
    }

    /**
     * 查询设备二维码
     *
     * @param mac
     */
    public JSONObject findTicket(String mac) {
        JSONObject resultTicket = new JSONObject();
        String deviceId = "LSD" + mac;
        String accessToken = WechatUtil.getAccessToken();

        JSONObject getTicketJson = new JSONObject();
        getTicketJson.put("device_num", "1");

        List<String> deviceIdList = new ArrayList<String>();
        deviceIdList.add(deviceId);
        getTicketJson.put("device_id_list", deviceIdList);
        resultTicket = WechatUtil.getDvcTicket(accessToken, getTicketJson);

        //返回结果处理
        if ((int) resultTicket.get("errcode") != 0 || !resultTicket.get("errmsg").equals("ok")) {
            logger.error("设备二维码获取失败,device_id为:" + deviceId);
        }
        return resultTicket;
    }

    /**
     * 通过dvcId获得airId
     *
     * @param deviceId
     * @return
     * @time 2016年12月26日下午1:47:40
     */
    public Integer getAirIdByDeviceId(String deviceId) {
        return iAircubeInfoMapper.getAirIdByDeviceId(deviceId);
    }


    /**
     * 根据openid获取所属魔方
     *
     * @param userId
     * @return
     * @time 2017年1月3日上午9:12:25
     */
    public List<AircubeInfo> getAirByuserId(Integer userId) {
        return iAircubeInfoMapper.getAirByUserId(userId);

    }

}