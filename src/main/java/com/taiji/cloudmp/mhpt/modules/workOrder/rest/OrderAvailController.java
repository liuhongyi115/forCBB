package com.taiji.cloudmp.mhpt.modules.workOrder.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderAvail;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.OrderAvailService;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderAvailQueryCriteria;
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
@Api(tags = "OrderAvail管理")
@RestController
@RequestMapping("api")
public class OrderAvailController {

    @Autowired
    private OrderAvailService OrderAvailService;

    @Log("查询OrderAvail")
    @ApiOperation(value = "查询OrderAvail")
    @GetMapping(value = "/OrderAvail")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERAVAIL_ALL','ORDERAVAIL_SELECT')")
    public ResponseEntity getOrderAvails(OrderAvailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(OrderAvailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增OrderAvail")
    @ApiOperation(value = "新增OrderAvail")
    @PostMapping(value = "/OrderAvail")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERAVAIL_ALL','ORDERAVAIL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody OrderAvail resources){
        return new ResponseEntity(OrderAvailService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改OrderAvail")
    @ApiOperation(value = "修改OrderAvail")
    @PutMapping(value = "/OrderAvail")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERAVAIL_ALL','ORDERAVAIL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody OrderAvail resources){
        OrderAvailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除OrderAvail")
    @ApiOperation(value = "删除OrderAvail")
    @DeleteMapping(value = "/OrderAvail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORDERAVAIL_ALL','ORDERAVAIL_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        OrderAvailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}