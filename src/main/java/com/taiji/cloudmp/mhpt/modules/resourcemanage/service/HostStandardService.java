package com.taiji.cloudmp.mhpt.modules.resourcemanage.service;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.HostStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardQueryCriteria;
import org.springframework.data.domain.Pageable;



public interface HostStandardService {

    Object HostStandardList(HostStandardQueryCriteria hostStandardDTO, Pageable pageable);

    HostStandardDTO createHostStandard(HostStandard hostStandard);

    void updateHostStandard(HostStandard hostStandard);

    void deleteHostStandard(String[] id);

    HostStandardDTO findById(String id);


}
