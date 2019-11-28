package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.HostStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.repository.HostStandardRepository;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.HostStandardService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper.HostStandardMapper;
import com.taiji.cloudmp.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HostStandardServiceImpl implements HostStandardService {

    @Autowired
    private HostStandardRepository hostStandardRepository;
    @Autowired
    private HostStandardMapper hostStandardMapper;

    @Override
    public Object HostStandardList(HostStandardQueryCriteria hostStandard, Pageable pageable) {
        hostStandard.Pageable2BaseModel(pageable);
        Long rowcount = hostStandardRepository.HostStandardListCount(hostStandard);
        hostStandard.getPager().setRowCount(rowcount);
        List<HostStandard> hostStandardList = hostStandardRepository.HostStandardList(hostStandard);
        Page<HostStandard> page = new PageImpl<HostStandard>(hostStandardList,pageable,rowcount);
        return page;
    }

    @Override
    public HostStandardDTO createHostStandard(HostStandard hostStandard){
        String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        hostStandard.setId(id);
        hostStandardRepository.createHostStandard(hostStandard);
        return hostStandardMapper.toDto(hostStandard);
    }

    @Override
    public void updateHostStandard(HostStandard hostStandard){
        hostStandardRepository.updateHostStandard(hostStandard);
    }

    @Override
    public void deleteHostStandard(String[] id){
        hostStandardRepository.deleteHostStandard(id);
    }

    @Override
    public HostStandardDTO findById(String id){
        HostStandard hostStandard = hostStandardRepository.findById(id);
        return hostStandardMapper.toDto(hostStandard);
    }
}
