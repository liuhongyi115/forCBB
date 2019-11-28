package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.ServiceStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ServicestandardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiao
* @date 2019-10-22
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceStandardMapper extends EntityMapper<ServicestandardDTO, ServiceStandard> {

}