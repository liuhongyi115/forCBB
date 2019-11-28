package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Resourcesdetails;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.repository.ResourcesdetailsRepository;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.ResourcesdetailsService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ResourcesdetailsDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ResourcesdetailsQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper.ResourcesdetailsMapper;

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
* @date 2019-11-19
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ResourcesdetailsServiceImpl implements ResourcesdetailsService {

    @Autowired
    private ResourcesdetailsRepository resourcesdetailsRepository;

    @Autowired
    private ResourcesdetailsMapper resourcesdetailsMapper;

    @Override
    public Object queryAll(ResourcesdetailsQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = resourcesdetailsRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<Resourcesdetails> list = resourcesdetailsRepository.findAll(criteria);
        Page<Resourcesdetails> page = new PageImpl<Resourcesdetails>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(ResourcesdetailsQueryCriteria criteria){
    	Long rowCount = resourcesdetailsRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<Resourcesdetails> list = resourcesdetailsRepository.findAll(criteria);
        return resourcesdetailsMapper.toDto(list);
    }

    @Override
    public ResourcesdetailsDTO findById(String id) {
        Optional<Resourcesdetails> optionalResourcesdetails = Optional.ofNullable(resourcesdetailsRepository.findById(id));
        ValidationUtil.isNull(optionalResourcesdetails,"Resourcesdetails","id",id);
        return resourcesdetailsMapper.toDto(optionalResourcesdetails.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourcesdetailsDTO create(Resourcesdetails resources) {
    	resources.setId(IdUtil.simpleUUID());
		resourcesdetailsRepository.add(resources);
        return resourcesdetailsMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Resourcesdetails resources) {
        Optional<Resourcesdetails> optionalResourcesdetails = Optional.ofNullable(resourcesdetailsRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalResourcesdetails,"Resourcesdetails","id",resources.getId());
        Resourcesdetails resourcesdetails = optionalResourcesdetails.get();
        resourcesdetailsRepository.update(resourcesdetails);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] id) {
        resourcesdetailsRepository.delete(id);
    }
}