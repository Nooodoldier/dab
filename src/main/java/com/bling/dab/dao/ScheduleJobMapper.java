package com.bling.dab.dao;



import com.bling.dab.domain.ScheduleJob;

import java.util.List;

/**
 * 定时任务
 * @author admin
 * @date 2017-11-20 下午 15:52
 */
public interface ScheduleJobMapper {

    /**
     * 查询所有的定时任务
     * @return List<ScheduleJob>
     */
    List<ScheduleJob> listAllJob();

    /**
     * 更新定时任务状态
     * @param scheduleJob
     */
    void updateJobStatusById(ScheduleJob scheduleJob);

    /**
     * 根据主键查询定时任务
     * @param id
     * @return ScheduleJob
     */
    ScheduleJob getScheduleJobByPrimaryKey(int id);

    /**
     * 更新时间表达式
     * @param scheduleJob
     */
    void updateJobCronExpressionById(ScheduleJob scheduleJob);

    /**
     * 添加定时任务
     * @param scheduleJob
     */
    void addScheduleJob(ScheduleJob scheduleJob);
}