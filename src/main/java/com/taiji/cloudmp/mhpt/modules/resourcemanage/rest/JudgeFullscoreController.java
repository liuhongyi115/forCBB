package com.taiji.cloudmp.mhpt.modules.resourcemanage.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.JudgeFullscore;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.JudgeFullscoreService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeFullscoreQueryCriteria;
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
* @date 2019-11-14
*/
@Api(tags = "JudgeFullscore管理")
@RestController
@RequestMapping("mhpt")
public class JudgeFullscoreController {

    @Autowired
    private JudgeFullscoreService judgeFullscoreService;

    @Log("查询JudgeFullscore")
    @ApiOperation(value = "查询JudgeFullscore")
    @GetMapping(value = "/judgeFullscore/list")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGEFULLSCORE_ALL','JUDGEFULLSCORE_SELECT')")
    public ResponseEntity getJudgeFullscores(JudgeFullscoreQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(judgeFullscoreService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增JudgeFullscore")
    @ApiOperation(value = "新增JudgeFullscore")
    @PostMapping(value = "/judgeFullscore/create")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGEFULLSCORE_ALL','JUDGEFULLSCORE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody JudgeFullscore resources){
        return new ResponseEntity(judgeFullscoreService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改JudgeFullscore")
    @ApiOperation(value = "修改JudgeFullscore")
    @PutMapping(value = "/judgeFullscore/update")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGEFULLSCORE_ALL','JUDGEFULLSCORE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody JudgeFullscore resources){
    	resources.setFullScore(resources.getFullScore()/5);
        judgeFullscoreService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除JudgeFullscore")
    @ApiOperation(value = "删除JudgeFullscore")
    @DeleteMapping(value = "/judgeFullscore/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JUDGEFULLSCORE_ALL','JUDGEFULLSCORE_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        judgeFullscoreService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}