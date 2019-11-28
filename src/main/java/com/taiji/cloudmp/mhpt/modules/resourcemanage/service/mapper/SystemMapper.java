package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.System;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.SystemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiao
* @date 2019-10-23
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemMapper extends EntityMapper<SystemDTO, System> {

}