package com.taiji.cloudmp.mhpt.modules.activiti.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class ModelDTO implements Serializable {

    /**
     * ID
     */
    private String id;

    private Integer rev;
    /**
     * 名称
     */
   private String name;
   
   private String key;
   
   private String category;
   
   private Timestamp create_time;
   
   private Timestamp last_update_time;
   
   private Integer version;
   
   private String meta_info;
   
   private String deployment_id;
   
   private String editor_source_value_id;
   
   private String editor_source_extra_value_id;
   
   private String tenant_id;
   
   private String deployment_lable;  
   

   public static ModelDTO Model2DTO(Model m){
	   ModelDTO dto = new ModelDTO();
	   dto.setId(m.getId());
//	   dto.setREV_(m.get);//model接口中不存在
	   dto.setName(m.getName());
	   dto.setKey(m.getKey());
	   dto.setCategory(m.getCategory());
	   dto.setCreate_time(new Timestamp(m.getCreateTime().getTime()));
	   dto.setLast_update_time(new Timestamp(m.getLastUpdateTime().getTime()));
	   dto.setVersion(m.getVersion());
	   dto.setMeta_info(m.getMetaInfo());
	   dto.setDeployment_id(m.getDeploymentId());
	   dto.setTenant_id(m.getTenantId());
	   if(StringUtils.isNotBlank(dto.getDeployment_id())){
		   dto.setDeployment_lable("已发布");
	   }else{
		   dto.setDeployment_lable("未发布");
	   }
	   
	   return dto;
   }
   
   public static List<ModelDTO> modelList2Dto(List<Model> m){
	  List<ModelDTO> dtoList = new ArrayList<ModelDTO>();
	  for(Model model :m){
		  ModelDTO model2dto = ModelDTO.Model2DTO(model);
		  dtoList.add(model2dto);
	  }
	  
	  return dtoList;
   }
   
}