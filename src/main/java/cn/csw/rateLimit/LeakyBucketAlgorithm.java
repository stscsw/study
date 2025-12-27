package cn.csw.rateLimit;

/**
 * 漏桶限流算法
 * @author Lier
 * @since 2024/2/29 15:08
 */
public class LeakyBucketAlgorithm {
    private final long capacity;    // 桶的容量
    private final long rate;        // 漏桶出水速率
    private long water;             // 当前桶中的水量
    private long lastLeakTimestamp; // 上次漏水时间戳


    public LeakyBucketAlgorithm(long capacity, long rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.water = 0;
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        leaky();
        if (water + 1 <= capacity) {
            water++;
            return true;
        } else {
            return false;
        }
    }


    public void leaky(){
        long now = System.currentTimeMillis();
        long elapsedTime = now - lastLeakTimestamp;
        long leakedWater = elapsedTime * rate / 1000;
        if (leakedWater > 0) {
            water = Math.max(0, water - leakedWater);
            lastLeakTimestamp = now;
        }
    }
}
