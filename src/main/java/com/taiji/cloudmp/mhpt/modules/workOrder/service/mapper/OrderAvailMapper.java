package com.taiji.cloudmp.mhpt.modules.workOrder.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderAvail;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderAvailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author lhy
* @date 2019-11-26
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderAvailMapper extends EntityMapper<OrderAvailDTO, OrderAvail> {

}