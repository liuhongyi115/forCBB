package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.taiji.cloudmp.mhpt.modules.system.domain.User;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.UserDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.UserQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserRepository {
    List<User> findByWhere(UserQueryCriteria user);

    Long findByWhereCount(UserQueryCriteria user);

    User findByUsername(@Param("name") String name);

    User findUserById(@Param("id") Long id);

    User findUserByEmail(@Param("email") String email);

    void addUser (User user);

    void updateUser(User user);

    void addRole(Map roleId);

    void deleteRole(Long userid);

    void deleteUserById(Long id);

    void updatePass(@Param("username") String username,@Param("pass") String pass, @Param("date") Date date);

    void updateAvatar(@Param("username") String username,@Param("avatar") String avatar);

    void updateEmail(@Param("username") String username,@Param("email") String email);

    List<Map> getRoleById(@Param("id") Long id);

    List<Map> getAllRoleByUserid(@Param("id") Long id);

    //获取工作信息
    Map getJobById(@Param("id") Long id);
   //获取单位信息
    String getDeptById(@Param("id") Long id);

    Long findDeptCountByUser(Long deptId);
    
    Long findUserCountByJobId(Long jobId);


}
