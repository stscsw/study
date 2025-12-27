package cn.csw.util;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author Lier
 * @since 2023/7/17 15:09
 */
public final class ULID {


    /**
     * 此ULID的最高有效64位。 含时间戳48位和16位随机数
     */
    private final long msb;

    /**
     * 此ULID的最低有效位64位。
     */
    private final long lsb;  //后64位随机数

    public ULID(long msb, long lsb) {
        this.msb = msb;
        this.lsb = lsb;
    }

    public ULID(long timestamp, byte[] randomness) {

//        if ((timestamp & TIMESTAMP_MASK) != 0) {
//            throw new IllegalArgumentException("Invalid timestamp");
//        }
//        if (Objects.isNull(randomness) || RANDOMNESS_BYTE_LEN != randomness.length) {
//            throw new IllegalArgumentException("Invalid randomness");
//        }
//        long msb = 0;
//        long lsb = 0;
//        // 时间戳左移16位，低位补零准备填入部分随机数位，即16_bit_uint_random
//        msb |= timestamp << 16;
//        // randomness[0]左移0位填充到16_bit_uint_random的高8位，randomness[1]填充到16_bit_uint_random的低8位
//        msb |= (long) (randomness[0x0] & 0xff) << 8;
//        // randomness[1]填充到16_bit_uint_random的低8位
//        msb |= randomness[0x1] & 0xff;
//        // randomness[2] ~ randomness[9]填充到剩余的bit_uint_random中，要左移相应的位
//        lsb |= (long) (randomness[0x2] & 0xff) << 56;
//        lsb |= (long) (randomness[0x3] & 0xff) << 48;
//        lsb |= (long) (randomness[0x4] & 0xff) << 40;
//        lsb |= (long) (randomness[0x5] & 0xff) << 32;
//        lsb |= (long) (randomness[0x6] & 0xff) << 24;
//        lsb |= (long) (randomness[0x7] & 0xff) << 16;
//        lsb |= (long) (randomness[0x8] & 0xff) << 8;
//        lsb |= (randomness[0x9] & 0xff);

        //上面注释的简便算法
        long msb = 0;
        long lsb = 0;
        byte[] data = new byte[16];
        //高位放入的顺序从索引0开始
        byte[] ts = ByteBuffer.allocate(8).putLong(0, timestamp << 16).array();
        System.arraycopy(ts, 0, data, 0, 6);
        System.arraycopy(randomness, 0, data, 6, 10);
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        this.msb = msb;
        this.lsb = lsb;
    }

    public long getMostSignificantBits() {
        return this.msb;
    }

    public long getLeastSignificantBits() {
        return this.lsb;
    }

    // 静态工厂方法，由UUID实例生成ULID实例
    public static ULID fromUUID(UUID uuid) {
        return new ULID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }

    // 实例方法，当前ULID实例转换为UUID实例
    public UUID toUUID() {
        return new UUID(this.msb, this.lsb);
    }

    /**
     * Default alphabet of ULID
     */
    private static final char[] DEFAULT_ALPHABET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
    /**
     * Default alphabet mask
     */
    private static final int DEFAULT_ALPHABET_MASK = 0b11111;
    /**
     * Character num of ULID
     */
    private static final int ULID_CHAR_LEN = 0x1a;

    @Override
    public String toString() {
        return toCanonicalString(DEFAULT_ALPHABET);
    }

    public String toCanonicalString(char[] alphabet) {
        char[] chars = new char[ULID_CHAR_LEN];
        long timestamp = this.msb >> 16;
        // 第一部分随机数取msb的低16位+lsb的高24位，这里(msb & 0xffff) << 24作为第一部分随机数的高16位，所以要左移24位
        long randMost = ((this.msb & 0xffffL) << 24) | (this.lsb >>> 40);
        // 第二部分随机数取lsb的低40位，0xffffffffffL是2^40-1
        long randLeast = (this.lsb & 0xffffffffffL);
        // 接着每个部分的偏移量和DEFAULT_ALPHABET_MASK(31)做一次或运算就行，就是char[index] = alphabet[(part >> (step * index)) & 31]
        chars[0x00] = alphabet[(int) (timestamp >>> 45 & DEFAULT_ALPHABET_MASK)];
        chars[0x01] = alphabet[(int) (timestamp >>> 40 & DEFAULT_ALPHABET_MASK)];
        chars[0x02] = alphabet[(int) (timestamp >>> 35 & DEFAULT_ALPHABET_MASK)];
        chars[0x03] = alphabet[(int) (timestamp >>> 30 & DEFAULT_ALPHABET_MASK)];
        chars[0x04] = alphabet[(int) (timestamp >>> 25 & DEFAULT_ALPHABET_MASK)];
        chars[0x05] = alphabet[(int) (timestamp >>> 20 & DEFAULT_ALPHABET_MASK)];
        chars[0x06] = alphabet[(int) (timestamp >>> 15 & DEFAULT_ALPHABET_MASK)];
        chars[0x07] = alphabet[(int) (timestamp >>> 10 & DEFAULT_ALPHABET_MASK)];
        chars[0x08] = alphabet[(int) (timestamp >>> 5 & DEFAULT_ALPHABET_MASK)];
        chars[0x09] = alphabet[(int) (timestamp & DEFAULT_ALPHABET_MASK)];
        chars[0x0a] = alphabet[(int) (randMost >>> 35 & DEFAULT_ALPHABET_MASK)];
        chars[0x0b] = alphabet[(int) (randMost >>> 30 & DEFAULT_ALPHABET_MASK)];
        chars[0x0c] = alphabet[(int) (randMost >>> 25 & DEFAULT_ALPHABET_MASK)];
        chars[0x0d] = alphabet[(int) (randMost >>> 20 & DEFAULT_ALPHABET_MASK)];
        chars[0x0e] = alphabet[(int) (randMost >>> 15 & DEFAULT_ALPHABET_MASK)];
        chars[0x0f] = alphabet[(int) (randMost >>> 10 & DEFAULT_ALPHABET_MASK)];
        chars[0x10] = alphabet[(int) (randMost >>> 5 & DEFAULT_ALPHABET_MASK)];
        chars[0x11] = alphabet[(int) (randMost & DEFAULT_ALPHABET_MASK)];
        chars[0x12] = alphabet[(int) (randLeast >>> 35 & DEFAULT_ALPHABET_MASK)];
        chars[0x13] = alphabet[(int) (randLeast >>> 30 & DEFAULT_ALPHABET_MASK)];
        chars[0x14] = alphabet[(int) (randLeast >>> 25 & DEFAULT_ALPHABET_MASK)];
        chars[0x15] = alphabet[(int) (randLeast >>> 20 & DEFAULT_ALPHABET_MASK)];
        chars[0x16] = alphabet[(int) (randLeast >>> 15 & DEFAULT_ALPHABET_MASK)];
        chars[0x17] = alphabet[(int) (randLeast >>> 10 & DEFAULT_ALPHABET_MASK)];
        chars[0x18] = alphabet[(int) (randLeast >>> 5 & DEFAULT_ALPHABET_MASK)];
        chars[0x19] = alphabet[(int) (randLeast & DEFAULT_ALPHABET_MASK)];
        return new String(chars);
    }
}
