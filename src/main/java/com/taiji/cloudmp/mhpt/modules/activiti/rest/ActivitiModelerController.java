package com.taiji.cloudmp.mhpt.modules.activiti.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.activiti.service.ActDefService;
import com.taiji.cloudmp.mhpt.modules.activiti.service.dto.ActDefQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.activiti.service.dto.ModelDTO;
import com.taiji.cloudmp.mhpt.modules.activiti.service.dto.ModelQueryCriteria;

@Controller
@RequestMapping("activiti")
public class ActivitiModelerController {
	
	@Autowired
	private RepositoryService repositoryService;
  
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ActDefService actDefService;
	
	
    @RequestMapping("/test")
    @ResponseBody
	public String newMode() {
    	return "spring-boot";
    }
	
    /**
     * 设计
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/design")
//    @PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView design(@RequestParam("id")String id) throws UnsupportedEncodingException {
    	System.out.println("");
        return new ModelAndView("redirect:/modeler.html?modelId=" + id);
    }
    
    @Log("新增流程")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity create(@Validated @RequestBody ModelDTO resources) throws UnsupportedEncodingException{
    	
    	int revision = 1;
    	String description = "新建流程模型";
    	
    	ObjectNode modelNode = objectMapper.createObjectNode();
    	modelNode.put(ModelDataJsonConstants.MODEL_NAME, resources.getName());
    	modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
    	modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
    	
        //ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
    	
    	//初始化一个空模型
    	Model model = repositoryService.newModel();
        model.setName(resources.getName());
        model.setKey(resources.getKey());
        model.setMetaInfo(modelNode.toString());
        
        repositoryService.saveModel(model);
        
        repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
    	
        return new ResponseEntity(resources,HttpStatus.CREATED);
    }
    
    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity update(@Validated @RequestBody ModelDTO resources){
      Model model = repositoryService.getModel(resources.getId());
  	 //初始化一个空模型
      if(null == model){
    	  throw new BadRequestException("模型数据不存在");
      }
      
      //只可修改 模型名称
      model.setName(resources.getName());
      
      repositoryService.saveModel(model);
  	
      return new ResponseEntity(resources,HttpStatus.OK);
  }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 发布模型为流程定义
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("{id}/deployment")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity deploy(@PathVariable("id")String id) throws Exception {

        //获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
        	 throw new BadRequestException("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
        	throw new BadRequestException("数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
        return new ResponseEntity(ModelDTO.Model2DTO(modelData),HttpStatus.OK);
    }
    
    @RequestMapping("list")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getModelList(ModelQueryCriteria criteria, Pageable pageable){
    	
    	int rowSize = pageable.getPageSize();
    	int page = pageable.getPageNumber();
    	
    	String modelName =  criteria.getName();
    	
    	Long count = null;
    	List<Model> list = null;
    	if(StringUtils.isBlank(modelName)){
    		count = repositoryService.createModelQuery().count();
    		 list = repositoryService.createModelQuery().listPage(rowSize * page, rowSize);
    	}else{
    		 count = repositoryService.createModelQuery().modelNameLike(modelName).count();
    		 list = repositoryService.createModelQuery().modelNameLike(modelName).listPage(rowSize * page, rowSize);
    	}
    	List<ModelDTO> modelList= ModelDTO.modelList2Dto(list);
    	
    	Page<ModelDTO> modelPage = new PageImpl<ModelDTO>(modelList,pageable,count);
        return new ResponseEntity(modelPage, HttpStatus.OK);
        
    }
    
    
    
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity deleteOne(@PathVariable("id")String id){
    	Model modelData = repositoryService.getModel(id);
    	String deploymentId = modelData.getDeploymentId();
    	if(deploymentId != null  && !"".equals(deploymentId)){
    		throw new BadRequestException("模型数据已发布。不可删除");
    	}
    	
        repositoryService.deleteModel(id);
        return new ResponseEntity(ModelDTO.Model2DTO(modelData), HttpStatus.OK);
    }
    
    /**
	   * 流程定义列表
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/actDefList")
	//@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity actDefList(ActDefQueryCriteria criteria, Pageable pageable){
	return new ResponseEntity(actDefService.queryAll(criteria, pageable),HttpStatus.OK);
	}
    
    public Object postOne(@RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }

    public Object putOne(@PathVariable("id") String s, @RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }

    public Object patchOne(@PathVariable("id") String s, @RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }
    
}
