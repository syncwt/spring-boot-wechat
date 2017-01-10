 package com.senthink.www.domain.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName("aircube_info")
public class AircubeInfo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 空气魔方名字 */
	private String name;

	/** 消息开启 */
	@TableField(value = "news_open")
	private String newsOpen;

	/** 场景（客厅之类） */
	private String scene;

	/**  */
	@TableField(value = "create_time")
	private Date createTime;

	/**  */
	@TableField(value = "update_time")
	private Date updateTime;

	/** mac地址 */
	private String mac;

	/** 设备id */
	@TableField(value = "device_id")
	private String deviceId;

	private String qrticket;

	private String productId;

	private Integer status;
	@TableField(value = "dvc_base_id")
	private Integer dvcBaseId;
	@TableField(value = "back_time")
	private Date backTime;

	public Integer getDvcBaseId() {
		return dvcBaseId;
	}

	public void setDvcBaseId(Integer dvcBaseId) {
		this.dvcBaseId = dvcBaseId;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getQrticket() {
		return qrticket;
	}

	public void setQrticket(String qrticket) {
		this.qrticket = qrticket;
	}

	public Integer getId() {
		return this.id;
	}

	public AircubeInfo setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public AircubeInfo setName(String name) {
		this.name = name;
		return this;
	}

	public String getNewsOpen() {
		return this.newsOpen;
	}

	public AircubeInfo setNewsOpen(String newsOpen) {
		this.newsOpen = newsOpen;
		return this;
	}

	public String getScene() {
		return this.scene;
	}

	public AircubeInfo setScene(String scene) {
		this.scene = scene;
		return this;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public AircubeInfo setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public AircubeInfo setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public String getMac() {
		return this.mac;
	}

	public AircubeInfo setMac(String mac) {
		this.mac = mac;
		return this;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public AircubeInfo setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

}
