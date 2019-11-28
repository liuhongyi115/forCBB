package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.system.domain.Job;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",uses = {DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends EntityMapper<JobDTO, Job> {

    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    JobDTO toDto(Job job, String deptSuperiorName);
}