package com.taiji.cloudmp.mhpt.modules.resourcemanage.repository;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.HostStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardQueryCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HostStandardRepository {
    Long HostStandardListCount(HostStandardQueryCriteria hostStandardQueryCriteria);

    List<HostStandard> HostStandardList(HostStandardQueryCriteria hostStandardQueryCriteria);

    HostStandardDTO createHostStandard(HostStandard hostStandard);

    void updateHostStandard(HostStandard hostStandard);

    void deleteHostStandard(String[] id);

    HostStandard findById(String id);
}
