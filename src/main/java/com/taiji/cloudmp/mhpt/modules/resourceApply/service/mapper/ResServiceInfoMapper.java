package com.taiji.cloudmp.mhpt.modules.resourceApply.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResServiceInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResServiceInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lhy
* @date 2019-11-15
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResServiceInfoMapper extends EntityMapper<ResServiceInfoDTO, ResServiceInfo> {

}