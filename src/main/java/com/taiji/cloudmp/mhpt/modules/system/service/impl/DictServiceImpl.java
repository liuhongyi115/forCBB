package com.taiji.cloudmp.mhpt.modules.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.taiji.cloudmp.mhpt.modules.system.repository.DictDetailRepository;
import com.taiji.cloudmp.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.taiji.cloudmp.mhpt.modules.system.domain.Dict;
import com.taiji.cloudmp.mhpt.modules.system.repository.DictRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.DictService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.DictMapper;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Autowired
    private DictMapper dictMapper;

    @Override
    public Object queryAll(DictQueryCriteria dict, Pageable pageable){
        List<Dict> list = dictRepository.findAll(dict);
        return dictMapper.toDto(list);
    }

    @Override
    public List<DictDTO> queryAll(DictQueryCriteria criteria) {
        List<Dict> list = dictRepository.findAll(criteria);
        return dictMapper.toDto(list);
    }

    @Override
    public DictDTO findById(Long id) {
        Dict dict = dictRepository.findById(id);
        return dictMapper.toDto(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDTO create(Dict resources) {
        dictRepository.save(resources);
        return dictMapper.toDto(resources);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
        Optional<Dict> optionalDict = Optional.ofNullable(dictRepository.findById(resources.getId()));
        ValidationUtil.isNull( optionalDict,"DictDetail","id",resources.getId());
        Dict dict = optionalDict.get();
        resources.setId(dict.getId());
        dictRepository.update(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //删除字典之前先删除字典详情
        dictDetailRepository.deleteByDictId(id);
        dictRepository.deleteById(id);
    }


    /**
     * 构建字典树
     * @param dictDTOS
     * @return
     */
    @Override
    public Object buildTree(List<DictDTO> dictDTOS) {
        Set<DictDTO> trees = new LinkedHashSet<>();
        Set<DictDTO> dicts= new LinkedHashSet<>();
        List<String> dictNames = dictDTOS.stream().map(DictDTO::getName).collect(Collectors.toList());
        Boolean isChild;
        for (DictDTO dictDTO : dictDTOS) {
            isChild = false;
            if ("0".equals(dictDTO.getPid().toString())) {
                trees.add(dictDTO);
            }
            for (DictDTO it : dictDTOS) {
                if (it.getPid().equals(dictDTO.getId())) {
                    isChild = true;
                    if (dictDTO.getChildren() == null) {
                        dictDTO.setChildren(new ArrayList<DictDTO>());
                    }
                    dictDTO.getChildren().add(it);
                }
            }
            if(isChild)
                dicts.add(dictDTO);
            else if(!dictNames.contains(dictRepository.findNameById(dictDTO.getPid())))
                dicts.add(dictDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = dicts;
        }
        Integer totalElements = dictDTOS!=null?dictDTOS.size():0;

        Map map = new HashMap();
        map.put("totalElements",totalElements);
        map.put("content",CollectionUtils.isEmpty(trees)?dictDTOS:trees);
        return map;
    }
}