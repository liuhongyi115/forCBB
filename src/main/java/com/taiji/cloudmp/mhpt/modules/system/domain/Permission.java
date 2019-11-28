package com.taiji.cloudmp.mhpt.modules.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Getter
@Setter
public class Permission implements Serializable{

	private static final long serialVersionUID = 2667307357287149073L;
	
	public static final String ID 		  = "id";
	public static final String NAME 	  = "name";
	public static final String ALIAS 	  = "alias";
	public static final String ROLES 	  = "roles";
	public static final String CREATETIME = "createTime";
	
	@NotNull(groups = {Update.class})
	private Long id;

	@NotBlank
	private String name;

	//上级类目
	@NotNull
	private Long pid;

	@NotBlank
	private String alias;

	@JsonIgnore
	private Set<Role> roles;

	private Date createTime;

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pid=" + pid +
				", alias='" + alias + '\'' +
				", createTime=" + createTime +
				'}';
	}

	public interface Update{}
}
