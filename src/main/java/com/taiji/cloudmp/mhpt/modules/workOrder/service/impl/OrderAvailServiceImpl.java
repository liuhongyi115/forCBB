package com.taiji.cloudmp.mhpt.modules.workOrder.service.impl;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderAvail;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.workOrder.repository.OrderAvailRepository;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderAvailService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderAvailDTO;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderAvailQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.mapper.OrderAvailMapper;

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
* @date 2019-11-26
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderAvailServiceImpl implements OrderAvailService {

    @Autowired
    private OrderAvailRepository OrderAvailRepository;

    @Autowired
    private OrderAvailMapper OrderAvailMapper;

    @Override
    public Object queryAll(OrderAvailQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = OrderAvailRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<OrderAvail> list = OrderAvailRepository.findAll(criteria);
        Page<OrderAvail> page = new PageImpl<OrderAvail>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(OrderAvailQueryCriteria criteria){
    	Long rowCount = OrderAvailRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<OrderAvail> list = OrderAvailRepository.findAll(criteria);
        return OrderAvailMapper.toDto(list);
    }

    @Override
    public OrderAvailDTO findById(String id) {
        Optional<OrderAvail> optionalOrderAvail = Optional.ofNullable(OrderAvailRepository.findById(id));
        ValidationUtil.isNull(optionalOrderAvail,"OrderAvail","id",id);
        return OrderAvailMapper.toDto(optionalOrderAvail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderAvailDTO create(OrderAvail resources) {
    	resources.setId(IdUtil.simpleUUID());
		OrderAvailRepository.add(resources);
        return OrderAvailMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderAvail resources) {
        Optional<OrderAvail> optionalOrderAvail = Optional.ofNullable(OrderAvailRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalOrderAvail,"OrderAvail","id",resources.getId());
        OrderAvail OrderAvail = optionalOrderAvail.get();
        OrderAvailRepository.update(OrderAvail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        OrderAvailRepository.delete(id);
    }
}