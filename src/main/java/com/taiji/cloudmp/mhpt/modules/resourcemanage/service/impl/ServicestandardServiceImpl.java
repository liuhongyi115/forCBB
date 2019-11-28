package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.ServiceStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.repository.ServiceStandardRepository;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.ServicestandardService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ServiceStandardQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ServicestandardDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper.ServiceStandardMapper;
import com.taiji.cloudmp.utils.ValidationUtil;

import cn.hutool.core.util.IdUtil;


/**
* @author xiao
* @date 2019-10-22
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServicestandardServiceImpl implements ServicestandardService {

    @Autowired
    private ServiceStandardRepository servicestandardRepository;

    @Autowired
    private ServiceStandardMapper servicestandardMapper;

    @Override
    public Object queryAll(ServiceStandardQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = servicestandardRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<ServiceStandard> list = servicestandardRepository.findAll(criteria);
        Page<ServiceStandard> page = new PageImpl<ServiceStandard>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(ServiceStandardQueryCriteria criteria){
    	List<ServiceStandard> list = servicestandardRepository.findAll(criteria);
        return servicestandardMapper.toDto(list);
    }

    @Override
    public ServicestandardDTO findById(String id) {
        Optional<ServiceStandard> optionalServicestandard = Optional.ofNullable(servicestandardRepository.findById(id));
        ValidationUtil.isNull(optionalServicestandard,"Servicestandard","id",id);
        return servicestandardMapper.toDto(optionalServicestandard.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServicestandardDTO create(ServiceStandard resources) {
    	resources.setId(IdUtil.simpleUUID());
		servicestandardRepository.add(resources);
        return servicestandardMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ServiceStandard resources) {
        Optional<ServiceStandard> optionalServicestandard = Optional.ofNullable(servicestandardRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalServicestandard,"Servicestandard","id",resources.getId());
//        ServiceStandard servicestandard = optionalServicestandard.get();
        servicestandardRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] id) {
        servicestandardRepository.delete(id);
    }

	@Override
	public Object queryContent(ServiceStandardQueryCriteria criteria) {
		// TODO Auto-generated method stub
	    	List<ServiceStandard> list = servicestandardRepository.findContent(criteria);
	        return servicestandardMapper.toDto(list);
	}
}