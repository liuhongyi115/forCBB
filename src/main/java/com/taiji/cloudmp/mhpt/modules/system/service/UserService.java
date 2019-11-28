package com.taiji.cloudmp.mhpt.modules.system.service;

import com.taiji.cloudmp.mhpt.modules.system.domain.Role;
import com.taiji.cloudmp.mhpt.modules.system.domain.User;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */

public interface UserService {

    /**
     * get
     * @param id
     * @return
     */

    User findById(long id);

    /**
     * create
     * @param resources
     * @return
     */

    UserDTO create(User resources);

    /**
     * update
     * @param resources
     */

    void update(User resources);

    /**
     * delete
     * @param id
     */

    void delete(Long id);

    /**
     * findByName
     * @param userName
     * @return
     */

    UserDTO findByName(String userName);

    /**
     * 修改密码
     * @param username
     * @param encryptPassword
     */

    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     * @param username
     * @param url
     */

    void updateAvatar(String username, String url);

    /**
     * 修改邮箱
     * @param username
     * @param email
     */

    void updateEmail(String username, String email);

//    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(UserQueryCriteria criteria,Pageable pageable);

    /*
    * 根据用户id获取角色信息
    * */
    Set<RoleSmallDTO> getRoleById(Long id);

    Set<Role> getAllRoleById(Long id);

    JobSmallDTO getJobById(Long id);

    DeptSmallDTO getDeptById(Long id);

}
