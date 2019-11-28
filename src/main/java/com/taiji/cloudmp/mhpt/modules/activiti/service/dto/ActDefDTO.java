package com.taiji.cloudmp.mhpt.modules.activiti.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class ActDefDTO implements Serializable {

   private String id;
   
   private String name;
   
   private String key;
   
   public static ActDefDTO actDef2DTO(Map map){
	   ActDefDTO dto = new ActDefDTO();
	   dto.setId(map.get("id_").toString());
	   dto.setName(map.get("name_").toString());
	   dto.setKey(map.get("key_").toString());
	   return dto;
   }
   
   public static List<ActDefDTO> map2DtoList(List<Map> list){
	  List<ActDefDTO> dtoList = new ArrayList<ActDefDTO>();
	  for(Map map : list){
		  ActDefDTO actDefDTO = ActDefDTO.actDef2DTO(map);
		  dtoList.add(actDefDTO);
	  }
	  return dtoList;
   }
}