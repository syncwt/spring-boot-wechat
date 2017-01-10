package com.senthink.www.util;

import com.senthink.www.common.RedisUtil;
import com.senthink.www.common.WechatUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Jason on 2016/12/28.
 */
@Component
public class SyncWechatToken {

    @Inject
    @Qualifier("taskExecutor")
    private TaskExecutor taskExecutor;

    @Inject
    private RedisUtil redisUtil;

    public String syncToken() {
        taskExecutor.execute(() -> {
            //有效期小于十分钟，更新redis中access_token
            if (redisUtil.getExpire("access_token") < 10 * 60) {
                redisUtil.set("access_token", WechatUtil.getAccessTokenByWechat(), (long) 2 * 60 * 60);
            }
        });
        return "success";
    }
}
