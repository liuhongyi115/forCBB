package com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.domain.ProcessnodeConfig;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigDTO;

/**
* @author lwy
* @date 2019-10-31
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcessnodeConfigMapper extends EntityMapper<ProcessnodeConfigDTO, ProcessnodeConfig> {

}