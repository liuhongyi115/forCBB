package com.taiji.cloudmp.mhpt.modules.resourceApply.service.impl;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResHostInfo;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.resourceApply.repository.ResHostInfoRepository;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.ResHostInfoService;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResHostInfoDTO;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResHostInfoQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.mapper.ResHostInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cn.hutool.core.util.IdUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;


/**
* @author lhy
* @date 2019-11-14
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ResHostInfoServiceImpl implements ResHostInfoService {

    @Autowired
    private ResHostInfoRepository ResHostInfoRepository;

    @Autowired
    private ResHostInfoMapper ResHostInfoMapper;

    @Override
    public Object queryAll(ResHostInfoQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = ResHostInfoRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<ResHostInfo> list = ResHostInfoRepository.findAll(criteria);
        Page<ResHostInfo> page = new PageImpl<ResHostInfo>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(ResHostInfoQueryCriteria criteria){
    	Long rowCount = ResHostInfoRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<ResHostInfo> list = ResHostInfoRepository.findAll(criteria);
        return ResHostInfoMapper.toDto(list);
    }

    @Override
    public ResHostInfoDTO findById(String id) {
        Optional<ResHostInfo> optionalResHostInfo = Optional.ofNullable(ResHostInfoRepository.findById(id));
        ValidationUtil.isNull(optionalResHostInfo,"ResHostInfo","id",id);
        return ResHostInfoMapper.toDto(optionalResHostInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResHostInfoDTO create(ResHostInfo resources) {
    	resources.setId(IdUtil.simpleUUID());
		ResHostInfoRepository.add(resources);
        return ResHostInfoMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ResHostInfo resources) {
        Optional<ResHostInfo> optionalResHostInfo = Optional.ofNullable(ResHostInfoRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalResHostInfo,"ResHostInfo","id",resources.getId());
        ResHostInfo ResHostInfo = optionalResHostInfo.get();
        ResHostInfoRepository.update(ResHostInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        ResHostInfoRepository.delete(id);
    }
}