package com.taiji.cloudmp.mhpt.modules.workOrder.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderProc;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderProcService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderProcQueryCriteria;
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
* @date 2019-11-26
*/
@Api(tags = "OrderProc管理")
@RestController
@RequestMapping("api")
public class OrderProcController {

    @Autowired
    private OrderProcService OrderProcService;

    @Log("查询OrderProc")
    @ApiOperation(value = "查询OrderProc")
    @GetMapping(value = "/OrderProc")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERPROC_ALL','ORDERPROC_SELECT')")
    public ResponseEntity getOrderProcs(OrderProcQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(OrderProcService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增OrderProc")
    @ApiOperation(value = "新增OrderProc")
    @PostMapping(value = "/OrderProc")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERPROC_ALL','ORDERPROC_CREATE')")
    public ResponseEntity create(@Validated @RequestBody OrderProc resources){
        return new ResponseEntity(OrderProcService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改OrderProc")
    @ApiOperation(value = "修改OrderProc")
    @PutMapping(value = "/OrderProc")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERPROC_ALL','ORDERPROC_EDIT')")
    public ResponseEntity update(@Validated @RequestBody OrderProc resources){
        OrderProcService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除OrderProc")
    @ApiOperation(value = "删除OrderProc")
    @DeleteMapping(value = "/OrderProc/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERPROC_ALL','ORDERPROC_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        OrderProcService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}