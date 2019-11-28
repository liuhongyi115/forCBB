package com.taiji.cloudmp.mhpt.modules.resourceApply.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResHostInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResHostInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lhy
* @date 2019-11-14
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResHostInfoMapper extends EntityMapper<ResHostInfoDTO, ResHostInfo> {

}