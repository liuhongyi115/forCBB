package com.taiji.cloudmp.mhpt.modules.demo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taiji.cloudmp.mhpt.modules.demo.domain.TestQingjia;
import com.taiji.cloudmp.mhpt.modules.demo.service.TestQingjiaService;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaCriteria;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaDTO;
import com.taiji.cloudmp.mhpt.modules.security.utils.JwtTokenUtil;
import com.taiji.cloudmp.mhpt.modules.system.service.UserService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.UserDTO;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@RestController
@RequestMapping("mhpt/demo/qingjia")
public class TestQingjiaController {

    @Autowired
    private TestQingjiaService testQingjiaService;
    
    @Autowired
    private HttpServletRequest request; //自动注入request
   
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
   
    @Autowired
    private UserService userService;
    
//    @Autowired
 // private ProcessnodeConfigService processnodeConfigService;

    @GetMapping(value = "/daiShenHeList")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity getdaiShenHeList(TestQingjiaCriteria criteria, Pageable pageable){
//    	criteria.setUserId(userId);
        // 获得user
        String username = jwtTokenUtil.getUsernameFromRequest(request);
        UserDTO user = userService.findByName(username);
        criteria.setUserId(user.getId()+"");
        return new ResponseEntity(testQingjiaService.queryAllDaiShenHe(criteria,pageable), HttpStatus.OK);
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/getOne/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity getOne(@PathVariable String id){
        return new ResponseEntity(testQingjiaService.findById(id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/zanCun")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity zanCun(@RequestBody TestQingjia resources){
    	  return new ResponseEntity(this.testQingjiaService.zanCun(resources),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/tijiao")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity tijiao(@RequestBody TestQingjia resources){
    	 String username = jwtTokenUtil.getUsernameFromRequest(request);
         UserDTO user = userService.findByName(username);
    	
    		TestQingjiaDTO submitQingjia = this.testQingjiaService.submitQingjia(resources,user.getId()+"");
    	  return new ResponseEntity(submitQingjia,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/bohui")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity bohui(@RequestBody TestQingjia resources){
    	 String username = jwtTokenUtil.getUsernameFromRequest(request);
         UserDTO user = userService.findByName(username);
    	  TestQingjiaDTO reject = this.testQingjiaService.reject(resources,user.getId()+"");
    	  return new ResponseEntity(reject,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/shenheView")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public String shenheView(@RequestParam(value = "procInstId") String procInstId){
    		String formFilePath = this.testQingjiaService.formFilePath(procInstId);
    	  return formFilePath;
    }
    
    @RequestMapping(value = "queryPicByInstanceID")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public void queryPicByInstanceID(@RequestParam(value = "procInstId") String procInstId, HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg");
        
        InputStream is = this.testQingjiaService.getLiuChengTu(procInstId);

        if (is != null){
            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            response.setContentType("image/png");  // 设置返回的文件类型
            OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
            toClient.write(data); // 输出数据
            toClient.close();
        }
    }
    
    /**
     * 查看 流程的日志
     * @param procInstId proc_inst_id
     */
    @RequestMapping(value = "getHistoryOfProcess")
    @PreAuthorize("hasAnyRole('ADMIN','QING_JIA_AUDIT')")
    public ResponseEntity getHistoryOfProcess(@RequestParam(value = "procInstId") String procInstId){
    		List<Map<String, Object>> historyOfProcess = this.testQingjiaService.getHistoryOfProcess(procInstId);
         return new ResponseEntity(historyOfProcess, HttpStatus.OK);
    }
    
    /**
     * lwy
     * @param resources
     * @return
     */
    @PostMapping(value = "/save")
    @PreAuthorize("hasAnyRole('ADMIN','QINGJIA_APPLY')")
    public ResponseEntity create(@RequestBody TestQingjia resources){
    	resources.setIsStart("0");
        return new ResponseEntity(testQingjiaService.create(resources),HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/applyList")
    @PreAuthorize("hasAnyRole('ADMIN','QINGJIA_APPLY')")
    public ResponseEntity getApplyList(TestQingjiaCriteria criteria, Pageable pageable){
    	criteria.setIsStart("0");
    	return new ResponseEntity(testQingjiaService.queryAll(criteria,pageable),HttpStatus.OK);
    }
    
    //提交按钮，启动流程
    @PostMapping(value = "/startFlow")
    @PreAuthorize("hasAnyRole('ADMIN','QINGJIA_APPLY')")
    public ResponseEntity startFlow(TestQingjia resource){
        String username = jwtTokenUtil.getUsernameFromRequest(request);
        UserDTO user = userService.findByName(username);
    	testQingjiaService.startFlow(resource,user.getId()+"");
    	return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
}
