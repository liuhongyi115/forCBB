package com.taiji.cloudmp.mhpt.modules.resourcemanage.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.HostStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.HostStandardService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.HostStandardQueryCriteria;
import io.swagger.annotations.ApiOperation;
import javafx.application.HostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mhpt")
public class HostStandardController {

    @Autowired
    private HostStandardService hostStandardService;

    @Log("查询主机标准")
    @ApiOperation("主机标准列表")
    @GetMapping({"/hoststandard/list"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity getHostandardList(HostStandardQueryCriteria hostStandardQueryCriteria, Pageable pageable) {
        return new ResponseEntity(hostStandardService.HostStandardList(hostStandardQueryCriteria, pageable), HttpStatus.OK);
    }

    @Log("新增主机标准")
    @ApiOperation("新增主机标准")
    @PostMapping({"/hoststandard/create"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity createHostStandard(@Validated @RequestBody HostStandard hostStandard) {
        return new ResponseEntity(hostStandardService.createHostStandard(hostStandard), HttpStatus.OK);
    }

    @Log("更新主机标准")
    @ApiOperation("更新主机标准")
    @PutMapping({"/hoststandard/update"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity updateHostStandard(@Validated @RequestBody HostStandard hostStandard) {
        hostStandardService.updateHostStandard(hostStandard);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除主机标准")
    @ApiOperation("删除主机标准")
    @DeleteMapping({"/hoststandard/delete}"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity deleteHostStandard(@RequestBody String[] id) {
        hostStandardService.deleteHostStandard(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping({"/hoststandard/findById/{id}"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity getHostStandard(String id){
        return new ResponseEntity(hostStandardService.findById(id),HttpStatus.OK);
    }
}
