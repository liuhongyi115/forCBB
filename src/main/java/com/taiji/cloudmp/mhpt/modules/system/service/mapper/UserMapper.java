package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mhpt.modules.system.domain.User;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.UserDTO;
import com.taiji.cloudmp.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",uses = {RoleMapper.class, DeptMapper.class, JobMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, User> {

}
