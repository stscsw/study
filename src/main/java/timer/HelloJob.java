package timer;

import org.quartz.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @PersistJobDataAfterExecution：将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常）， 更新JobDetail中JobDataMap的数据，使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据
 * @DisallowConcurrentExecution：将该注解加到job类上，告诉Quartz不要并发地执行同一个job定义（这里指特定的job类）的多个实例
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class HelloJob implements Job {

    private String jobSays;
    private float myFloatValue;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        //JobDetail中的JobDataMap和Trigger中的JobDataMap的并集，但是如果存在相同的数据，则后者会覆盖前者的值
//        JobDataMap dataMap = context.getMergedJobDataMap();
//        int count = context.getJobDetail().getJobDataMap().get("count") == null ? 0 : (Integer) context.getJobDetail().getJobDataMap().get("count");
//        System.out.println(count);
//        context.getJobDetail().getJobDataMap().put("count", count + 1);
        //是否持久化任务
//        boolean durable = context.getJobDetail().isDurable();
//        System.out.println("是否持久化任务" + durable);
        //是否可恢复任务
//        boolean b = context.getJobDetail().requestsRecovery();
//        boolean recovering = context.isRecovering();
//        System.out.println("是否可恢复任务" +recovering);


        JobKey key = context.getJobDetail().getKey();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()) + "hello world      " + key + "           " + jobSays + "     " + myFloatValue);
    }

    public String getJobSays() {
        return jobSays;
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public float getMyFloatValue() {
        return myFloatValue;
    }

    public void setMyFloatValue(float myFloatValue) {
        this.myFloatValue = myFloatValue;
    }
}
