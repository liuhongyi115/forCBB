package com.taiji.cloudmp.mhpt.modules.resourcemanage.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.System;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.SystemService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.SystemQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author xiao
* @date 2019-10-23
*/
@Api(tags = "System管理")
@RestController
@RequestMapping("mhpt")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @Log("查询System")
    @ApiOperation(value = "查询System")
    @GetMapping(value = "/systeminit/list")
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM_ALL','SYSTEM_SELECT')")
    public ResponseEntity getSystems(SystemQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(systemService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增System")
    @ApiOperation(value = "新增System")
    @PostMapping(value = "/systeminit/create")
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM_ALL','SYSTEM_CREATE')")
    public ResponseEntity create(@Validated @RequestBody System resources){
        return new ResponseEntity(systemService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改System")
    @ApiOperation(value = "修改System")
    @PutMapping(value = "/systeminit/update")
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM_ALL','SYSTEM_EDIT')")
    public ResponseEntity update(@Validated @RequestBody System resources){
        systemService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除System")
    @ApiOperation(value = "删除System")
    @DeleteMapping(value = "/systeminit/delete")
    @PreAuthorize("hasAnyRole('ADMIN','SYSTEM_ALL','SYSTEM_DELETE')")
    public ResponseEntity delete(@RequestBody String[] id){
        systemService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}