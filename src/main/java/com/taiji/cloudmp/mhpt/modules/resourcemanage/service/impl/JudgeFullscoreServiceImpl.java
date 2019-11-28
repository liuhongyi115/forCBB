package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.JudgeFullscore;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.repository.JudgeFullscoreRepository;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.JudgeFullscoreService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeFullscoreDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeFullscoreQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper.JudgeFullscoreMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cn.hutool.core.util.IdUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;


/**
* @author xiao
* @date 2019-11-14
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JudgeFullscoreServiceImpl implements JudgeFullscoreService {

    @Autowired
    private JudgeFullscoreRepository judgeFullscoreRepository;

    @Autowired
    private JudgeFullscoreMapper judgeFullscoreMapper;

    @Override
    public Object queryAll(JudgeFullscoreQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = judgeFullscoreRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<JudgeFullscore> list = judgeFullscoreRepository.findAll(criteria);
    	for(JudgeFullscore jf:list){
    		jf.setFullScore(jf.getFullScore()*5);
    	}
        Page<JudgeFullscore> page = new PageImpl<JudgeFullscore>(list,pageable,rowCount);
        return page;
    }
    @Override
    public Object queryAll(JudgeFullscoreQueryCriteria criteria){
    	Long rowCount = judgeFullscoreRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<JudgeFullscore> list = judgeFullscoreRepository.findAll(criteria);
        return judgeFullscoreMapper.toDto(list);
    }

    @Override
    public JudgeFullscoreDTO findById(String id) {
        Optional<JudgeFullscore> optionalJudgeFullscore = Optional.ofNullable(judgeFullscoreRepository.findById(id));
        ValidationUtil.isNull(optionalJudgeFullscore,"JudgeFullscore","id",id);
        return judgeFullscoreMapper.toDto(optionalJudgeFullscore.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JudgeFullscoreDTO create(JudgeFullscore resources) {
    	resources.setId(IdUtil.simpleUUID());
		judgeFullscoreRepository.add(resources);
        return judgeFullscoreMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JudgeFullscore resources) {
        Optional<JudgeFullscore> optionalJudgeFullscore = Optional.ofNullable(judgeFullscoreRepository.findById(resources.getId()));;
        ValidationUtil.isNull( optionalJudgeFullscore,"JudgeFullscore","id",resources.getId());
        JudgeFullscore judgeFullscore = optionalJudgeFullscore.get();
        judgeFullscoreRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        judgeFullscoreRepository.delete(id);
    }
}