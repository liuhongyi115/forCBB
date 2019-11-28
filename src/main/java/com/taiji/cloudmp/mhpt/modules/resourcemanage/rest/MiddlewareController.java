package com.taiji.cloudmp.mhpt.modules.resourcemanage.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Middleware;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.MiddlewareService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.MiddlewareQueryCriteria;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mhpt")
public class MiddlewareController {

    @Autowired
    private MiddlewareService middlewareService;

    @Log("查询中间件")
    @ApiOperation("查询中间件")
    @GetMapping({"/middleware/list"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity getMiddlewareList(MiddlewareQueryCriteria middlewareQueryCriteria, Pageable pageable) {
        return new ResponseEntity(middlewareService.MiddlewareList(middlewareQueryCriteria, pageable), HttpStatus.OK);
    }

    @Log("新增中间件")
    @ApiOperation("新增中间件")
    @PostMapping({"/middleware/create"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity createMiddleware(@Validated @RequestBody Middleware middleware) {
        return new ResponseEntity(middlewareService.createMiddleware(middleware), HttpStatus.OK);
    }

    @Log("更新中间件")
    @ApiOperation("更新中间件")
    @PutMapping({"/middleware/update"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity updateMiddleware(@Validated @RequestBody Middleware middleware) {
        middlewareService.updateMiddleware(middleware);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除中间件")
    @ApiOperation("删除中间件")
    @DeleteMapping({"/middleware/delete"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity deleteMiddleware(@RequestBody String[] id) {
        middlewareService.deleteMiddleware(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping({"/middleware/findById/{id}"})
    @PreAuthorize("hasAnyRole('ADMIN','TEST_ALL','TEST_SELECT')")
    public ResponseEntity getMiddleware(String id){
        return new ResponseEntity(middlewareService.findById(id),HttpStatus.OK);
    }
}
