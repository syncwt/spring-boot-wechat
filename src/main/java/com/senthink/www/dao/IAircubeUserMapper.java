package com.senthink.www.dao;

import org.springframework.dao.DataAccessException;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.senthink.www.domain.po.AircubeUser;
import com.senthink.www.domain.po.PageData;

import java.util.List;

/**
 * AircubeUser 表数据库控制层接口
 */
public interface IAircubeUserMapper extends BaseMapper<AircubeUser> {

    /**
     * 通过openid获取用户信息
     *
     * @param aircubeUser
     * @return
     */
    AircubeUser selectUserInfoByOpenid(AircubeUser aircubeUser);

    /**
     * 保存用户基本信息
     *
     * @param pd
     */
    boolean saveUserInfo(PageData pd);

    /**
     * 更新用户重新关注信息
     */
    int updateUserInfo(PageData pd);

    /**
     * 用户取消关注更新关注状态
     *
     * @param pd
     */
    int updateUserSubsuribe(PageData pd);

	/**
	 * 根据openid查询userId
	 * 
	 * @param openid
	 * @return
	 * @throws DataAccessException
	 * @time 2016年12月26日上午11:37:05
	 */
	Integer getUserIdByOpenId(String openid) throws DataAccessException;

	/**
	 * 获取用户设备数量
	 * @param openid
	 * @return
     */
	int selectDvcNums(String openid);

	/**
	 * 通过用户openid获取该用户绑定的设备列表（本公众号绑定的设备）
	 * @param openid
	 * @return
     */
	List<String> findUserDvcList(String openid);
}