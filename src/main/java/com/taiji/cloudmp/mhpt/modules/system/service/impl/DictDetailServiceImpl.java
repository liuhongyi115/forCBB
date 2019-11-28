package com.taiji.cloudmp.mhpt.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.taiji.cloudmp.mhpt.modules.system.domain.Permission;
import com.taiji.cloudmp.utils.PageUtil;
import com.taiji.cloudmp.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.mhpt.modules.system.domain.DictDetail;
import com.taiji.cloudmp.mhpt.modules.system.repository.DictDetailRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.DictDetailService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDetailDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDetailQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.DictDetailMapper;

import javax.validation.constraints.NotNull;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl implements DictDetailService {

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Autowired
    private DictDetailMapper dictDetailMapper;

    @Override
    public Map queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        criteria.Pageable2BaseModel(pageable);
        Long count = dictDetailRepository.getCount(criteria);
        criteria.getPager().setRowCount(count);
        List<DictDetail> list = dictDetailRepository.findAll(criteria);
        Page<DictDetail> page = new PageImpl<>(list,pageable,count);
        return PageUtil.toPage(page);
    }

    @Override
    public DictDetailDTO findById(Long id) {
        DictDetail detail = dictDetailRepository.findById(id);
        return dictDetailMapper.toDto(detail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDTO create(DictDetail resources) {
        Long dictId = resources.getDict().getId();
        resources.setDictId(dictId);
        dictDetailRepository.save(resources);
        return dictDetailMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {
        Optional<DictDetail> optionalDictDetail = Optional.ofNullable(dictDetailRepository.findById(resources.getId()));
        ValidationUtil.isNull( optionalDictDetail,"DictDetail","id",resources.getId());
        DictDetail dictDetail = optionalDictDetail.get();
        resources.setId(dictDetail.getId());
        dictDetailRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dictDetailRepository.deleteById(id);
    }
}