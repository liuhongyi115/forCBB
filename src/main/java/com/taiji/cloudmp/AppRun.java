package com.taiji.cloudmp;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.taiji.cloudmp.utils.SpringContextHolder;

/**
 * @author Zheng Jie
 * @date 2018/11/15 9:20:19
 */
@EnableAsync
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableTransactionManagement
@MapperScan("com.taiji.cloudmp.**.repository")
public class AppRun {

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
