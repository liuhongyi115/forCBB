package com.taiji.cloudmp.mhpt.modules.demo.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.mhpt.modules.activiti.domain.ProcessHiExtraInfo;
import com.taiji.cloudmp.mhpt.modules.activiti.domain.WaitHandler;
import com.taiji.cloudmp.mhpt.modules.activiti.repository.WaitHandlerRepository;
import com.taiji.cloudmp.mhpt.modules.activiti.repository.processHiExtraInfoRepository;
import com.taiji.cloudmp.mhpt.modules.demo.domain.TestQingjia;
import com.taiji.cloudmp.mhpt.modules.demo.repository.TestQingjiaRepository;
import com.taiji.cloudmp.mhpt.modules.demo.service.TestQingjiaService;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaCriteria;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaDTO;
import com.taiji.cloudmp.mhpt.modules.demo.service.mapper.TestQingjiaMapper;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.ProcessnodeConfigService;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigDTO;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.domain.User;
import com.taiji.cloudmp.utils.FlowUtil;
import com.taiji.cloudmp.utils.ValidationUtil;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,  rollbackFor = Exception.class)
public class TestQingjiaServiceImpl implements TestQingjiaService {

    @Autowired
    private TestQingjiaRepository testQingjiaRepository;

    @Autowired
    private TestQingjiaMapper testQingjiaMapper;
    
    @Autowired
	private RepositoryService repositoryService;
    
    @Autowired
	private HistoryService historyService;
    
    @Autowired
	private TaskService taskService;
    
    @Autowired
	private RuntimeService runtimeService;
    
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;
    
    @Autowired
    private ProcessnodeConfigService processnodeConfigService;
    
    @Autowired
    private processHiExtraInfoRepository processHiExtraInfoRepository;
    
    @Autowired
    private WaitHandlerRepository WaitHandlerRepository;
    
    
    
//    private FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration); 


    @Override
    public Object queryAllDaiShenHe(TestQingjiaCriteria criteria, Pageable pageable){
    	
    	FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration);
    	
    	criteria.Pageable2BaseModel(pageable);
    	
    	Long rowCount = testQingjiaRepository.findAllDaiShenHeByCount(criteria);
    	
    	criteria.getPager().setRowCount(rowCount);
    	List<TestQingjia> findAll = testQingjiaRepository.findAllDaiShenHe(criteria);
    	
    	Page<TestQingjia> page = new PageImpl<TestQingjia>(findAll,pageable,rowCount);
    	
    	Page<TestQingjiaDTO> pageDTO = page.map(testQingjiaMapper::toDto);
    	
