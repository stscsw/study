package cn.csw.util;


import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ttttttttttrrrrrrrrrrrrrrrr
 * where
 * t is Timestamp (10 characters)
 * r is Randomness (16 characters)
 *
 * @author Lier
 * @since 2023/7/17 10:00
 */
public class ULIDGenerator {

    private static final long EPOCH = Instant.parse("2021-01-01T00:00:00Z").toEpochMilli();
    private static final char[] ENCODING = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
    private static final int ENCODING_LENGTH = ENCODING.length;
    private static final int TIMESTAMP_LENGTH = 10;
    private static final int RANDOMNESS_LENGTH = 16;
    private static final AtomicLong LAST_TIMESTAMP_MS = new AtomicLong(Long.MIN_VALUE);
    private static final SecureRandom RANDOM = new SecureRandom();


    public static void main(String[] args) {
        // 常规
//        Ulid ulid1 = UlidCreator.getUlid();
//        System.out.println(ulid1);
        // 单调排序
//        Ulid ulid2 = UlidCreator.getMonotonicUlid();
//        System.out.println(ulid2);
        String ULID = generateULID();
        System.out.println("ULID:" + ULID);
        System.out.println("UUID:" + UUID.randomUUID());
    }

    public static String generateULID() {
        long timestampMs = getCurrentTimestampMs();
        StringBuilder sb = new StringBuilder(TIMESTAMP_LENGTH + RANDOMNESS_LENGTH);
        sb.append(encodeLong(timestampMs - EPOCH, TIMESTAMP_LENGTH)); // 时间戳
        sb.append(encodeLong(generateRandomness(), RANDOMNESS_LENGTH)); // 随机数
        return sb.toString();
    }

    private static long getCurrentTimestampMs() {
        long now = Instant.now().toEpochMilli();
//        return now;
        while (true) {
            long lastTimestampMs = LAST_TIMESTAMP_MS.get();
            if (now > lastTimestampMs) {
                if (LAST_TIMESTAMP_MS.compareAndSet(lastTimestampMs, now)) {
                    return now;
                }
            } else {
                return LAST_TIMESTAMP_MS.incrementAndGet();
            }
        }
    }

    private static long generateRandomness() {
        byte[] randomness = new byte[8];
        RANDOM.nextBytes(randomness);
        return bytesToLong(randomness);
    }

    private static StringBuilder encodeLong(long value, int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        stringBuilder.setLength(length);
        for (int i = length - 1; i >= 0; i--) {
            stringBuilder.setCharAt(i, ENCODING[(int) (value % ENCODING_LENGTH)]);
            value /= ENCODING_LENGTH;
        }
        return stringBuilder;
    }

    private static long bytesToLong(byte[] bytes) {
        long value = 0;
        for (byte b : bytes) {
            value = (value << 8) | (b & 0xff);
        }
        return Math.abs(value);
    }

}
