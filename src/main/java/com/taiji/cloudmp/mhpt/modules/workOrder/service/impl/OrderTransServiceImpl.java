package com.taiji.cloudmp.mhpt.modules.workOrder.service.impl;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderTrans;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.workOrder.repository.OrderTransRepository;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderTransService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderTransDTO;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderTransQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.mapper.OrderTransMapper;

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
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class OrderTransServiceImpl implements OrderTransService {

	@Autowired
	private OrderTransRepository OrderTransRepository;

	@Autowired
	private OrderTransMapper OrderTransMapper;

	@Override
	public Object queryAll(OrderTransQueryCriteria criteria, Pageable pageable) {
		criteria.Pageable2BaseModel(pageable);
		Long rowCount = OrderTransRepository.findTotalCount(criteria);
		criteria.getPager().setRowCount(rowCount);
		List<OrderTrans> list = OrderTransRepository.findAll(criteria);
		Page<OrderTrans> page = new PageImpl<OrderTrans>(list, pageable, rowCount);
		return page;
	}

	@Override
	public Object queryAll(OrderTransQueryCriteria criteria) {
		Long rowCount = OrderTransRepository.findTotalCount(criteria);
		criteria.getPager().setRowCount(rowCount);
		List<OrderTrans> list = OrderTransRepository.findAll(criteria);
		return OrderTransMapper.toDto(list);
	}

	@Override
	public OrderTransDTO findById(String id) {
		Optional<OrderTrans> optionalOrderTrans = Optional.ofNullable(OrderTransRepository.findById(id));
		ValidationUtil.isNull(optionalOrderTrans, "OrderTrans", "id", id);
		return OrderTransMapper.toDto(optionalOrderTrans.get());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderTransDTO create(OrderTrans resources) {
		resources.setId(IdUtil.simpleUUID());
		OrderTransRepository.add(resources);
		return OrderTransMapper.toDto(resources);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(OrderTrans resources) {
		Optional<OrderTrans> optionalOrderTrans = Optional.ofNullable(OrderTransRepository.findById(resources.getId()));
		;
		ValidationUtil.isNull(optionalOrderTrans, "OrderTrans", "id", resources.getId());
		OrderTrans OrderTrans = optionalOrderTrans.get();
		OrderTransRepository.update(OrderTrans);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) {
		OrderTransRepository.delete(id);
	}

	/////////

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderTransDTO writeToTrans(String baseInfoId, String userId, String username, String state, String stateDesc,
			String WorkOrderType) {
		// 写入一条在途交易
		OrderTrans orderTrans = new OrderTrans();
		orderTrans.setId(IdUtil.fastUUID());
		orderTrans.setBaseInfoId(baseInfoId);
		// 存草稿状态
		orderTrans.setState(state);
		orderTrans.setStateDesc(stateDesc);
		orderTrans.setWorkOrderType(WorkOrderType);
		orderTrans.setManagerId(userId);
		orderTrans.setManagerName(username);
		OrderTransRepository.add(orderTrans);

		return OrderTransMapper.toDto(orderTrans);
	}

	@Override
	public void deleteByBaseInfoId(String baseInfoId) {
		OrderTransRepository.deleteByBaseInfoId(baseInfoId);
	}

	@Override
	public OrderTransDTO findByBaseInfoId(String baseInfoId) {
		Optional<OrderTrans> optionalOrderTrans = Optional.ofNullable(OrderTransRepository.findByBaseInfoId(baseInfoId));
		ValidationUtil.isNull(optionalOrderTrans, "OrderTrans", "id", baseInfoId);
		return OrderTransMapper.toDto(optionalOrderTrans.get());
	}

}