        return pageDTO;
    }

	@Override
	public TestQingjiaDTO findById(String id) {
		FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration);
		
		TestQingjia findById = testQingjiaRepository.findQingJiaById(id);
		Optional<TestQingjia> qingjia = Optional.of(findById);
		ValidationUtil.isNull(qingjia, "TestQingjia", "id", id);
		return testQingjiaMapper.toDto(qingjia.get());
	}

	@Override
	public TestQingjiaDTO zanCun(TestQingjia qingjia) {
		FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration);
		
		String id = qingjia.getId();
		String suggestion = qingjia.getSuggestion();
		testQingjiaRepository.updateSuggestion(qingjia); 
		
		return testQingjiaMapper.toDto(qingjia);
	}

	@Override
	public TestQingjiaDTO submitQingjia(TestQingjia qingjia,String loginUserId) {
		FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration);
		
		//判断当前节点是否是最后一个节点。 是的话  结束 否则 指派下一节点的 审核人
		//complete task
		//得到流程实例的id
		String procInstId = qingjia.getProcInstId();
		
		//根据流程实例判断本节点是否是最后一个节点, 
		List<PvmTransition> nextNodeList = flowUtil.getNextNodeList(procInstId);
		String types = nextNodeList.get(0).getDestination().getProperty("type").toString();
		//结束本节点
		Task curretTask = flowUtil.getTaskByProcInstId(procInstId);
		//当前登录用户  点击 审核提交需要 认领 当前任务
		flowUtil.zhiPai(curretTask.getId(), loginUserId);
		
		//删掉待办表先先放在这里，后面得迁到公共类里
		this.WaitHandlerRepository.deleteWaitHandlerByTaskId(curretTask.getId());
		//写入流程日志表 审核意见
		

		
        ProcessHiExtraInfo processHiExtraInfo = new ProcessHiExtraInfo();
        
        processHiExtraInfo.setId(UUID.fastUUID().toString());
        processHiExtraInfo.setProcInstId(procInstId);
        processHiExtraInfo.setHiTaskId(curretTask.getId());
        processHiExtraInfo.setSuggestion(qingjia.getSuggestion());
        
        processHiExtraInfoRepository.save(processHiExtraInfo);
        
        
		if("endEvent".equals(types)){
			flowUtil.completeTask(curretTask.getId());
			
		}else{
			//结束  并且指派 下个节点办理人
			//mark
			flowUtil.completeTask(curretTask.getId());
			
			Task nextTask = flowUtil.getTaskByProcInstId(procInstId);
			
			String procdefId = nextTask.getProcessDefinitionId();
			
			
			
			String excId = nextTask.getExecutionId();
	        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
	            
	        String nodeId = execution.getActivityId();
			
			ProcessnodeConfigQueryCriteria criteria = new ProcessnodeConfigQueryCriteria();
			criteria.setProcdefId(procdefId);
			criteria.setNodeId(nodeId);
			
			ArrayList<ProcessnodeConfigDTO> queryAll = (ArrayList<ProcessnodeConfigDTO>) processnodeConfigService.queryAll(criteria);
			ProcessnodeConfigDTO processnodeConfigDTO = queryAll.get(0);
			List<User> userList = this.getUserByConfig(processnodeConfigDTO);
			userList.forEach(user ->{
				//写入 wait_handler 处理人信息.有可能下一节点有很多人需要
				WaitHandler wh  = new WaitHandler();
				wh.setId(UUID.fastUUID().toString());
				wh.setTaskId(nextTask.getId());
				wh.setProcInstId(procInstId);
				wh.setUserId(user.getId() + "");
				this.WaitHandlerRepository.save(wh);
			});
		}  
		  
		  
//		String nextNodeId = nextNodeList.get(0).getDestination().getId();
		return testQingjiaMapper.toDto(qingjia);
	}
	
	
	
	public TestQingjiaDTO reject(TestQingjia qingjia,String loginUserId) {
		
		String procInstId = qingjia.getProcInstId();
		FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration);
		//结束本节点
		Task curretTask = flowUtil.getTaskByProcInstId(procInstId);
		String taskId = curretTask.getId();
		//审核人 进行驳回  先得 认领 当前task
		flowUtil.zhiPai(taskId, loginUserId);

		Map variables = new HashMap<>();
		
		//获取当前任务
		HistoricTaskInstance currTaskHi = historyService.createHistoricTaskInstanceQuery()
						.taskId(taskId)
						.singleResult();
		//获取流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
						.processInstanceId(currTaskHi.getProcessInstanceId())
						.singleResult();
		//获取流程定义
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(currTaskHi.getProcessDefinitionId());
		if (processDefinitionEntity == null) {
			System.out.println("不存在的流程定义。");
			
		}
		
		//获取当前activity
		ActivityImpl currActivity = ((ProcessDefinitionImpl) processDefinitionEntity)
                .findActivity(currTaskHi.getTaskDefinitionKey());
		
		//获取当前任务流入
		List<PvmTransition> histTransitionList = currActivity
                .getIncomingTransitions();
	
        
        //清除当前活动出口
        List<PvmTransition> originPvmTransitionList = new ArrayList<PvmTransition>();
        List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
        	originPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();
        
		//查找上一个user task节点
		List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery().activityType("userTask")
                .processInstanceId(processInstance.getId())
                .finished()
                .orderByHistoricActivityInstanceStartTime()
                .desc().list();
		//上个节点
		HistoricActivityInstance lastHistoricActivityInstance = historicActivityInstances.get(0);
		//上个节点处理人
		String lastAssignee = lastHistoricActivityInstance.getAssignee();
		
		
		//上个节点的
		TransitionImpl transitionImpl = null;
        if (historicActivityInstances.size() > 0) {
        	ActivityImpl lastActivity = ((ProcessDefinitionImpl) processDefinitionEntity)
                    .findActivity(lastHistoricActivityInstance.getActivityId());
        	//创建当前任务的新出口
        	//使用findOutgoingTransition
        	TransitionImpl findOutgoingTransition = currActivity.findOutgoingTransition(lastActivity.getId());
        	if(null != findOutgoingTransition){
        		transitionImpl = findOutgoingTransition;
        		currActivity.getOutgoingTransitions().add(transitionImpl);
        	}else{
        		transitionImpl = currActivity.createOutgoingTransition(lastActivity.getId());
        		transitionImpl.setDestination(lastActivity);
        	}
        }else
        {
        	System.out.println("上级节点不存在。");	
        }
        
        variables = processInstance.getProcessVariables();
        // 完成任务
