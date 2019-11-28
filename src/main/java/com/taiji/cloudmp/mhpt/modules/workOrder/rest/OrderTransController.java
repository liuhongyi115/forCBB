package com.taiji.cloudmp.mhpt.modules.workOrder.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderTrans;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderTransService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderTransQueryCriteria;
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
@Api(tags = "OrderTrans管理")
@RestController
@RequestMapping("api")
public class OrderTransController {

    @Autowired
    private OrderTransService OrderTransService;

    @Log("查询OrderTrans")
    @ApiOperation(value = "查询OrderTrans")
    @GetMapping(value = "/OrderTrans")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERTRANS_ALL','ORDERTRANS_SELECT')")
    public ResponseEntity getOrderTranss(OrderTransQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(OrderTransService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增OrderTrans")
    @ApiOperation(value = "新增OrderTrans")
    @PostMapping(value = "/OrderTrans")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERTRANS_ALL','ORDERTRANS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody OrderTrans resources){
        return new ResponseEntity(OrderTransService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改OrderTrans")
    @ApiOperation(value = "修改OrderTrans")
    @PutMapping(value = "/OrderTrans")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERTRANS_ALL','ORDERTRANS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody OrderTrans resources){
        OrderTransService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除OrderTrans")
    @ApiOperation(value = "删除OrderTrans")
    @DeleteMapping(value = "/OrderTrans/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERTRANS_ALL','ORDERTRANS_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        OrderTransService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}