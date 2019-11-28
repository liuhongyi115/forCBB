package com.taiji.cloudmp.mhpt.modules.system.repository;

import com.taiji.cloudmp.mhpt.modules.system.domain.Dict;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictQueryCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Repository
public interface DictRepository {

    String findNameById(Long id);

    Dict findById(Long id);

    void deleteById(Long id);

    void save(Dict dict);

    List<Dict> findAll(DictQueryCriteria criteria);

    void update(Dict resources);
}