//        List<Task> tasks = taskService.createTaskQuery()
//                .processInstanceId(processInstance.getId())
//                .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
//        for (Task task : tasks) {
        
          //删掉待办表先先放在这里，后面得迁到公共类里
    		this.WaitHandlerRepository.deleteWaitHandlerByTaskId(curretTask.getId());
    		//写入流程日志表 审核意见
    		
    		
    		//保存 审核意见日志 （审核才记）
            
    	    ProcessHiExtraInfo processHiExtraInfo = new ProcessHiExtraInfo();
            
            processHiExtraInfo.setId(UUID.fastUUID().toString());
            processHiExtraInfo.setProcInstId(procInstId);
            processHiExtraInfo.setHiTaskId(curretTask.getId());
            processHiExtraInfo.setSuggestion(qingjia.getSuggestion());
    		
            processHiExtraInfoRepository.save(processHiExtraInfo);
            //保存审核意见日志 end
            
            taskService.complete(curretTask.getId(), variables);
            
            //complete 以后 得到下一个流程(即我们的驳回之前的流程)
    		Task afterBohuiTask = flowUtil.getTaskByProcInstId(procInstId);
    		
    		//这里不写指派了。驳回时只存到  waithandle里。到经办的待处理中再进行认领。我们现在都是  task认领模式
    		//flowUtil.zhiPai(afterBohuiTask.getId(), lastAssignee);
    		
    		//写入 wait_handler 处理人信息
			WaitHandler wh  = new WaitHandler();
			wh.setId(UUID.fastUUID().toString());
			wh.setTaskId(afterBohuiTask.getId());
			wh.setProcInstId(procInstId);
			wh.setUserId(lastAssignee);
			this.WaitHandlerRepository.save(wh);
    		
//        }
        
        // 恢复方向
        currActivity.getOutgoingTransitions().remove(transitionImpl);
//        上面的如果有bug 就尝试下面的
        currActivity.getOutgoingTransitions().clear();
        
        
        /////////////////////////////////////// 
//        destActiviti.getIncomingTransitions().remove(newTransitionImpl);
        //清除原活动节点的临时流程项
