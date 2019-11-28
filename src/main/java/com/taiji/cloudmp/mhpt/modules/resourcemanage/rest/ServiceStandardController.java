package com.taiji.cloudmp.mhpt.modules.resourcemanage.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.ServiceStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.ServicestandardService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ServiceStandardQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl.ServicestandardServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author xiao
* @date 2019-10-22
*/
@Api(tags = "Servicestandard管理")
@RestController
@RequestMapping("mhpt")
public class ServiceStandardController {

    @Autowired
    private ServicestandardService servicestandardService;

    @Log("查询Servicestandard")
    @ApiOperation(value = "查询Servicestandard")
    @GetMapping(value = "/serviceStandard/list")
    @PreAuthorize("hasAnyRole('ADMIN','SERVICESTANDARD_ALL','SERVICESTANDARD_SELECT')")
    public ResponseEntity getServicestandards(ServiceStandardQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(servicestandardService.queryAll(criteria,pageable),HttpStatus.OK);
    }
    
    @Log("查询Servicestandard服务目录")
    @ApiOperation(value = "查询Servicestandard服务目录")
    @GetMapping(value = "/serviceStandard/contentList")
    @PreAuthorize("hasAnyRole('ADMIN','SERVICESTANDARD_ALL','SERVICESTANDARD_SELECT')")
    public ResponseEntity getServicestandards(ServiceStandardQueryCriteria criteria){
        return new ResponseEntity(servicestandardService.queryContent(criteria),HttpStatus.OK);
    }

    @Log("新增Servicestandard")
    @ApiOperation(value = "新增Servicestandard")
    @PostMapping(value = "/servicestandard/add")
    @PreAuthorize("hasAnyRole('ADMIN','SERVICESTANDARD_ALL','SERVICESTANDARD_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ServiceStandard resources){
    	String date = formatTime();
    	resources.setServiceCtime(date);
    	resources.setServiceUtime(date);
        return new ResponseEntity(servicestandardService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Servicestandard")
    @ApiOperation(value = "修改Servicestandard")
    @PutMapping(value = "/servicestandard/update")
    @PreAuthorize("hasAnyRole('ADMIN','SERVICESTANDARD_ALL','SERVICESTANDARD_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ServiceStandard resources){
    	String date = formatTime();
    	resources.setServiceUtime(date);
        servicestandardService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

	public String formatTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String date=dateFormat.format(new Date());
		return date;
	}

    @Log("删除Servicestandard")
    @ApiOperation(value = "删除Servicestandard")
    @DeleteMapping(value = "/servicestandard/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SERVICESTANDARD_ALL','SERVICESTANDARD_DELETE')")
    public ResponseEntity delete(@RequestBody String[] id){
        servicestandardService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    public static void main(String[] args) {
		ServiceStandardQueryCriteria s=new ServiceStandardQueryCriteria();
		
		s.setCloudVendor("tjy");
		ServicestandardServiceImpl ss=new ServicestandardServiceImpl();
		ss.queryContent(s);
	}
    
}