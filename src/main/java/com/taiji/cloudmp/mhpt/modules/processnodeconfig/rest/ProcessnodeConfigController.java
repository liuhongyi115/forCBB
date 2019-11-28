package com.taiji.cloudmp.mhpt.modules.processnodeconfig.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.domain.ProcessnodeConfig;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.ProcessnodeConfigService;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigDTO;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigQueryCriteria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author lwy
* @date 2019-10-31
*/
@Api(tags = "ProcessnodeConfig管理")
@RestController
@RequestMapping("ywpt")
public class ProcessnodeConfigController {

    @Autowired
    private ProcessnodeConfigService processnodeConfigService;

    @Log("获取ProcessnodeConfig")
    @ApiOperation(value = "获取ProcessnodeConfig")
    @GetMapping(value = "/processnodeConfig")
    @PreAuthorize("hasAnyRole('ADMIN','PROCESSNODECONFIG_ALL','PROCESSNODECONFIG_SELECT')")
    public ResponseEntity addAndgetProcessnodeConfigs(ProcessnodeConfigQueryCriteria criteria){
    	//查询processNodeConfig表中是否存在该节点的数据，若不存在，则新增
    	List<ProcessnodeConfigDTO> list = (List<ProcessnodeConfigDTO>) processnodeConfigService.queryAll(criteria);
        if(list == null || list.size() == 0) {
        	//不存在节点数据，新增，并把数据返回
        	list = processnodeConfigService.addByProcessKey(criteria.getProcessKey());
        }
        Map map = new HashMap();
    	map.put("content", list);
    	map.put("totalElements", list.size());
    	return new ResponseEntity(map,HttpStatus.OK);
    }
    
    
    @Log("新增ProcessnodeConfig")
    @ApiOperation(value = "新增ProcessnodeConfig")
    @PostMapping(value = "/processnodeConfig")
    @PreAuthorize("hasAnyRole('ADMIN','PROCESSNODECONFIG_ALL','PROCESSNODECONFIG_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProcessnodeConfig resources){
        return new ResponseEntity(processnodeConfigService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ProcessnodeConfig")
    @ApiOperation(value = "修改ProcessnodeConfig")
    @PutMapping(value = "/processnodeConfig")
    @PreAuthorize("hasAnyRole('ADMIN','PROCESSNODECONFIG_ALL','PROCESSNODECONFIG_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProcessnodeConfig resources){
        processnodeConfigService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ProcessnodeConfig")
    @ApiOperation(value = "删除ProcessnodeConfig")
    @DeleteMapping(value = "/processnodeConfig/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROCESSNODECONFIG_ALL','PROCESSNODECONFIG_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        processnodeConfigService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}