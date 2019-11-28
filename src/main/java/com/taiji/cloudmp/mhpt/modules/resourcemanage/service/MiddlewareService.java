package com.taiji.cloudmp.mhpt.modules.resourcemanage.service;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Middleware;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.MiddlewareDto;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.MiddlewareQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MiddlewareService {

    Object MiddlewareList(MiddlewareQueryCriteria middlewareQueryCriteria, Pageable pageable);

    MiddlewareDto createMiddleware(Middleware middleware);

    void updateMiddleware(Middleware middleware);

    void deleteMiddleware(String[] id);

    MiddlewareDto findById(String id);
}
