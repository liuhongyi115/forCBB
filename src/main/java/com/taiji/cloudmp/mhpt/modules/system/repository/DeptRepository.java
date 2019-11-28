package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.taiji.cloudmp.mhpt.modules.system.domain.Dept;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DeptQueryCriteria;
import org.springframework.stereotype.Repository;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Mapper
@Repository
public interface DeptRepository {

    
    List<Dept> findAll(DeptQueryCriteria criteria);
    
    Dept findById(Long id);

    Set<Dept> findByRoles_Id(Long id);
    
    String findNameById(Long id);
    
    void save(Dept d);
    
    void update(Dept d);
    
    void deleteById(Long id);
    
    
    void deleteDeptLinkedRoles(Long id);
    
    Long findDeptCountLinkedRoles(Long deptId);
    
    List<Dept> findByPid(Long pid);

    /**
     * 通过角色获取部门
     * @param id
     * @return
     */
    Set<Dept> findDeptByRole(Long id);
}
