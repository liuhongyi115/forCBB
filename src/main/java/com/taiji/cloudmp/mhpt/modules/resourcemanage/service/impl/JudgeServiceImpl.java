package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl;

import com.taiji.cloudmp.utils.ValidationUtil;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Judge;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.repository.JudgeRepository;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.JudgeService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper.JudgeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;


/**
* @author xiao
* @date 2019-10-22
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private JudgeMapper judgeMapper;

    @Override
    public Object queryAll(JudgeQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = judgeRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<Judge> list = judgeRepository.findAll(criteria);
        Page<Judge> page = new PageImpl<Judge>(list,pageable,rowCount);
        return page;
    }

    @Override
    public Object queryAll(JudgeQueryCriteria criteria){
    	Long rowCount = judgeRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<Judge> list = judgeRepository.findAll(criteria);
        return judgeMapper.toDto(list);
    }

    @Override
    public JudgeDTO findById(String id) {
        Optional<Judge> optionalJudge = Optional.ofNullable(judgeRepository.findById(id));
        ValidationUtil.isNull(optionalJudge,"Judge","id",id);
        return judgeMapper.toDto(optionalJudge.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JudgeDTO create(Judge resources) {
    	String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    	resources.setId(id);
		judgeRepository.add(resources);
        return judgeMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Judge resources) {
        judgeRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] id) {
        judgeRepository.delete(id);
    }
    
    public boolean isExist(Judge resources){
    	Optional<Judge> optionalJudge = Optional.ofNullable(judgeRepository.findByName(resources.getName()));
    	//存在返回true，不存在返回false
    	return optionalJudge.isPresent();
    }

}