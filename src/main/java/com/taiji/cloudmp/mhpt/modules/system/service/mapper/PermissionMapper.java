package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mhpt.modules.system.domain.Permission;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionDTO;
import com.taiji.cloudmp.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
