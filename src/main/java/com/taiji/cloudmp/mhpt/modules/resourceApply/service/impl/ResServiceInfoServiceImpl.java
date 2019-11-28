package com.taiji.cloudmp.mhpt.modules.resourceApply.service.impl;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResServiceInfo;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.resourceApply.repository.ResServiceInfoRepository;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.ResServiceInfoService;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResServiceInfoDTO;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResServiceInfoQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.mapper.ResServiceInfoMapper;

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
* @date 2019-11-15
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ResServiceInfoServiceImpl implements ResServiceInfoService {

    @Autowired
    private ResServiceInfoRepository ResServiceInfoRepository;

    @Autowired
    private ResServiceInfoMapper ResServiceInfoMapper;

    @Override
    public Object queryAll(ResServiceInfoQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = ResServiceInfoRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<ResServiceInfo> list = ResServiceInfoRepository.findAll(criteria);
        Page<ResServiceInfo> page = new PageImpl<ResServiceInfo>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(ResServiceInfoQueryCriteria criteria){
    	Long rowCount = ResServiceInfoRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<ResServiceInfo> list = ResServiceInfoRepository.findAll(criteria);
        return ResServiceInfoMapper.toDto(list);
    }

    @Override
    public ResServiceInfoDTO findById(String id) {
        Optional<ResServiceInfo> optionalResServiceInfo = Optional.ofNullable(ResServiceInfoRepository.findById(id));
        ValidationUtil.isNull(optionalResServiceInfo,"ResServiceInfo","id",id);
        return ResServiceInfoMapper.toDto(optionalResServiceInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResServiceInfoDTO create(ResServiceInfo resources) {
    	resources.setId(IdUtil.simpleUUID());
		ResServiceInfoRepository.add(resources);
        return ResServiceInfoMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ResServiceInfo resources) {
        Optional<ResServiceInfo> optionalResServiceInfo = Optional.ofNullable(ResServiceInfoRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalResServiceInfo,"ResServiceInfo","id",resources.getId());
        ResServiceInfo ResServiceInfo = optionalResServiceInfo.get();
        ResServiceInfoRepository.update(ResServiceInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        ResServiceInfoRepository.delete(id);
    }
}