//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper;

import com.taiji.cloudmp.mapper.EntityMapper;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.HostStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface HostStandardMapper extends EntityMapper<HostStandardDTO, HostStandard> {
}
