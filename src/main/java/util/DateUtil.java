package util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DateUtil {

    /**
     * 计算两个时间段的重复时间和相互的多余另一个的时间段
     *
     * @param timeSlot 两个时间段
     * @return Triple 若无对应时间段存null
     * left 重复的时间段
     * middle 第一个时间段多于第二个时间段的时间段(可能存在两个时间段)
     * right 第二个时间段多于第一个时间段的时间段(可能存在两个时间段)
     * 例如1
     * 传入
     * 第一个时间段
     * 2019-09-01 00：00：00  -  2020-09-01 00：00：00
     * 第二个时间段
     * 2019-01-01 00：00：00  -  2020-01-01 00：00：00
     * 返回
     * 重复的时间段
     * 2019-09-01 00：00：00 - 2020-01-01 00：00：00
     * 第一个时间段多于第二个时间段的时间段
     * 2020-01-01 00：00：00 - 2020-09-01 00：00：00
     * 第二个时间段多于第一个时间段的时间段
     * 2019-01-01 00：00：00 - 2019-09-01 00：00：00
     */
    public static Triple<Pair<Date, Date>, Pair<Pair<Date, Date>, Pair<Date, Date>>, Pair<Pair<Date, Date>, Pair<Date, Date>>>
    calculateRepetitionTime(Pair<Pair<Date, Date>, Pair<Date, Date>> timeSlot) {

        if((!timeSlot.getLeft().getLeft().before(timeSlot.getLeft().getRight())) ||
                (!timeSlot.getRight().getLeft().before(timeSlot.getRight().getRight()))){
            throw new IllegalArgumentException("开始时间必须小于结束时间");
        }
        //第一个时间段
        Date startDate = timeSlot.getLeft().getLeft();
        Date endDate = timeSlot.getLeft().getRight();
        //第二个时间段
        Date startTime = timeSlot.getRight().getLeft();
        Date endTime = timeSlot.getRight().getRight();

        MutableTriple<Pair<Date, Date>, Pair<Pair<Date, Date>, Pair<Date, Date>>, Pair<Pair<Date, Date>, Pair<Date, Date>>> triple = new MutableTriple();
        //有重合时间段情况
        if ((startDate.compareTo(startTime) <= 0 && endDate.compareTo(startTime) > 0)
                || (startDate.compareTo(startTime) >= 0 && endTime.compareTo(startDate) > 0)) {
            Date csDate = startDate.after(startTime) ? startDate : startTime;
            Date ceDate = endDate.before(endTime) ? endDate : endTime;
            triple.setLeft(new ImmutablePair<>(csDate, ceDate));
            if (startDate.compareTo(startTime) == 0 && endDate.compareTo(endTime) == 0) {// 两个时间段相等情况
                triple.setMiddle(new ImmutablePair<>(null, null));
                triple.setRight(new ImmutablePair<>(null, null));
            } else if (startDate.compareTo(startTime) <= 0 && endDate.compareTo(endTime) >= 0) {//第一个时间段包含第二个时间段
                if (startDate.equals(startTime)) {//开始时间相同
                    triple.setMiddle(new ImmutablePair<>(new ImmutablePair<>(endTime, endDate), null));
                } else if (endDate.equals(endTime)) {//结束时间相同
                    triple.setMiddle(new ImmutablePair<>(new ImmutablePair<>(startDate, startTime), null));
                } else {//开始时间和结束时间都不相同
                    triple.setMiddle(new ImmutablePair<>(new ImmutablePair<>(startDate, startTime), new ImmutablePair<>(endTime, endDate)));
                }
                triple.setRight(new ImmutablePair<>(null, null));
            } else if (startTime.compareTo(startDate) <= 0 && endTime.compareTo(endDate) >= 0) {//第二个时间段包含第一个时间段
                if (startDate.equals(startTime)) {//开始时间相同
                    triple.setRight(new ImmutablePair<>(new ImmutablePair<>(endDate, endTime), null));
                } else if (endDate.equals(endTime)) {//结束时间相同
                    triple.setRight(new ImmutablePair<>(new ImmutablePair<>(startTime, startDate), null));
                } else {//开始时间和结束时间都不相同
                    triple.setRight(new ImmutablePair<>(new ImmutablePair<>(startTime, startDate), new ImmutablePair<>(endDate, endTime)));
                }
                triple.setMiddle(new ImmutablePair<>(null, null));
            } else {//两个时间交叉
                if (startDate.before(csDate)) {//第一个时间段在交叉左边
                    triple.setMiddle(new ImmutablePair<>(new ImmutablePair<>(startDate, startTime), null));
                    triple.setRight(new ImmutablePair<>(new ImmutablePair<>(endDate, endTime), null));
                } else {//第一个时间段在交叉右边
                    triple.setMiddle(new ImmutablePair<>(new ImmutablePair<>(endTime, endDate), null));
                    triple.setRight(new ImmutablePair<>(new ImmutablePair<>(startTime, startDate), null));
                }
            }
        } else {//无交集情况
            triple.setLeft(null);
            triple.setMiddle(new ImmutablePair<>(new ImmutablePair<>(startDate, endDate), null));
            triple.setRight(new ImmutablePair<>(new ImmutablePair<>(startTime, endTime), null));

        }
        return triple;
    }

    @Test
    public static void testCalculateRepetitionTime(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = df.parse("2017-01-01 00:00:00");
        Date endDate = df.parse("2022-01-01 00:00:00");

        Date startTime = df.parse("2022-01-01 00:00:00");
        Date endTime = df.parse("2023-01-01 00:00:00");
        Triple<Pair<Date, Date>, Pair<Pair<Date, Date>, Pair<Date, Date>>, Pair<Pair<Date, Date>, Pair<Date, Date>>> result
                = calculateRepetitionTime(new ImmutablePair<>(new ImmutablePair<>(startDate, endDate), new ImmutablePair<>(startTime, endTime)));

        System.out.println("重复时间段");
        if (Objects.nonNull(result.getLeft())) {
            System.out.println("开始时间:" + df.format(result.getLeft().getLeft()) + "----" + "结束时间" + df.format(result.getLeft().getRight()));
        }
        System.out.println("第一段时间多余第二段时间");
        Pair<Pair<Date, Date>, Pair<Date, Date>> middle = result.getMiddle();
        if (Objects.nonNull(middle.getLeft())) {
            System.out.println("开始时间:" + df.format(middle.getLeft().getLeft()) + "----" + "结束时间" + df.format(middle.getLeft().getRight()));
        }
        if (Objects.nonNull(middle.getRight())) {
            System.out.println("开始时间:" + df.format(middle.getRight().getLeft()) + "----" + "结束时间" + df.format(middle.getRight().getRight()));
        }
        System.out.println("第二段时间多余第一段时间");
        Pair<Pair<Date, Date>, Pair<Date, Date>> right = result.getRight();
        if (Objects.nonNull(right.getLeft())) {
            System.out.println("开始时间:" + df.format(right.getLeft().getLeft()) + "----" + "结束时间" + df.format(right.getLeft().getRight()));
        }
        if (Objects.nonNull(right.getRight())) {
            System.out.println("开始时间:" + df.format(right.getRight().getLeft()) + "----" + "结束时间" + df.format(right.getRight().getRight()));
        }

    }

    @Test
    public void test2() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date startDateA = df.parse("2019-09-01 00:00:00");
        Date endDateA = df.parse("2019-09-10 00:00:00");
        Date startDateB = df.parse("2019-09-11 00:00:00");
        Date endDateB = df.parse("2019-09-21 00:00:00");
        Date startDateC = df.parse("2019-09-21 00:00:00");
        Date endDateC = df.parse("2019-11-21 00:00:00");


        Date startTimeA = df.parse("2019-09-01 00:00:00");
        Date endTimeA = df.parse("2019-09-12 00:00:00");
        Date startTimeB = df.parse("2019-09-15 00:00:00");
        Date endTimeB = df.parse("2019-09-18 00:00:00");
        Date startTimeC = df.parse("2019-09-26 00:00:00");
        Date endTimeC = df.parse("2019-11-20 00:00:00");


        List<Pair<Date, Date>> a = new ArrayList<>();
        List<Pair<Date, Date>> b = new ArrayList<>();
        a.add(new ImmutablePair<>(startDateA, endDateA));
        a.add(new ImmutablePair<>(startDateC, endDateC));
        a.add(new ImmutablePair<>(startDateB, endDateB));


        b.add(new ImmutablePair<>(startTimeA, endTimeA));
        b.add(new ImmutablePair<>(startTimeB, endTimeB));
        b.add(new ImmutablePair<>(startTimeC, endTimeC));

        int count = 1;

        for (int i = 0; i < a.size(); i++) {
            boolean inner_loop_end = false;
            for (int j = 0; j < b.size(); j++) {
                Triple<Pair<Date, Date>, Pair<Pair<Date, Date>, Pair<Date, Date>>, Pair<Pair<Date, Date>, Pair<Date, Date>>>
                        dateResult = DateUtil.calculateRepetitionTime(new ImmutablePair<>(a.get(i), b.get(j)));
                Pair<Pair<Date, Date>, Pair<Date, Date>> middle = dateResult.getMiddle();
                if (Objects.nonNull(middle.getLeft())) {
                    if (middle.getLeft().getLeft().equals(a.get(i).getLeft()) && middle.getLeft().getRight().equals(a.get(i).getRight()))
                        continue;
                    a.add(middle.getLeft());
                }
                a.remove(i);
                i--;
                inner_loop_end = true;
                if (Objects.nonNull(middle.getRight())) {
                    a.add(middle.getRight());
                }
                if (inner_loop_end)
                    break;
            }
        }
        System.out.println("最终结果");
        a.forEach(item -> {
            System.out.println(df.format(item.getLeft()));
            System.out.println(df.format(item.getRight()));
            System.out.println("------------------------");
        });


    }

}
