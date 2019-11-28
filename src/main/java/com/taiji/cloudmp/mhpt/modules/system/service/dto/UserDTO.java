package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taiji.cloudmp.mhpt.modules.system.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Data
public class UserDTO implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String username;

    private String avatar;

    private String email;

    private String phone;

    private Boolean enabled;

    @JsonIgnore
    private String password;

    private Timestamp createTime;

    private Date lastPasswordResetTime;

    @ApiModelProperty(hidden = true)
    private Set<RoleSmallDTO> roles;

    @ApiModelProperty(hidden = true)
    private JobSmallDTO job;

    private DeptSmallDTO dept;

    private Long deptId;

    public static UserDTO MapToDto(User user,Set<RoleSmallDTO> roleSmallDTOSet,DeptSmallDTO deptSmallDTO,JobSmallDTO jobSmallDTO){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setEnabled(user.getEnabled());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreateTime(user.getCreatetime());
        userDTO.setLastPasswordResetTime(user.getLastPasswordResetTime());
        userDTO.setRoles(roleSmallDTOSet);
        userDTO.setJob(jobSmallDTO);
        userDTO.setDept(deptSmallDTO);
        return userDTO;
    }
}
