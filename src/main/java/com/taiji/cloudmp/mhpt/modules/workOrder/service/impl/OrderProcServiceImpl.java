package com.taiji.cloudmp.mhpt.modules.workOrder.service.impl;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderProc;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.workOrder.repository.OrderProcRepository;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderProcService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderProcDTO;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderProcQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.mapper.OrderProcMapper;

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
public class OrderProcServiceImpl implements OrderProcService {

	@Autowired
	private OrderProcRepository OrderProcRepository;

	@Autowired
	private OrderProcMapper OrderProcMapper;

	@Override
	public Object queryAll(OrderProcQueryCriteria criteria, Pageable pageable) {
		criteria.Pageable2BaseModel(pageable);
		Long rowCount = OrderProcRepository.findTotalCount(criteria);
		criteria.getPager().setRowCount(rowCount);
		List<OrderProc> list = OrderProcRepository.findAll(criteria);
		Page<OrderProc> page = new PageImpl<OrderProc>(list, pageable, rowCount);
		return page;
	}

	@Override
	public Object queryAll(OrderProcQueryCriteria criteria) {
		Long rowCount = OrderProcRepository.findTotalCount(criteria);
		criteria.getPager().setRowCount(rowCount);
		List<OrderProc> list = OrderProcRepository.findAll(criteria);
		return OrderProcMapper.toDto(list);
	}

	@Override
	public OrderProcDTO findById(String id) {
		Optional<OrderProc> optionalOrderProc = Optional.ofNullable(OrderProcRepository.findById(id));
		ValidationUtil.isNull(optionalOrderProc, "OrderProc", "id", id);
		return OrderProcMapper.toDto(optionalOrderProc.get());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderProcDTO create(OrderProc resources) {
		resources.setId(IdUtil.simpleUUID());
		OrderProcRepository.add(resources);
		return OrderProcMapper.toDto(resources);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(OrderProc resources) {
		Optional<OrderProc> optionalOrderProc = Optional.ofNullable(OrderProcRepository.findById(resources.getId()));
		;
		ValidationUtil.isNull(optionalOrderProc, "OrderProc", "id", resources.getId());
		OrderProc OrderProc = optionalOrderProc.get();
		OrderProcRepository.update(OrderProc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) {
		OrderProcRepository.delete(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OrderProcDTO writeToProc(String baseInfoId, String userId, String username, String state, String stateDesc,
			String WorkOrderType, String suggestion) {
		
		OrderProc proc = new OrderProc();
		proc.setId(IdUtil.fastUUID());
		proc.setBaseInfoId(baseInfoId);
		// 记下当前操作人
		proc.setManagerId(userId);
		proc.setManagerName(username);
		proc.setState(state);
		proc.setStateDesc(stateDesc);
		proc.setWorkOrderType(WorkOrderType);
		proc.setSuggestion(suggestion);
		OrderProcRepository.add(proc);
		
		return  OrderProcMapper.toDto(proc);
	}

	@Override
	public void deleteByBaseInfoId(String baseInfoId) {
		OrderProcRepository.deleteByBaseInfoId(baseInfoId);
	}
}