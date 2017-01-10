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
@TableName("aircube_user")
public class AircubeUser implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 微信用户唯一openid */
	private String openid;

	/** 昵称 */
	private String nickname;

	/** 性别 */
	private String sex;

	/** 图像 */
	@TableField(value = "head_img_url")
	private String headImgUrl;

	/** 注册时间 */
	@TableField(value = "gmt_regist")
	private Date gmtRegist;

	/** 城市 */
	private String city;

	/** 国家 */
	private String country;

	/** 省份 */
	private String province;

	/** 关注时间,取关后重新关注更新 */
	@TableField(value = "gmt_subscribe")
	private Date gmtSubscribe;

	/** 是否订阅公众号 0 未关注  1 已关注 */
	@TableField(value = "is_subscribe")
	private Integer isSubscribe;

	/** 绑定设备数量 */
	@TableField(value = "dvc_num")
	private Integer dvcNum;

	/** 用户所在位置信息 */
	private String gps;

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public Integer getId() {
		return this.id;
	}

	public AircubeUser setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getOpenid() {
		return this.openid;
	}

	public AircubeUser setOpenid(String openid) {
		this.openid = openid;
		return this;
	}

	public String getNickname() {
		return this.nickname;
	}

	public AircubeUser setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	public String getSex() {
		return this.sex;
	}

	public AircubeUser setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public String getHeadImgUrl() {
		return this.headImgUrl;
	}

	public AircubeUser setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
		return this;
	}

	public Date getGmtRegist() {
		return this.gmtRegist;
	}

	public AircubeUser setGmtRegist(Date gmtRegist) {
		this.gmtRegist = gmtRegist;
		return this;
	}

	public String getCity() {
		return this.city;
	}

	public AircubeUser setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCountry() {
		return this.country;
	}

	public AircubeUser setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getProvince() {
		return this.province;
	}

	public AircubeUser setProvince(String province) {
		this.province = province;
		return this;
	}

	public Date getGmtSubscribe() {
		return this.gmtSubscribe;
	}

	public AircubeUser setGmtSubscribe(Date gmtSubscribe) {
		this.gmtSubscribe = gmtSubscribe;
		return this;
	}

	public Integer getIsSubscribe() {
		return this.isSubscribe;
	}

	public AircubeUser setIsSubscribe(Integer isSubscribe) {
		this.isSubscribe = isSubscribe;
		return this;
	}

	public Integer getDvcNum() {
		return this.dvcNum;
	}

	public AircubeUser setDvcNum(Integer dvcNum) {
		this.dvcNum = dvcNum;
		return this;
	}

}
