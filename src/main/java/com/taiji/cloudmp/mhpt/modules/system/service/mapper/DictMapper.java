package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.system.domain.Dict;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DictMapper extends EntityMapper<DictDTO, Dict> {

}