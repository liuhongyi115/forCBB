package com.taiji.cloudmp.mhpt.modules.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.system.domain.Dept;
import com.taiji.cloudmp.mhpt.modules.system.domain.Job;
import com.taiji.cloudmp.mhpt.modules.system.repository.DeptRepository;
import com.taiji.cloudmp.mhpt.modules.system.repository.JobRepository;
import com.taiji.cloudmp.mhpt.modules.system.repository.UserRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.JobService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.JobDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.JobQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.JobMapper;
import com.taiji.cloudmp.utils.ValidationUtil;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Object queryAll(JobQueryCriteria criteria, Pageable pageable) {
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = jobRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
        List<Job> list = jobRepository.findAll(criteria);
        List<JobDTO> jobs = new ArrayList<>();
        for (Job job : list) {
        	Optional<Dept> optionalDept = Optional.ofNullable(deptRepository.findById(job.getDeptId()));
        	ValidationUtil.isNull(optionalDept, "Dept", "id", job.getDeptId());
        	Dept dept = optionalDept.get();
        	job.setDept(dept);
            jobs.add(jobMapper.toDto(job,deptRepository.findNameById(dept.getPid())));
        }
        Page<JobDTO> page = new PageImpl<JobDTO>(jobs,pageable,rowCount);
        
//        Map map = new HashMap();
//    	map.put("content", jobs);
//    	map.put("totalElements", jobs.size());
    	return page;
    }

    @Override
    public JobDTO findById(Long id) {
    	Optional<Job> optionalJob = Optional.ofNullable(jobRepository.findById(id));
        ValidationUtil.isNull(optionalJob,"Job","id",id);
    	Job job = optionalJob.get();
    	Optional<Dept> optionalDept = Optional.ofNullable(deptRepository.findById(job.getDeptId()));
    	ValidationUtil.isNull(optionalDept, "Dept", "id", job.getDeptId());
		Dept dept = optionalDept.get();
		job.setDept(dept);
        return jobMapper.toDto(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobDTO create(Job resources) {
    	resources.setDeptId(resources.getDept().getId());
    	resources.setCreateTime(new Date());
    	jobRepository.add(resources);
        return jobMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Job resources) {
    	Optional<Job> optionalJob = Optional.ofNullable(jobRepository.findById(resources.getId()));
        ValidationUtil.isNull( optionalJob,"Job","id",resources.getId());
    	resources.setDeptId(resources.getDept().getId());
    	jobRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
    	//查询岗位关联的用户
    	Long count = userRepository.findUserCountByJobId(id);
    	if(count > 0) {
    		throw new BadRequestException("该岗位存在关联用户，请先取消关联！");
    	}
        jobRepository.deleteById(id);
    }
}