package com.taiji.cloudmp.mhpt.modules.system.service.impl;


import java.sql.Timestamp;
import java.util.*;

import com.taiji.cloudmp.mhpt.modules.system.service.dto.*;
import com.taiji.cloudmp.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.EntityExistException;
import com.taiji.cloudmp.exception.EntityNotFoundException;
import com.taiji.cloudmp.mhpt.modules.monitor.service.RedisService;
import com.taiji.cloudmp.mhpt.modules.system.domain.Role;
import com.taiji.cloudmp.mhpt.modules.system.domain.User;
import com.taiji.cloudmp.mhpt.modules.system.repository.UserRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.UserService;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.UserMapper;
import com.taiji.cloudmp.utils.ValidationUtil;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @SuppressWarnings("unchecked")
	@Override
    public Object queryAll(UserQueryCriteria user, Pageable pageable) {
        user.Pageable2BaseModel(pageable);
        Long rowCount = userRepository.findByWhereCount(user);
        user.getPager().setRowCount(rowCount);

        List<User> userList = userRepository.findByWhere(user);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i=0;i<userList.size();i++){
            User user1 = userList.get(i);
            JobSmallDTO jobSmallDTO = getJobById(Long.parseLong(user1.getJob_id()));
            DeptSmallDTO deptSmallDTO = getDeptById(Long.parseLong(user1.getDept_id()));
            Set<RoleSmallDTO> roleSmallDTOSet = getRoleById(user1.getId());
            UserDTO userDTO1 = UserDTO.MapToDto(user1,roleSmallDTOSet,deptSmallDTO,jobSmallDTO);
            userDTOList.add(userDTO1);
        }
        Page<UserDTO> userPage = new PageImpl<UserDTO>(userDTOList,pageable,rowCount);
//        Map map = PageUtil.toPage(userPage.map(userMapper::toDto));

        return userPage;
    }

    @Override
    public User findById(long id) {
        UserDTO userDTO = new UserDTO();
        UserQueryCriteria user = new UserQueryCriteria();
        user.setId(id);
        List<User> userList = userRepository.findByWhere(user);
        User user1 = userList.get(0);
        return user1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(User resources) {

        if(userRepository.findByUsername(resources.getUsername())!=null){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }

        if(userRepository.findUserByEmail(resources.getEmail())!=null){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }

        // 默认密码 123456，此密码是加密后的字符
        resources.setPassword("e10adc3949ba59abbe56e057f20f883e");
        resources.setAvatar("https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg");
        resources.setJob_id(resources.getJob().getId().toString());
        resources.setDept_id(resources.getDept().getId().toString());
        resources.setCreatetime(new Timestamp(new Date().getTime()));
        userRepository.addUser(resources);

        List<Role> userRole  = new ArrayList(resources.getRoles());
        for (int i=0;i<userRole.size();i++){
            Map roleId = new HashMap();
            roleId.put("roleid",userRole.get(i).getId());
            roleId.put("userid",resources.getId());
            userRepository.addRole(roleId);
        }
        return userMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
//        Optional<User> userOptional = userRepository.findUserById(resources.getId());
//        ValidationUtil.isNull(userOptional,"User","id",resources.getId());

//        User user = userOptional.get();
        User user = userRepository.findUserById(resources.getId());

        User user1 = userRepository.findByUsername(user.getUsername());
        User user2 = userRepository.findUserByEmail(user.getEmail());

        if(user1 !=null&&!user.getId().equals(user1.getId())){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }

        if(user2!=null&&!user.getId().equals(user2.getId())){
            throw new EntityExistException(User.class,"email",resources.getEmail());
        }

        // 如果用户的角色改变了，需要手动清理下缓存
//        if (!resources.getRoles().equals(user.getRoles())) {
//            String key = "role::loadPermissionByUser:" + user.getUsername();
//            redisService.delete(key);
//            key = "role::findByUsers_Id:" + user.getId();
//            redisService.delete(key);
//        }
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setDept_id(resources.getDept().getId().toString());
        user.setJob_id(resources.getJob().getId().toString());
        user.setPhone(resources.getPhone());

        List roleList = userRepository.getAllRoleByUserid(resources.getId());
        if (roleList!=null){
            userRepository.deleteRole(resources.getId());
        }
        List<Role> userRole  = new ArrayList(resources.getRoles());
        for (int i=0;i<userRole.size();i++){
            Map roleId = new HashMap();
            roleId.put("roleid",userRole.get(i).getId());
            roleId.put("userid",resources.getId());
            userRepository.addRole(roleId);
        }

        userRepository.updateUser(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userRepository.deleteRole(id);
        userRepository.deleteUserById(id);

    }

    @Override
    public UserDTO findByName(String userName) {
        User user = null;
        if(ValidationUtil.isEmail(userName)){
            user = userRepository.findUserByEmail(userName);
        } else {
            user = userRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO = userMapper.toDto(user);
            userDTO.setDept(getDeptById(Long.parseLong(user.getDept_id())));
            userDTO.setJob(getJobById(Long.parseLong(user.getJob_id())));
            userDTO.setRoles(getRoleById(user.getId()));
            return userDTO;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userRepository.updatePass(username,pass,new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String url) {
        userRepository.updateAvatar(username,url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username,email);
    }

    @Override
    public Set<RoleSmallDTO> getRoleById(Long id){
        List<Map> roleList = userRepository.getRoleById(id);
        List<RoleSmallDTO> roleSmallDTOS = new ArrayList<>() ;
        for (int i=0;i<roleList.size();i++){
            Map role1 = roleList.get(i);
            RoleSmallDTO roleSmallDTO =new RoleSmallDTO();
            roleSmallDTO.setId(Long.parseLong(role1.get("id").toString()));
            roleSmallDTO.setName(role1.get("name").toString());
            roleSmallDTO.setLevel(Integer.parseInt(role1.get("level").toString()));
            roleSmallDTO.setDataScope(role1.get("data_scope").toString());
            roleSmallDTOS.add(roleSmallDTO);
        }
        Set<RoleSmallDTO> roleSmallDTOSet = new HashSet(roleSmallDTOS);
        return roleSmallDTOSet;
    }

    @Override
    public Set<Role> getAllRoleById(Long id){
        List<Map> roleList = userRepository.getAllRoleByUserid(id);
        Set<Role> roles = null;
        for (int i=0;i<roleList.size();i++){
            Map role1 = roleList.get(i);
            Role role =new Role();
            role.setId(Long.parseLong(role1.get("id").toString()));
            role.setName(role1.get("name").toString());
            role.setDataScope(role1.get("data_scope").toString());
            role.setLevel(Integer.parseInt(role1.get("level").toString()));
            role.setRemark(role1.get("remark").toString());
            roles.add(role);
        }
        return roles;
    }

    //获取个人工作信息
    @Override
    public JobSmallDTO getJobById(Long id){
        String job = userRepository.getJobById(id).get("name").toString();
        JobSmallDTO jobSmallDTO = new JobSmallDTO();
        jobSmallDTO.setId(id);
        jobSmallDTO.setName(job);
        return jobSmallDTO;
    }

    //获取个人单位信息
    @Override
    public DeptSmallDTO getDeptById(Long id){
        String dept = userRepository.getDeptById(id);
        DeptSmallDTO deptSmallDTO = new DeptSmallDTO();
        deptSmallDTO.setId(id);
        deptSmallDTO.setName(dept);
        return deptSmallDTO;
    }


}
