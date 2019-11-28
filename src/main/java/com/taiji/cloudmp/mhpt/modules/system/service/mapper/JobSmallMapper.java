package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.system.domain.Job;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.JobSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobSmallMapper extends EntityMapper<JobSmallDTO, Job> {

}