package com.taiji.cloudmp.mhpt.modules.resourceApply.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResServiceInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.ResServiceInfoService;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResServiceInfoQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author lhy
* @date 2019-11-15
*/
@Api(tags = "ResServiceInfo管理")
@RestController
@RequestMapping("api")
public class ResServiceInfoController {

    @Autowired
    private ResServiceInfoService ResServiceInfoService;

    @Log("查询ResServiceInfo")
    @ApiOperation(value = "查询ResServiceInfo")
    @GetMapping(value = "/ResServiceInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESSERVICEINFO_ALL','RESSERVICEINFO_SELECT')")
    public ResponseEntity getResServiceInfos(ResServiceInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(ResServiceInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ResServiceInfo")
    @ApiOperation(value = "新增ResServiceInfo")
    @PostMapping(value = "/ResServiceInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESSERVICEINFO_ALL','RESSERVICEINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ResServiceInfo resources){
        return new ResponseEntity(ResServiceInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ResServiceInfo")
    @ApiOperation(value = "修改ResServiceInfo")
    @PutMapping(value = "/ResServiceInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESSERVICEINFO_ALL','RESSERVICEINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ResServiceInfo resources){
        ResServiceInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ResServiceInfo")
    @ApiOperation(value = "删除ResServiceInfo")
    @DeleteMapping(value = "/ResServiceInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RESSERVICEINFO_ALL','RESSERVICEINFO_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        ResServiceInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}