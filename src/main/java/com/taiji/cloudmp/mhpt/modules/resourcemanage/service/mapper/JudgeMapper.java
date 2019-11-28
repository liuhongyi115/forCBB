package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Judge;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeDTO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiao
* @date 2019-10-22
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JudgeMapper extends EntityMapper<JudgeDTO, Judge> {

}