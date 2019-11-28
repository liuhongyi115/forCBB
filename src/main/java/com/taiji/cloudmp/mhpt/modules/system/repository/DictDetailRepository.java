package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.system.domain.DictDetail;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDetailDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDetailQueryCriteria;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Repository
public interface DictDetailRepository {


    DictDetail findById(Long id);

    void deleteById(Long id);

    void save(DictDetail dictDetail);

    void update(DictDetail dictDetail);

    List<DictDetail> findAll(DictDetailQueryCriteria criteria);

    void deleteByDictId(Long id);

    Long getCount(DictDetailQueryCriteria criteria);
}