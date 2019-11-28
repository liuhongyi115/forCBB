package com.taiji.cloudmp.mhpt.modules.resourceApply.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResApplyBaseInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResApplyBaseInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lhy
* @date 2019-11-13
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResApplyBaseInfoMapper extends EntityMapper<ResApplyBaseInfoDTO, ResApplyBaseInfo> {

}