package com.taiji.cloudmp.mhpt.modules.resourceApply.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResHostInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.ResHostInfoService;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResHostInfoQueryCriteria;
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
* @date 2019-11-14
*/
@Api(tags = "ResHostInfo管理")
@RestController
@RequestMapping("api")
public class ResHostInfoController {

    @Autowired
    private ResHostInfoService ResHostInfoService;

    @Log("查询ResHostInfo")
    @ApiOperation(value = "查询ResHostInfo")
    @GetMapping(value = "/ResHostInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESHOSTINFO_ALL','RESHOSTINFO_SELECT')")
    public ResponseEntity getResHostInfos(ResHostInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(ResHostInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ResHostInfo")
    @ApiOperation(value = "新增ResHostInfo")
    @PostMapping(value = "/ResHostInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESHOSTINFO_ALL','RESHOSTINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ResHostInfo resources){
        return new ResponseEntity(ResHostInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ResHostInfo")
    @ApiOperation(value = "修改ResHostInfo")
    @PutMapping(value = "/ResHostInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESHOSTINFO_ALL','RESHOSTINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ResHostInfo resources){
        ResHostInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ResHostInfo")
    @ApiOperation(value = "删除ResHostInfo")
    @DeleteMapping(value = "/ResHostInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RESHOSTINFO_ALL','RESHOSTINFO_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        ResHostInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}