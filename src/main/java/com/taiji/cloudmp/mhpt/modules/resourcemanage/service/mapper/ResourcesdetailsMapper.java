package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Resourcesdetails;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ResourcesdetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiao
* @date 2019-11-19
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourcesdetailsMapper extends EntityMapper<ResourcesdetailsDTO, Resourcesdetails> {

}