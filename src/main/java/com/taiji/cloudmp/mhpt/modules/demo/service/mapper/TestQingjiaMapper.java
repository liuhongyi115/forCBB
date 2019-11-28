package com.taiji.cloudmp.mhpt.modules.demo.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.demo.domain.TestQingjia;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaDTO;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestQingjiaMapper extends EntityMapper<TestQingjiaDTO, TestQingjia> {

}