package com.senthink.www.domain.po.wechat;

/**
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.quartz
 * @Description :  微信语音消息
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午5:35
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class VoiceMessage extends BaseMessage 
{
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
