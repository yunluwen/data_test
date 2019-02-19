package base;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 获取昨天的开始和结束时间戳
 */
public class TimeTest {

    private static long getYesterdayEndTime(){
        Calendar yesterdayEnd=Calendar.getInstance();
        yesterdayEnd.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        yesterdayEnd.add(Calendar.DATE,-1);
        yesterdayEnd.set(Calendar.HOUR_OF_DAY, 23);
        yesterdayEnd.set(Calendar.MINUTE, 59);
        yesterdayEnd.set(Calendar.SECOND, 59);
        yesterdayEnd.set(Calendar.MILLISECOND, 999);
        Date time=yesterdayEnd.getTime();
        System.out.println("昨天结束时间： "+yesterdayEnd.getTime());
        String timestamp = String.valueOf(time.getTime()/1000);
        return Long.valueOf(timestamp);
    }
    private static long getYesterdayStartTime(){
        Calendar yesterdayStart=Calendar.getInstance();
        yesterdayStart.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        yesterdayStart.add(Calendar.DATE,-1);
        yesterdayStart.set(Calendar.HOUR_OF_DAY, 0);
        yesterdayStart.set(Calendar.MINUTE, 0);
        yesterdayStart.set(Calendar.SECOND, 0);
        yesterdayStart.set(Calendar.MILLISECOND, 0);
        Date time=yesterdayStart.getTime();
        System.out.println("昨天开始时间： "+yesterdayStart.getTime());
        String timestamp = String.valueOf(time.getTime()/1000);
        return Long.valueOf(timestamp);
    }

    public static void main(String[] args) {
        System.out.println(getYesterdayStartTime());
        System.out.println(getYesterdayEndTime());
    }
}
