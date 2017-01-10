/**
 *
 */
package com.senthink.www.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @ClassName : AirAdressMapper
 * @Description :
 * @Author : xujialin
 * @CreationDate : 2016年12月19日下午3:11:33
 */
@Mapper
public interface AirAdressMapper {
    AirAdressMapper INSTANCE = Mappers.getMapper(AirAdressMapper.class);

    //sample as list
    //AircubeAdressVo poTovoDto(AircubeAdress airadress);

}
