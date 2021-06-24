package com.iris.community.config;

import com.iris.community.quartz.AlphaJob;
import com.iris.community.quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

//配置-> 数据库 -> 调用
@Configuration
public class QuartzConfig {

    //配置JobDetail
    //@Bean
    public JobDetailFactoryBean alphaJobDeail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(AlphaJob.class);
        jobDetailFactoryBean.setName("alphaJob");
        jobDetailFactoryBean.setGroup("alphaJobGroup");
        //任务长久保存
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setRequestsRecovery(true);
        return jobDetailFactoryBean;
    }

    //配置Trigger
    //@Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDeail){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(alphaJobDeail);
        simpleTriggerFactoryBean.setName("alphaTrigger");
        simpleTriggerFactoryBean.setGroup("alphaTriggerGroup");
        simpleTriggerFactoryBean.setRepeatInterval(3000);
        //底层要存Job的状态
        simpleTriggerFactoryBean.setJobDataMap(new JobDataMap());
        return simpleTriggerFactoryBean;
    }
    //刷新帖子分数的任务
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(PostScoreRefreshJob.class);
        jobDetailFactoryBean.setName("postScoreRefreshJob");
        jobDetailFactoryBean.setGroup("postScoreRefreshJob");
        //任务长久保存
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setRequestsRecovery(true);
        return jobDetailFactoryBean;
    }
    //配置Trigger
    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(postScoreRefreshJobDetail);
        simpleTriggerFactoryBean.setName("postScoreRefreshTrigger");
        simpleTriggerFactoryBean.setGroup("postScoreRefreshTriggerGroup");
        simpleTriggerFactoryBean.setRepeatInterval(1000 * 60 * 5);
        //底层要存Job的状态
        simpleTriggerFactoryBean.setJobDataMap(new JobDataMap());
        return simpleTriggerFactoryBean;
    }
}
//任务 任务详情 触发器
//一旦启动服务后,配置文件默认被加载，quartz根据配置文件向数据库中插入数据，数据库里有了数据，quartz底层控制器scheduler来进行调度