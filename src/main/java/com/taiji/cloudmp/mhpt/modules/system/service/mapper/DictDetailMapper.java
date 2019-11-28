package com.taiji.cloudmp.mhpt.modules.system.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.system.domain.DictDetail;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DictDetailMapper extends EntityMapper<DictDetailDTO, DictDetail> {

}