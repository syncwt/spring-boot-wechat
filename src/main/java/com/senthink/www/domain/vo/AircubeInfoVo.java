/**
 * 
 */
package com.senthink.www.domain.vo;

import java.util.Date;

/**
 * @ClassName           : AircubeInfoVo
 * @Description         :
 * @Author              : xujialin
 * @CreationDate        : 2016年12月15日下午4:21:07
 */
public class AircubeInfoVo {

	private String ticks;

	/**  */
	private Integer id;
	// @NotEmpty(message = "魔方名称不能为空")
	private String name;

	/** 消息开启 */
	private String newsOpen;

	/** 场景（客厅之类） */
	private String scene;

	/**  */
	private Date createTime;

	/**  */
	private Date updateTime;

	/** mac地址 */
	private String mac;

	/** 设备id */
	private String deviceId;


	private String qrticket;

	public String getQrticket() {
		return qrticket;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewsOpen() {
		return newsOpen;
	}

	public void setNewsOpen(String newsOpen) {
		this.newsOpen = newsOpen;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTicks() {
		return ticks;
	}

	public void setTicks(String ticks) {
		this.ticks = ticks;
	}

	public void setQrticket(String qrticket) {
		this.qrticket = qrticket;
	}

}
