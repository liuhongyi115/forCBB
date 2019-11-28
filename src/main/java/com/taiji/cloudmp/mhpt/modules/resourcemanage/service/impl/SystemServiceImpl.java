package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.System;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.repository.SystemRepository;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.SystemService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.SystemDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.SystemQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper.SystemMapper;

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
* @author xiao
* @date 2019-10-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public Object queryAll(SystemQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = systemRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<System> list = systemRepository.findAll(criteria);
        Page<System> page = new PageImpl<System>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(SystemQueryCriteria criteria){
    	Long rowCount = systemRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<System> list = systemRepository.findAll(criteria);
        return systemMapper.toDto(list);
    }

    @Override
    public SystemDTO findById(String id) {
        Optional<System> optionalSystem = Optional.ofNullable(systemRepository.findById(id));
        ValidationUtil.isNull(optionalSystem,"System","id",id);
        return systemMapper.toDto(optionalSystem.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemDTO create(System resources) {
    	resources.setId(IdUtil.simpleUUID());
		systemRepository.add(resources);
        return systemMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(System resources) {
        Optional<System> optionalSystem = Optional.ofNullable(systemRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalSystem,"System","id",resources.getId());
        System system = optionalSystem.get();
        systemRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] id) {
        systemRepository.delete(id);
    }
}