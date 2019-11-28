package com.taiji.cloudmp.mhpt.modules.system.domain;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Data
public class Dict implements Serializable {



    public final static String ID ="id";
    public final static String NAME ="name";
    public final static String REMARK ="remark";
    public final static String PID ="pid";

    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 字典名称
     */
    @NotBlank
    private String name;

    /**
     * 描述
     */
    private String remark;

    /**
     * 上级字典
     */
    @NotNull
    private Long pid;

    private List<DictDetail> dictDetails;

    public @interface Update {}
}