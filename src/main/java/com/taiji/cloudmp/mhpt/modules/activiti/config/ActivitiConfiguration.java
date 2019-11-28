package com.taiji.cloudmp.mhpt.modules.activiti.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.taiji.cloudmp.activiti.CustomProcessDiagramGenerator;

@Configuration
public class ActivitiConfiguration implements ProcessEngineConfigurationConfigurer{
    
    @Autowired
    private CustomProcessDiagramGenerator customProcessDiagramGenerator;
    
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        // 流程图字体
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        //自定义流程图样式
        processEngineConfiguration.setProcessDiagramGenerator(customProcessDiagramGenerator);
    }
}
