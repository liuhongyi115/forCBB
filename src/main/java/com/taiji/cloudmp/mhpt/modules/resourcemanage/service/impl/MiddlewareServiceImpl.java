package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.impl;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Middleware;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.repository.MiddlewareRepository;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.MiddlewareService;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.MiddlewareDto;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.MiddlewareQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.mapper.MiddlewareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MiddlewareServiceImpl implements MiddlewareService {
    @Autowired
    private MiddlewareRepository middlewareRepository;
    @Autowired
    private MiddlewareMapper middlewareMapper;

    @Override
    public Object MiddlewareList(MiddlewareQueryCriteria middlewareQueryCriteria, Pageable pageable){
        middlewareQueryCriteria.Pageable2BaseModel(pageable);
        Long rowcount = middlewareRepository.MiddlewareListCount(middlewareQueryCriteria);
        middlewareQueryCriteria.getPager().setRowCount(rowcount);
        List<Middleware> middlewareList = middlewareRepository.MiddlewareList(middlewareQueryCriteria);
        Page<Middleware> page = new PageImpl<Middleware>(middlewareList,pageable,rowcount);
        return page;
    }

    @Override
    public MiddlewareDto createMiddleware(Middleware middleware){
        String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        middleware.setId(id);
        middlewareRepository.createMiddleware(middleware);
        return middlewareMapper.toDto(middleware);
    }

    @Override
    public void updateMiddleware(Middleware middleware){
        middlewareRepository.updateMiddleware(middleware);
    }

    @Override
    public void deleteMiddleware(String[] id){
        middlewareRepository.deleteMiddleware(id);
    }

    @Override
    public MiddlewareDto findById(String id){
        Middleware middleware = middlewareRepository.findById(id);
        return middlewareMapper.toDto(middleware);
    }
}
