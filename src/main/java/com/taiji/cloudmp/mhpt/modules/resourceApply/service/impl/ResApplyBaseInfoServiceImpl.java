package com.taiji.cloudmp.mhpt.modules.resourceApply.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.constant.WorkOrderState;
import com.taiji.cloudmp.mhpt.constant.WorkOrderType;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaDTO;
import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResApplyBaseInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.repository.ResApplyBaseInfoRepository;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.ResApplyBaseInfoService;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResApplyBaseInfoDTO;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResApplyBaseInfoQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.mapper.ResApplyBaseInfoMapper;
import com.taiji.cloudmp.mhpt.modules.workOrder.repository.OrderTransRepository;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderProcService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderTransService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderTransDTO;
import com.taiji.cloudmp.utils.ValidationUtil;

import cn.hutool.core.util.IdUtil;

/**
 * @author lhy
 * @date 2019-11-13
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class ResApplyBaseInfoServiceImpl implements ResApplyBaseInfoService {

	@Autowired
	private ResApplyBaseInfoRepository ResApplyBaseInfoRepository;

	@Autowired
	private OrderTransService orderTransService;

	@Autowired
	private OrderProcService orderProcService;

	@Autowired
	private ResApplyBaseInfoMapper ResApplyBaseInfoMapper;

	@Override
	public Object queryAll(ResApplyBaseInfoQueryCriteria criteria, Pageable pageable) {
		criteria.Pageable2BaseModel(pageable);
		Long rowCount = ResApplyBaseInfoRepository.findTotalCount(criteria);
		criteria.getPager().setRowCount(rowCount);
		List<ResApplyBaseInfo> list = ResApplyBaseInfoRepository.findAll(criteria);
		Page<ResApplyBaseInfo> page = new PageImpl<ResApplyBaseInfo>(list, pageable, rowCount);
		return page;
	}

	@Override
	public Object queryAll(ResApplyBaseInfoQueryCriteria criteria) {
		Long rowCount = ResApplyBaseInfoRepository.findTotalCount(criteria);
		criteria.getPager().setRowCount(rowCount);
		List<ResApplyBaseInfo> list = ResApplyBaseInfoRepository.findAll(criteria);
		return ResApplyBaseInfoMapper.toDto(list);
	}

	/////////

	/**
	 * 只是新增的保存。 需要判断修改
	 * 
	 * @param resources
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResApplyBaseInfoDTO createBaseInfoWithBanjianCode(ResApplyBaseInfo resources, String userId,
			String username) {
		// 加入基本信息
		String baseInfoId = IdUtil.fastUUID();
		resources.setId(baseInfoId);
		ResApplyBaseInfoRepository.add(resources);
		// 写入一条在途交易
		orderTransService.writeToTrans(baseInfoId, userId, username, WorkOrderState.DRAFT, "",
				WorkOrderType.APPLY_ORDER);
		// 写入proc日志
		orderProcService.writeToProc(baseInfoId, userId, username, WorkOrderState.DRAFT, "", WorkOrderType.APPLY_ORDER,
				"");

		return ResApplyBaseInfoMapper.toDto(resources);
	}

	@Override
	public Object queryByDaiShenQing(ResApplyBaseInfoQueryCriteria criteria, Pageable pageable) {
		criteria.Pageable2BaseModel(pageable);
		Long rowCount = ResApplyBaseInfoRepository.findWithTransForCount(criteria);
		criteria.getPager().setRowCount(rowCount);
		List<Map<String, Object>> findWithTrans = ResApplyBaseInfoRepository.findWithTrans(criteria);
		Page<Map<String, Object>> page = new PageImpl<Map<String, Object>>(findWithTrans, pageable, rowCount);

		return page;
	}

	@Override
	public ResApplyBaseInfoDTO findById(String id) {
		Optional<ResApplyBaseInfo> optionalResApplyBaseInfo = Optional
				.ofNullable(ResApplyBaseInfoRepository.findById(id));
		ValidationUtil.isNull(optionalResApplyBaseInfo, "ResApplyBaseInfo", "id", id);
		return ResApplyBaseInfoMapper.toDto(optionalResApplyBaseInfo.get());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResApplyBaseInfoDTO update(ResApplyBaseInfo resources) {
		Optional<ResApplyBaseInfo> optionalResApplyBaseInfo = Optional
				.ofNullable(ResApplyBaseInfoRepository.findById(resources.getId()));
		;
		ValidationUtil.isNull(optionalResApplyBaseInfo, "ResApplyBaseInfo", "id", resources.getId());
		ResApplyBaseInfo ResApplyBaseInfo = optionalResApplyBaseInfo.get();
		ResApplyBaseInfoRepository.update(ResApplyBaseInfo);
		return ResApplyBaseInfoMapper.toDto(ResApplyBaseInfo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) {
		// 检查状态是否为草稿状态。否则不可删除
		ResApplyBaseInfo findById = ResApplyBaseInfoRepository.findById(id);
		if (findById != null) {

			OrderTransDTO trans = orderTransService.findByBaseInfoId(id);
			// 如果不是草稿状态,不允许删除
			if (WorkOrderState.DRAFT != trans.getState()) {
				throw new BadRequestException("该申请单已经在流转,非待申请状态。不可以删除");
			}

			// 删除级联trans
			orderTransService.deleteByBaseInfoId(id);
			// 删除级联 proc
			orderProcService.deleteByBaseInfoId(id);
			// 删除 级联删除 主机以及服务项
			
			
			//删除基本信息
			ResApplyBaseInfoRepository.delete(id);


		} else {
			throw new BadRequestException("该数据已被删除,id" + id);
		}

	}

}