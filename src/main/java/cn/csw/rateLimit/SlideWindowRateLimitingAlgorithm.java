package cn.csw.rateLimit;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 滑动窗口限流算法
 *
 * @author Lier
 * @since 2024/2/29 13:46
 */
public class SlideWindowRateLimitingAlgorithm {

    /**
     * 单位时间划分的小周期（单位时间是1分钟，10s一个小格子窗口，一共6个格子）
     */
    private int SUB_CYCLE = 10;

    /**
     * 每分钟限流请求数
     */
    private int thresholdPerMin = 100;

    /**
     * 计数器, k-为当前窗口的开始时间值秒，value为当前窗口的计数
     */
    private final TreeMap<Long, Integer> counters = new TreeMap<>();


    /**
     * 滑动窗口时间算法实现
     * @return
     */
    public boolean slidingWindowsTryAcquire() {
        long currentWindowTime =  LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) / SUB_CYCLE * SUB_CYCLE;
        int currentWindowNum = countCurrentWindow(currentWindowTime);

        //超过阀值限流
        if (currentWindowNum >= thresholdPerMin) {
            return false;
        }
        //计数器+1
        counters.compute(currentWindowTime, (_, value) -> Objects.isNull(value) ? 1 : value + 1);
        return true;
    }

    private int countCurrentWindow(long currentWindowTime) {
        //计算窗口开始位置
        long startTime = currentWindowTime - (long) SUB_CYCLE * (60 / SUB_CYCLE - 1);
        int count = 0;
        Iterator<Map.Entry<Long, Integer>> iterator = counters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Integer> next = iterator.next();
            // 删除无效过期的子窗口计数器
            if (next.getValue() < startTime) {
                iterator.remove();
            } else {
                //累加当前窗口的所有计数器之和
                count = count + next.getValue();
            }
        }
        return count;
    }


}
