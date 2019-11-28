package com.taiji.cloudmp.mhpt.modules.system.service;

import com.taiji.cloudmp.mhpt.modules.system.domain.DictDetail;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDetailDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDetailQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictDetailService {

    /**
     * findById
     * @param id
     * @return
     */
    DictDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    DictDetailDTO create(DictDetail resources);

    /**
     * update
     * @param resources
     */
    void update(DictDetail resources);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    Map queryAll(DictDetailQueryCriteria criteria, Pageable pageable);
}