package com.taiji.cloudmp.mhpt.modules.resourcemanage.rest;

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
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Judge;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.JudgeService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeQueryCriteria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author xiao
* @date 2019-10-22
*/
@Api(tags = "Judge管理")
@RestController
@RequestMapping("mhpt")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @Log("查询Judge")
    @ApiOperation(value = "查询Judge")
    @GetMapping(value = "/comment/list")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGE_ALL','JUDGE_SELECT')")
    public ResponseEntity getJudges(JudgeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(judgeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增Judge")
    @ApiOperation(value = "新增Judge")
    @PostMapping(value = "/comment/create")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGE_ALL','JUDGE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Judge resources) throws Exception{
        return new ResponseEntity(judgeService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Judge")
    @ApiOperation(value = "修改Judge")
    @PutMapping(value = "/comment/update")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGE_ALL','JUDGE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Judge resources){
        judgeService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @Log("判断Judge")
    @ApiOperation(value = "判断Judge")
    @PutMapping(value = "/comment/isExist")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGE_ALL','JUDGE_EDIT')")
    public String isExist(@Validated @RequestBody Judge resources){
    	String msg = null;
       if( judgeService.isExist(resources)){
    	   msg = "same";}else
    	   msg = "different";
       return msg;
    }

    @Log("删除Judge")
    @ApiOperation(value = "删除Judge")
    @DeleteMapping(value = "/comment/delete")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGE_ALL','JUDGE_DELETE')")
    public ResponseEntity delete(@RequestBody String[] id){
//    	String[] arrOfId=id.split(",");
        judgeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}