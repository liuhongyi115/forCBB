package com.taiji.cloudmp.mhpt.modules.quartz.rest;

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
import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.quartz.domain.QuartzJob;
import com.taiji.cloudmp.mhpt.modules.quartz.service.QuartzJobService;
import com.taiji.cloudmp.mhpt.modules.quartz.service.dto.QuartzJobQueryCriteria;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";

    @Autowired
    private QuartzJobService quartzJobService;

    @Log("查询定时任务")
    @GetMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity getJobs(QuartzJobQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(quartzJobService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/jobLogs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity getJobLogs(QuartzJobQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(quartzJobService.queryAllLog(criteria,pageable), HttpStatus.OK);
    }

    @Log("新增定时任务")
    @PostMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_CREATE')")
    public ResponseEntity create(@Validated @RequestBody QuartzJob resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(quartzJobService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改定时任务")
    @PutMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity update(@Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources){
        quartzJobService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("更改定时任务状态")
    @PutMapping(value = "/jobs/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity updateIsPause(@PathVariable Long id){
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("执行定时任务")
    @PutMapping(value = "/jobs/exec/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity execution(@PathVariable Long id){
        quartzJobService.execution(quartzJobService.findById(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除定时任务")
    @DeleteMapping(value = "/jobs/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        quartzJobService.delete(quartzJobService.findById(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
