package common;

import sun.misc.Unsafe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

    public static void main(String[] args) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        String s = "2016-02-29";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
        calendar.setTime(date);
        System.out.println(calendar.getTime());
        calendar.add(calendar.YEAR, -1);//把日期往后增加一年.整数往后推,负数往前移动
        System.out.println(calendar.getTime());

        Unsafe unsafe = Unsafe.getUnsafe();
    }
}
