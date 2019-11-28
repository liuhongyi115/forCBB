package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.system.domain.Role;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * @author Zheng Jie
 * @date 2019-5-23
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface RoleSmallMapper extends EntityMapper<RoleSmallDTO, Role> {

}
