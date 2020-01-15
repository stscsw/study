package timer;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class QuartTest {

        public static void main(String[] args) throws SchedulerException, InterruptedException {
            // 1、创建调度器Scheduler
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
            JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                    .usingJobData("key","cswJob")
                    .withIdentity("job1", "group1").build();
            // 3、构建Trigger实例,每隔1s执行一次
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                    .startNow()//立即生效
                    .usingJobData("key","cswTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)//每隔1s执行一次
                            .repeatForever()).build();//一直执行

            //4、执行
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("--------scheduler start ! ------------");
            scheduler.start();


            //睡眠
            TimeUnit.MINUTES.sleep(1);
            scheduler.shutdown();
            System.out.println("--------scheduler shutdown ! ------------");

    }
}
