package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mhpt.modules.system.domain.Menu;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuDTO;
import com.taiji.cloudmp.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
