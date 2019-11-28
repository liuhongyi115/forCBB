package com.taiji.cloudmp.mhpt.modules.system.service;

import com.taiji.cloudmp.mhpt.modules.system.domain.Dict;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictService {

    /**
     * 查询
     * @param dict
     * @param pageable
     * @return
     */
    Object queryAll(DictQueryCriteria dict, Pageable pageable);

    /**
     * queryAll
     * @param criteria
     * @return
     */
    List<DictDTO> queryAll(DictQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    DictDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    DictDTO create(Dict resources);

    /**
     * update
     * @param resources
     */
    void update(Dict resources);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    /**
     * 构建字典树
     * @param dictDTOS
     * @return
     */
    Object buildTree(List<DictDTO> dictDTOS);
}