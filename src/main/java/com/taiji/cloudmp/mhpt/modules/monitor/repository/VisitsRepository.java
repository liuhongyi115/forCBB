package com.taiji.cloudmp.mhpt.modules.monitor.repository;

import com.taiji.cloudmp.mhpt.modules.monitor.domain.Visits;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-13
 */
@Mapper
public interface VisitsRepository {

    /**
     * findByDate
     * @param date
     * @return
     */
    Visits findByDate(String date);

    void save(Visits visits);

    void update(Visits visits);

    /**
     * 获得一个时间段的记录
     * @param date1
     * @param date2
     * @return
     */
    List<Visits> findAllVisits(@Param("date1") String date1,@Param("date2") String date2);
}
