package com.taiji.cloudmp.mhpt.modules.system.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


public class User {

    public final static String ID = "id";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String DEPT_ID = "dept_id";
    public final static String JOB_ID = "job_id";

    private Long id;

    //用户名
    private String username;

    //头像地址
    private String avatar;

    //邮箱
    private String email;

    //联系电话
    private String phone;

    //状态：0禁用1启用
    private Boolean enabled;

    //密码
    private String password;

    //创建日期
    private Timestamp createtime;

    //最后修改密码时间
    private Date lastPasswordResetTime;

    //所属单位id
    private String dept_id;

    //工作id
    private String job_id;

    private Set<Role> roles;

    private Job job;

    private Dept dept;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Date getLastPasswordResetTime() {
        return lastPasswordResetTime;
    }

    public void setLastPasswordResetTime(Date lastPasswordResetTime) {
        this.lastPasswordResetTime = lastPasswordResetTime;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}
