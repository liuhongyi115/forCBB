package com.taiji.cloudmp.mhpt.modules.resourcemanage.domain;

import lombok.Data;
import java.io.Serializable;

/**
* @author xiao
* @date 2019-10-22
*/
@Data
public class Judge implements Serializable {
	// 编号
    private String id;
	// 内容
    private String name;
	// 序号
    private int serialNumber;
}