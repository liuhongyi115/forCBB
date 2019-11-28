package com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.activiti.repository.ActDefRepository;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.domain.ProcessnodeConfig;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.repository.ProcessnodeConfigRepository;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.ProcessnodeConfigService;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigDTO;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.mapper.ProcessnodeConfigMapper;
import com.taiji.cloudmp.mhpt.modules.system.domain.User;
import com.taiji.cloudmp.utils.ValidationUtil;

import cn.hutool.core.util.IdUtil;


/**
* @author lwy
* @date 2019-10-31
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProcessnodeConfigServiceImpl implements ProcessnodeConfigService {

    @Autowired
    private ProcessnodeConfigRepository processnodeConfigRepository;

    @Autowired
    private ProcessnodeConfigMapper processnodeConfigMapper;

    @Autowired
    private ActDefRepository actDefRepository;
    
    private String PROCESS_SEQUENCE_CLASS = "class org.activiti.bpmn.model.SequenceFlow";
    private String PROCESS_START_CLASS = "class org.activiti.bpmn.model.StartEvent";
    private String PROCESS_END_CLASS = "class org.activiti.bpmn.model.EndEvent";
    
    @Override
    public Object queryAll(ProcessnodeConfigQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = processnodeConfigRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<ProcessnodeConfig> list = processnodeConfigRepository.findAll(criteria);
        Page<ProcessnodeConfig> page = new PageImpl<ProcessnodeConfig>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(ProcessnodeConfigQueryCriteria criteria){
    	Long rowCount = processnodeConfigRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<ProcessnodeConfig> list = processnodeConfigRepository.findAll(criteria);
        return processnodeConfigMapper.toDto(list);
    }

    @Override
    public ProcessnodeConfigDTO findById(String id) {
        Optional<ProcessnodeConfig> optionalProcessnodeConfig = Optional.ofNullable(processnodeConfigRepository.findById(id));
        ValidationUtil.isNull(optionalProcessnodeConfig,"ProcessnodeConfig","id",id);
        return processnodeConfigMapper.toDto(optionalProcessnodeConfig.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessnodeConfigDTO create(ProcessnodeConfig resources) {
    	resources.setId(IdUtil.simpleUUID());
		processnodeConfigRepository.add(resources);
        return processnodeConfigMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcessnodeConfig resources) {
        Optional<ProcessnodeConfig> optionalProcessnodeConfig = Optional.ofNullable(processnodeConfigRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalProcessnodeConfig,"ProcessnodeConfig","id",resources.getId());
        processnodeConfigRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        processnodeConfigRepository.delete(id);
    }

	@Override
	public List<ProcessnodeConfigDTO> addByProcessKey(String processKey) {
		//1.通过KEY查出来流程最新的数据
        List<Map> actDefList = actDefRepository.findLatestByKey(processKey);
        Map actDefMap;
        if(actDefList != null && actDefList.size()>0){
            actDefMap = actDefList.get(0);
        }else {
        	throw new BadRequestException("流程定义不存在，请检查流程配置！");
        }
        List<ProcessnodeConfigDTO> list = new ArrayList<ProcessnodeConfigDTO>();
        ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
        //获取流程图模型(解析xml)
        BpmnModel model = pe.getRepositoryService().getBpmnModel(actDefMap.get("id_").toString());
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {
                //class类型不等于flow,start,end的，认为是节点
                if( !PROCESS_SEQUENCE_CLASS.equals(e.getClass().toString()) 
                		&& !PROCESS_START_CLASS.equals(e.getClass().toString()) 
                		&& !PROCESS_END_CLASS.equals(e.getClass().toString()))
                {
                	ProcessnodeConfigDTO configDTO = new ProcessnodeConfigDTO();
                    configDTO.setId(IdUtil.simpleUUID());
                    configDTO.setProcessKey(processKey);
                    configDTO.setProcdefId(actDefMap.get("id_").toString());
                    configDTO.setNodeName(e.getName());
                    configDTO.setNodeId(e.getId());
                    configDTO.setProcdefName(actDefMap.get("name_").toString());
                    list.add(configDTO);
                }
            }
            processnodeConfigRepository.addByList(processnodeConfigMapper.toEntity(list));
            return list;
        }else {
        	throw new BadRequestException("流程数据模型(xml文件)不存在，请检查流程配置！");
        }
	}

	@Override
	public List<User> getProcessExecutor(String deptId, String roleId, String userIds) {
		return processnodeConfigRepository.findUserByConditions(deptId, roleId, userIds);
	}
}