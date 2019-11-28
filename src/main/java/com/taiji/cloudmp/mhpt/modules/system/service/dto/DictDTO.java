package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
* @author wuyw
* @date 2019-04-10
*/
@Data
public class DictDTO implements Serializable {

    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 描述
     */
    private String remark;

    /**
     * 上级字典
     */
    private Long pid;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DictDTO> children;


    public String getLabel() {
        return remark;
    }


}
