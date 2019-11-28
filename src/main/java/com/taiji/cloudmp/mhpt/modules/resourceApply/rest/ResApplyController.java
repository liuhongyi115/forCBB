package com.taiji.cloudmp.mhpt.modules.resourceApply.rest;

import com.taiji.cloudmp.aop.log.Log;
import com.taiji.cloudmp.mhpt.constant.WorkOrderState;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaCriteria;
import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResApplyBaseInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.ResApplyBaseInfoService;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResApplyBaseInfoQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.security.utils.JwtTokenUtil;
import com.taiji.cloudmp.mhpt.modules.system.service.UserService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.UserDTO;

import cn.hutool.core.util.IdUtil;

import javax.servlet.http.HttpServletRequest;

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
* @date 2019-11-13
*/
@Api(tags = "ResApplyBaseInfo管理")
@RestController
@RequestMapping("mhpt/resourceApply")
public class ResApplyController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UserService userService;
    
    
    @Autowired
    private HttpServletRequest request; //自动注入request
	
    @Autowired
    private ResApplyBaseInfoService ResApplyBaseInfoService;
    
    public UserDTO getUserByJWT(){
    	 String username = jwtTokenUtil.getUsernameFromRequest(request);
         UserDTO user = userService.findByName(username);
         return user;
    } 
    

    @SuppressWarnings("unchecked")
	@Log("查询ResApplyBaseInfo")
    @ApiOperation(value = "查询ResApplyBaseInfo")
    @GetMapping(value = "/ResApplyBaseInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESAPPLYBASEINFO_ALL','RESAPPLYBASEINFO_SELECT')")
    public ResponseEntity getResApplyBaseInfos(ResApplyBaseInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(ResApplyBaseInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }
    
  

    @Log("修改ResApplyBaseInfo")
    @ApiOperation(value = "修改ResApplyBaseInfo")
    @PutMapping(value = "/ResApplyBaseInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESAPPLYBASEINFO_ALL','RESAPPLYBASEINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ResApplyBaseInfo resources){
        ResApplyBaseInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    
    ////////////////////////////////////////////////  
    /**
     * 生成并返回办件编码
     * @param criteria
     * @param pageable
     * @return
     */
    @Log("生成办件编码")
    @ApiOperation(value = "生成办件编码")
    @GetMapping(value = "/getBanjianCode")
    @PreAuthorize("hasAnyRole('ADMIN','RESAPPLYBASEINFO_ALL','RESAPPLYBASEINFO_SELECT')")
    public String  getBanjianCode(){
    	//现在暂无办件编码。先使用UUID 来代替
    	return IdUtil.fastUUID();
    }

    @SuppressWarnings("unchecked")
	@Log("新增资源基本信息")
    @ApiOperation(value = "新增资源基本信息")
    @PostMapping(value = "/createBaseInfoWithBanjianCode")
    @PreAuthorize("hasAnyRole('ADMIN','RESAPPLYBASEINFO_ALL','RESAPPLYBASEINFO_CREATE')")
    public ResponseEntity createBaseInfoWithBanjianCode(@RequestBody ResApplyBaseInfo resources){
    	UserDTO userByJWT = this.getUserByJWT();
        return new ResponseEntity(ResApplyBaseInfoService.createBaseInfoWithBanjianCode(resources,userByJWT.getId()+"",userByJWT.getUsername()),HttpStatus.CREATED);
    }
    
    /***
     * 查询待申请列表
     * @param criteria
     * @param pageable
     * @return
     */
    @SuppressWarnings("unchecked")
	@GetMapping(value = "/list")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity getdaiShenQingList(ResApplyBaseInfoQueryCriteria criteria, Pageable pageable){
    	UserDTO userByJWT = this.getUserByJWT();
    	//传入当前登录人,只能查看自己新建的记录
    	criteria.setManagerId(userByJWT.getId()+"");
    	//传入 待申请状态
    	criteria.setState(WorkOrderState.DRAFT);
    	
        return new ResponseEntity(ResApplyBaseInfoService.queryByDaiShenQing(criteria,pageable), HttpStatus.OK);
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping(value = "getBaseInfoByBanjianCode")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity getOne(@RequestBody String id){
        return new ResponseEntity(ResApplyBaseInfoService.findById(id), HttpStatus.OK);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Log("修改资源基本信息")
    @ApiOperation(value = "修改资源基本信息")
    @PostMapping(value = "/updateBaseInfo")
    @PreAuthorize("hasAnyRole('ADMIN','RESAPPLYBASEINFO_ALL','RESAPPLYBASEINFO_CREATE')")
    public ResponseEntity updateBaseInfo(@RequestBody ResApplyBaseInfo resources){
        return new ResponseEntity(ResApplyBaseInfoService.update(resources),HttpStatus.OK);
    }
    
    @Log("删除资源基本信息")
    @ApiOperation(value = "删除资源基本信息")
    @DeleteMapping(value = "/deleteByBanjianCode/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RESAPPLYBASEINFO_ALL','RESAPPLYBASEINFO_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        ResApplyBaseInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}