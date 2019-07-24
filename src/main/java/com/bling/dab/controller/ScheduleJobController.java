package com.bling.dab.controller;


import com.bling.dab.common.quartz.BaseResult;
import com.bling.dab.common.quartz.BootstrapTableResult;
import com.bling.dab.domain.ScheduleJob;
import com.bling.dab.service.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 定时任务控制层
 * @author admin
 * @date 2017-11-25 18:49
 */

@Controller
@RequestMapping(value = "/scheduleJob")
public class ScheduleJobController {
    public static Logger log = LoggerFactory.getLogger(ScheduleJobController.class);

    @Resource
    private ScheduleJobService scheduleJobService;

    @RequestMapping(value = "/quartz", method = RequestMethod.GET)
    public String quartz(){
        return "/quartz.jsp";
    }
    /**
     * 查询所有的定时任务，用于页面加载时显示表格数据
     * @param pageSize 每页显示数量
     * @param pageNumber 页数
     * @return BootstrapTableResult
     */
    @RequestMapping(value = "/listAllJob", method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableResult listAllJob(int pageSize, int pageNumber) {
        BootstrapTableResult bootstrapTableResult = scheduleJobService.listAllJob(pageSize, pageNumber);
        return bootstrapTableResult;
    }

    /**
     * 暂停定时任务
     * @param jobId
     * @return BaseResult
     */
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult pauseJob(int jobId) {
        scheduleJobService.pauseJob(jobId);
        return new BaseResult(1, "success", "定时任务暂停成功");
    }

    /**
     * 恢复定时任务
     * @param jobId
     * @return BaseResult
     */
    @RequestMapping(value="/resumeJob",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult resumeJob(int jobId){
        scheduleJobService.resumeJob(jobId);
        return new BaseResult(1, "success", "定时任务恢复成功");
    }

    /**
     * 立即执行定时任务
     * @param jobId
     * @return BaseResult
     */
    @RequestMapping(value = "/runOnce",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult runOnce(int jobId){
        scheduleJobService.runOnce(jobId);
        return new BaseResult(1, "success", "立即执行定时任务成功");
    }

    /**
     * 更新时间表达式
     * @param id
     * @param cronExpression
     * @return BaseResult
     */
    @RequestMapping(value = "/updateCron",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateCron(int id,String cronExpression){
        scheduleJobService.updateCron(id,cronExpression);
        return new BaseResult(1, "success", "更新时间表达式成功");
    }

    /**
     * 添加定时任务
     * @param scheduleJob
     * @return
     */
    @RequestMapping(value = "/addScheduleJob",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addScheduleJob(ScheduleJob scheduleJob){
        try {
            scheduleJobService.addScheduleJob(scheduleJob);
        }catch (Exception e){
            return new BaseResult(0, "default", "添加定时任务失败");
        }

        return new BaseResult(1, "success", "添加定时任务成功");
    }

}
