package com.senthink.www.domain.po.wechat;

/**
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.quartz
 * @Description :  微信视频消息
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午5:35
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class VideoMessage extends BaseMessage {
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
