package base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class demo {
    public static void main(String[] args) {
        String str = "Wed Jan 30 15:15:28 CST 2019";
        Date date = parse(str, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        System.out.println(date);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String dd = format.format(date);
        System.out.println("dd>>>>  " + dd);

        System.out.println(format(date,"HH:mm:ss",Locale.US));
    }

    public static Date parse(String str, String pattern, Locale locale) {
        if (str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(Date date, String pattern, Locale locale) {
        if (date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern, locale).format(date);
    }
}