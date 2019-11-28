package com.taiji.cloudmp.mhpt.modules.system.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.system.domain.Dept;
import com.taiji.cloudmp.mhpt.modules.system.domain.Menu;
import com.taiji.cloudmp.mhpt.modules.system.repository.DeptRepository;
import com.taiji.cloudmp.mhpt.modules.system.repository.JobRepository;
import com.taiji.cloudmp.mhpt.modules.system.repository.UserRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.DeptService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DeptDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DeptQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.DeptMapper;
import com.taiji.cloudmp.utils.ValidationUtil;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private DeptMapper deptMapper;

	@Override
	public List<DeptDTO> queryAll(DeptQueryCriteria criteria) {
		List<Dept> findAll = deptRepository.findAll(criteria);
		return deptMapper.toDto(findAll);
	}

	@Override
	public DeptDTO findById(Long id) {
		Dept findById = deptRepository.findById(id);
		Optional<Dept> dept = Optional.of(findById);
		ValidationUtil.isNull(dept, "Dept", "id", id);
		return deptMapper.toDto(dept.get());
	}

	@Override
	public List<Dept> findByPid(long pid) {
		return deptRepository.findByPid(pid);
	}

	@Override
	public Set<Dept> findByRoleIds(Long id) {
		return deptRepository.findByRoles_Id(id);
	}

	@Override
	public Object buildTree(List<DeptDTO> deptDTOS) {
		Set<DeptDTO> trees = new LinkedHashSet<>();
		Set<DeptDTO> depts = new LinkedHashSet<>();
		List<String> deptNames = deptDTOS.stream().map(DeptDTO::getName).collect(Collectors.toList());
		Boolean isChild;
		for (DeptDTO deptDTO : deptDTOS) {
			isChild = false;
			if ("0".equals(deptDTO.getPid().toString())) {
				trees.add(deptDTO);
			}
			for (DeptDTO it : deptDTOS) {
				if (it.getPid().equals(deptDTO.getId())) {
					isChild = true;
					if (deptDTO.getChildren() == null) {
						deptDTO.setChildren(new ArrayList<DeptDTO>());
					}
					deptDTO.getChildren().add(it);
				}
			}
			if (isChild)
				depts.add(deptDTO);
			else if (!deptNames.contains(deptRepository.findNameById(deptDTO.getPid())))
				depts.add(deptDTO);
		}

		if (CollectionUtils.isEmpty(trees)) {
			trees = depts;
		}

		Integer totalElements = deptDTOS != null ? deptDTOS.size() : 0;

		Map map = new HashMap();
		map.put("totalElements", totalElements);
		map.put("content", CollectionUtils.isEmpty(trees) ? deptDTOS : trees);
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeptDTO create(Dept resources) {
		resources.setCreateTime(new Timestamp(new Date().getTime()));
		deptRepository.save(resources);
		return deptMapper.toDto(resources);
	}
	
	
    /**
     * 传自身的id  将 自己的id和 子子孙孙id查出来
     * @param id
     * @return
     */
    private Set<Long> getDeptByConnectByPrior(Long id){
    	Set<Long> ids = new HashSet<Long>();
    	//将自身传进去 查出来自己的子
    	List<Dept> findByPid = deptRepository.findByPid(id);
    	
    	for(Dept d:findByPid){
    		//先加自己
    		ids.add(d.getId());
    		//递归找自己的子孙 加进去
    		Set<Long> deptids = getDeptByConnectByPrior(d.getId());
    		ids.addAll(deptids);
    	}
    	return ids;
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(Dept resources) {
		if (resources.getId().equals(resources.getPid())) {
			throw new BadRequestException("上级不能为自己");
		}
		
		Dept findById = deptRepository.findById(resources.getId());
		
		Optional<Dept> optionalDept = Optional.of(findById);
		ValidationUtil.isNull(optionalDept, "Dept", "id", resources.getId());
		
        Set<Long> deptByConnectByPrior = this.getDeptByConnectByPrior(resources.getId());
        deptByConnectByPrior.add(resources.getId());
		
        if(deptByConnectByPrior.contains(resources.getPid())){
        	throw new BadRequestException("不可以选择自己的子孙部门做上级部门");
        }
		
		Dept dept = optionalDept.get();
		resources.setId(dept.getId());
		deptRepository.update(resources);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
		Long deptCountInUser = userRepository.findDeptCountByUser(id);
		Long deptCountInJob = jobRepository.findDeptCountInJob(id);
		if(deptCountInUser >0 || deptCountInJob >0){
			throw new BadRequestException("当前删除机构,本级或者下级机构存在所属用户或者岗位,请解绑后再删除");
		}
		//先从角色中解绑 机构信息
//		deptRepository.deleteDeptLinkedRoles(id);
		Long findDeptCountLinkedRoles = deptRepository.findDeptCountLinkedRoles(id);
		if(findDeptCountLinkedRoles >0){
			throw new BadRequestException("当前删除机构或下级机构在角色中有绑定,无法删除");
		}
		
		Set<Long> zisunDepts = this.getDeptByConnectByPrior(id);
		
		//递归删除所属子机构
		for(Long zisunid :zisunDepts){
			delete(zisunid);
		}
		
		deptRepository.deleteById(id);
	}
}