package com.senthink.www.web;

import com.senthink.www.domain.po.PageData;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.web
 * @Description :  基类控制类,用于基础注入等
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 上午10:35
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class BaseController {
    private static final long serialVersionUID = 6357869213649815390L;

    /**
     * new PageData对象
     *
     * @return
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * 得到request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
}
