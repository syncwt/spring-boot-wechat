/**
 * 
 */
package com.senthink.www.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.senthink.www.domain.po.AircubeInfo;
import com.senthink.www.domain.vo.AircubeInfoVo;

/**
 * @ClassName           : AirinfoMapper
 * @Description         :
 * @Author              : xujialin
 * @CreationDate        : 2016年12月15日下午4:10:20
 */
@Mapper
public interface AirinfoMapper {
	AirinfoMapper INSTANCE = Mappers.getMapper(AirinfoMapper.class);

	AircubeInfoVo poTovoDto(AircubeInfo airinfo);

	AircubeInfo voTopo(AircubeInfoVo vo);
}
