package cn.csw.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lier
 * @since 2023/4/10 14:15
 */
public class CommonUtils {

    /**
     * 分割集合
     *
     * @param source 原集合
     * @param n      拆分成的个数
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        int size = source.size();
        int remainder = size % n;//剩余量
        int number = size / n;//每个集合基本成员数
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> children;
            if (remainder > 0) {
                children = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                children = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(children);
        }
        return result;
    }



}
