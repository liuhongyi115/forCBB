package com.taiji.cloudmp.mhpt.modules.activiti.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.mhpt.modules.activiti.repository.ActDefRepository;
import com.taiji.cloudmp.mhpt.modules.activiti.service.ActDefService;
import com.taiji.cloudmp.mhpt.modules.activiti.service.dto.ActDefDTO;
import com.taiji.cloudmp.mhpt.modules.activiti.service.dto.ActDefQueryCriteria;


/**
* @author lwy
* @date 2019-10-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ActDefServiceImpl implements ActDefService {

    @Autowired
    private ActDefRepository actDefRepository;

    @Override
    public Object queryAll(ActDefQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = actDefRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<Map> list = actDefRepository.findAll(criteria);
    	List<ActDefDTO> dtoList = ActDefDTO.map2DtoList(list);
        Page<ActDefDTO> page = new PageImpl<ActDefDTO>(dtoList,pageable,rowCount);
        return page;
    }
}