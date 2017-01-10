package com.senthink.www.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.senthink.www.domain.po.AircubeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.www.dao
 * @Class Name    : ${CLASS_NAME}
 * @Description :
 * @Author : lingcy@lierda.com
 * @Creation Date : 2016年12月22日 上午10:14
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public interface IAircubeInfoMapper extends BaseMapper<AircubeInfo> {
	String getOpenIdBydvcId(String dvcId) throws DataAccessException;

	Integer getAirIdByDeviceId(@Param("dvcId") String dvcId) throws DataAccessException;

	List<AircubeInfo> getAirByUserId(Integer userId) throws DataAccessException;

}
