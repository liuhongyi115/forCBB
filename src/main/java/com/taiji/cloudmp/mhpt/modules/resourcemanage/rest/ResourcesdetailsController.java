package com.taiji.cloudmp.mhpt.modules.resourcemanage.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Resourcesdetails;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.ResourcesdetailsService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ResourcesdetailsQueryCriteria;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
* @date 2019-11-19
*/
@Api(tags = "Resourcesdetails管理")
@RestController
@RequestMapping("mhpt")
public class ResourcesdetailsController {

    @Autowired
    private ResourcesdetailsService resourcesdetailsService;

    @Log("查询Resourcesdetails")
    @ApiOperation(value = "查询Resourcesdetails")
    @GetMapping(value = "/resourcesdetails/list")
    @PreAuthorize("hasAnyRole('ADMIN','RESOURCESDETAILS_ALL','RESOURCESDETAILS_SELECT')")
    public ResponseEntity getResourcesdetailss(ResourcesdetailsQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(resourcesdetailsService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增Resourcesdetails")
    @ApiOperation(value = "新增Resourcesdetails")
    @PostMapping(value = "/resourcesdetails/create")
    @PreAuthorize("hasAnyRole('ADMIN','RESOURCESDETAILS_ALL','RESOURCESDETAILS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Resourcesdetails resources){
    	String date=formatTime();
    	resources.setResourceCtime(date);
    	resources.setResourceUtime(date);
        return new ResponseEntity(resourcesdetailsService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Resourcesdetails")
    @ApiOperation(value = "修改Resourcesdetails")
    @PutMapping(value = "/resourcesdetails/update")
    @PreAuthorize("hasAnyRole('ADMIN','RESOURCESDETAILS_ALL','RESOURCESDETAILS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Resourcesdetails resources){
    	resources.setResourceUtime(formatTime());
        resourcesdetailsService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Resourcesdetails")
    @ApiOperation(value = "删除Resourcesdetails")
    @DeleteMapping(value = "/resourcesdetails/delete")
    @PreAuthorize("hasAnyRole('ADMIN','RESOURCESDETAILS_ALL','RESOURCESDETAILS_DELETE')")
    public ResponseEntity delete(@PathVariable String[] id){
        resourcesdetailsService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    public String formatTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String date=dateFormat.format(new Date());
		return date;
	}
}