package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mhpt.modules.system.domain.Role;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleDTO;
import com.taiji.cloudmp.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class, MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
