package timer;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;


public class QuartzTest {

    public static void main(String[] args) {


        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            scheduler.start();

            /*
                次当scheduler执行job时，在调用其execute(…)方法之前会创建该类的一个新的实例；
                执行完毕，对该实例的引用就被丢弃了，实例会被垃圾回收
             */
            JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .withIdentity("myJob", "group1")
                    .usingJobData("jobSays", "job Hello World!")
                    .usingJobData("myFloatValue", 3.141f)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .usingJobData("jobSays","trigger Hello World!")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)
                            .repeatForever())
                    .build();


            scheduler.scheduleJob(job, trigger);
            TimeUnit.SECONDS.sleep(10);
            scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
