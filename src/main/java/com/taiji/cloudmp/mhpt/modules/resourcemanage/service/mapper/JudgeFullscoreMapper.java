package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.JudgeFullscore;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeFullscoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xiao
* @date 2019-11-14
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JudgeFullscoreMapper extends EntityMapper<JudgeFullscoreDTO, JudgeFullscore> {

}