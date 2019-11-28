package com.taiji.cloudmp.mhpt.modules.system.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class DictDetail implements Serializable {


    public final static String ID ="id";
    public final static String LABEL ="label";
    public final static String VALUE ="value";
    public final static String SORT ="sort";
    public final static String DICTID ="dictId";


    private Long id;
    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private String sort;

    /**
     * 字典集合
     */
    private Dict dict;


    /**
     * 字典ID
     */
    private Long dictId;


}