//        currActiviti.getOutgoingTransitions().clear();
        //////////////////////////////////////////
        
        currActivity.getOutgoingTransitions().addAll(originPvmTransitionList);
        
        
        return this.testQingjiaMapper.toDto(qingjia);
	}
	
	/**
	 * 根据 config 获得 节点处理人(根据业务而定)
	 *参数： 节点配置
	 * @return
	 */
	private List<User> getUserByConfig(ProcessnodeConfigDTO processnodeConfigDTO){
		//根据 流程定义下个节点的配置表  获取角色
		String deptId = processnodeConfigDTO.getDeptId();
		String roleId = processnodeConfigDTO.getRoleId();
		String handlerId = processnodeConfigDTO.getHandlerId();
		return this.processnodeConfigService.getProcessExecutor(deptId, roleId, handlerId);
	}
	/**
	 * 返回需要动态跳转的页面
	 * @param 
	 * @return
	 */
	public String formFilePath(String procInstId){
		
		FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration);
		
		Task task = flowUtil.getTaskByProcInstId(procInstId);
		
		String procdefId = task.getProcessDefinitionId();
		
		
		
		String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
            
        String nodeId = execution.getActivityId();
		
		ProcessnodeConfigQueryCriteria criteria = new ProcessnodeConfigQueryCriteria();
		criteria.setProcdefId(procdefId);
		criteria.setNodeId(nodeId);
		
		ArrayList<ProcessnodeConfigDTO> queryAll = (ArrayList<ProcessnodeConfigDTO>) processnodeConfigService.queryAll(criteria);
		ProcessnodeConfigDTO processnodeConfigDTO = queryAll.get(0);
		
		
		return processnodeConfigDTO.getFormFilePath();
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public TestQingjiaDTO create(TestQingjia resources) {
    	resources.setId(IdUtil.simpleUUID());
    	testQingjiaRepository.add(resources);
        return testQingjiaMapper.toDto(resources);
    }

	@Override
	public Object queryAll(TestQingjiaCriteria criteria, Pageable pageable) {
		criteria.Pageable2BaseModel(pageable);
    	Long rowCount = testQingjiaRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
        List<TestQingjia> list = testQingjiaRepository.findAll(criteria);
        List<TestQingjiaDTO> dtoList = testQingjiaMapper.toDto(list);
        Page<TestQingjiaDTO> page = new PageImpl<TestQingjiaDTO>(dtoList,pageable,rowCount);
    	return page;
	}

	@Override
	public InputStream getLiuChengTu(String processInstanceId) {
		FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration);
		
		return flowUtil.getTaskTraceDiagram(processInstanceId);
	}

	@Override
	public List<Map<String, Object>> getHistoryOfProcess(String procInstId) {
		List<Map<String, Object>> historyOfProcess = this.processHiExtraInfoRepository.getHistoryOfProcess(procInstId);
		return historyOfProcess;
	}
	
	@Override
	public void startFlow(TestQingjia resource,String currentUserId) {
		FlowUtil flowUtil = new FlowUtil(repositoryService, historyService, taskService, runtimeService,processEngineConfiguration); 
		//根据流程key启动流程
		String flowKey = "qingjia";
		ProcessInstance processInstance = flowUtil.startProcessByFlowKey(flowKey);
		//得到流程实例的id
		String procInstId = processInstance.getId();
		//获取当前节点task
		Task curretTask = flowUtil.getTaskByProcInstId(procInstId);
		//获取taskid
		String taskId = curretTask.getId();
		//获取当前actId

        //判断当前节点的下一个节点是否是结束节点，若是结束节点，无需操作，否则 指派下一节点的 审核人
  		List<PvmTransition> nextNodeList = flowUtil.getNextNodeList(procInstId);
  		String types = nextNodeList.get(0).getDestination().getProperty("type").toString();
		if("endEvent".equals(types)){
			//仅完成当前任务
	  		flowUtil.completeTask(curretTask.getId());
		}else{	//完成当前任务并且指派 下个节点办理人
			//完成当前任务
			flowUtil.zhiPai(curretTask.getId(), currentUserId);
	  		flowUtil.completeTask(curretTask.getId());
	  		//获取下一个节点的任务
			Task nextTask = flowUtil.getTaskByProcInstId(procInstId);
			String procdefId = nextTask.getProcessDefinitionId();
			String excId = nextTask.getExecutionId();
	        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
	        String nodeId = execution.getActivityId();
	        //从节点配置表中获取得到节点的配置信息
			ProcessnodeConfigQueryCriteria criteria = new ProcessnodeConfigQueryCriteria();
			criteria.setProcdefId(procdefId);
			criteria.setNodeId(nodeId);
			ArrayList<ProcessnodeConfigDTO> queryAll = (ArrayList<ProcessnodeConfigDTO>) processnodeConfigService.queryAll(criteria);
			ProcessnodeConfigDTO processnodeConfigDTO = queryAll.get(0);
			List<User> userList = this.getUserByConfig(processnodeConfigDTO);
			userList.forEach(user ->{
				//写入 wait_handler 处理人信息
				WaitHandler wh  = new WaitHandler();
				wh.setId(UUID.fastUUID().toString());
				wh.setTaskId(nextTask.getId());
				wh.setProcInstId(procInstId);
				wh.setUserId(user.getId() + "");
				this.WaitHandlerRepository.save(wh);
			});
		}
		//写入流程日志表 审核意见
        ProcessHiExtraInfo processHiExtraInfo = new ProcessHiExtraInfo();
        processHiExtraInfo.setId(UUID.fastUUID().toString());
        processHiExtraInfo.setProcInstId(procInstId);
        processHiExtraInfo.setHiTaskId(curretTask.getId());
        processHiExtraInfo.setSuggestion("提交");
        processHiExtraInfoRepository.save(processHiExtraInfo);
        //更新请假业务表
        resource.setIsStart("1");	//流程已启动
        resource.setProcInstId(procInstId);
        testQingjiaRepository.update(resource);
	}
}
