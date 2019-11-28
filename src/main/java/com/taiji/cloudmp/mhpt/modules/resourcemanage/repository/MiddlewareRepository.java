package com.taiji.cloudmp.mhpt.modules.resourcemanage.repository;


import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Middleware;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.MiddlewareDto;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.MiddlewareQueryCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddlewareRepository {
    Long MiddlewareListCount(MiddlewareQueryCriteria middlewareQueryCriteria);

    List<Middleware> MiddlewareList(MiddlewareQueryCriteria middlewareQueryCriteria);

    MiddlewareDto createMiddleware(Middleware middleware);

    void updateMiddleware(Middleware middleware);

    void deleteMiddleware(String[] id);

    Middleware findById(String id);
}
