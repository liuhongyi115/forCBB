package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.system.domain.Dept;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DeptSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptSmallMapper extends EntityMapper<DeptSmallDTO, Dept> {

}