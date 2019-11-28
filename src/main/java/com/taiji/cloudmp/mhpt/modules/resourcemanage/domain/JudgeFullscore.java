package com.taiji.cloudmp.mhpt.modules.resourcemanage.domain;

import lombok.Data;
import java.io.Serializable;

/**
* @author xiao
* @date 2019-11-14
*/
@Data
public class JudgeFullscore implements Serializable {
    private String id;
	// 满分分值
    private Integer fullScore;
}