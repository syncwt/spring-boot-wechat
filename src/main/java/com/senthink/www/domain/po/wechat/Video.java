package com.senthink.www.domain.po.wechat;

/**
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.quartz
 * @Description :  视频model
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午5:35
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class Video 
{
	// 媒体文件id
	private String MediaId;
	// 缩略图的媒体id
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
