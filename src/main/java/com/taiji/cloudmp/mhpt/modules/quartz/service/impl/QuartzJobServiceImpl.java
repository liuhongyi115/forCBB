package com.taiji.cloudmp.mhpt.modules.quartz.service.impl;

import java.util.List;
import java.util.Optional;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.quartz.domain.QuartzJob;
import com.taiji.cloudmp.mhpt.modules.quartz.domain.QuartzLog;
import com.taiji.cloudmp.mhpt.modules.quartz.repository.QuartzJobRepository;
import com.taiji.cloudmp.mhpt.modules.quartz.repository.QuartzLogRepository;
import com.taiji.cloudmp.mhpt.modules.quartz.service.QuartzJobService;
import com.taiji.cloudmp.mhpt.modules.quartz.service.dto.QuartzJobQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.quartz.utils.QuartzManage;
import com.taiji.cloudmp.utils.ValidationUtil;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Service(value = "quartzJobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    private QuartzJobRepository quartzJobRepository;

    @Autowired
    private QuartzLogRepository quartzLogRepository;

    @Autowired
    private QuartzManage quartzManage;

    @Override
    public Object queryAll(QuartzJobQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = quartzJobRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<QuartzJob> list = quartzJobRepository.findAll(criteria);
        Page<QuartzJob> page = new PageImpl<QuartzJob>(list,pageable,rowCount);

//    	Map map = new HashMap();
//    	map.put("content", list);
//    	map.put("totalElements", list.size());
    	return page;
        //return PageUtil.toPage(quartzJobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public Object queryAllLog(QuartzJobQueryCriteria criteria, Pageable pageable){
    	criteria.Pageable2BaseModel(pageable);
    	Long rowCount = quartzLogRepository.findTotalCount(criteria);
    	criteria.getPager().setRowCount(rowCount);
    	List<QuartzLog> list = quartzLogRepository.findAll(criteria);
        Page<QuartzLog> page = new PageImpl<QuartzLog>(list,pageable,rowCount);

//    	Map map = new HashMap();
//    	map.put("content", list);
//    	map.put("totalElements", list.size());
    	return page;
    	//return PageUtil.toPage(quartzLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public QuartzJob findById(Long id) {
    	Optional<QuartzJob> optionalQuartzJob = Optional.ofNullable(quartzJobRepository.findById(id));
        ValidationUtil.isNull(optionalQuartzJob,"QuartzJob","id",id);
        return optionalQuartzJob.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron表达式格式错误");
        }
        quartzJobRepository.add(resources);
        quartzManage.addJob(resources);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QuartzJob resources) {
        if(resources.getId().equals(1L)){
            throw new BadRequestException("该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron表达式格式错误");
        }
        quartzJobRepository.update(resources);
        quartzManage.updateJobCron(resources);
    }

    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if(quartzJob.getId().equals(1L)){
            throw new BadRequestException("该任务不可操作");
        }
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        } else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        quartzJobRepository.update(quartzJob);
    }

    @Override
    public void execution(QuartzJob quartzJob) {
        if(quartzJob.getId().equals(1L)){
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.runAJobNow(quartzJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(QuartzJob quartzJob) {
        if(quartzJob.getId().equals(1L)){
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.deleteJob(quartzJob);
        quartzJobRepository.delete(quartzJob);
    }
}
