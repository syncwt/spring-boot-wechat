package com.senthink.www.domain.vo;

import java.util.List;

/**
 *
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.domain.param
 * @Description :  用于接收认证设备的参数
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月28日 上午9:33
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class AircubeAuthorizeVo {

    /**Mac地址列表*/
    List<String> macList;

    /**设备类型id*/
    String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<String> getMacList() {
        return macList;
    }

    public void setMacList(List<String> macList) {
        this.macList = macList;
    }
}
