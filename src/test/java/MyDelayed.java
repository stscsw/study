import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Lier
 * @since 2022/11/24 13:57
 */
public class MyDelayed implements Delayed {

    private Long createTime = System.currentTimeMillis();
    private String name;
    private Long delayTime;

    public MyDelayed(String name, Long delayTime) {
        this.name = name;
        this.delayTime = delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(createTime + delayTime
                - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Objects.requireNonNull(o);
        long delay1 = this.getDelay(TimeUnit.MILLISECONDS);
        long delay2 = o.getDelay(TimeUnit.MILLISECONDS);
        return Long.compare(delay1, delay2);
    }

    @Override
    public String toString() {
        return "MyDelayed{" +
                "name='" + name + '\'' +
                ", delayTime=" + delayTime +
                '}';
    }
}
