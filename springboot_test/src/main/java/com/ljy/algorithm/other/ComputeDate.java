package com.ljy.algorithm.other;/**
 * Project: springboot_test
 * Package: com.ljy.algorithm.other
 * Version: 1.0
 * Created by ljy on 2022-2-10 9:17
 */

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName ComputeDate
 * @Author: ljy on 2022-2-10 9:17
 * @Version: 1.0
 * @Description:日期计算
 * 将日期加1，这通过cal1.add(Calendar.DATE,1)就可以实现了
 * DAY_OF_MONTH的主要作用是cal.get(DAY_OF_MONTH)，用来获得这一天在是这个月的第多少天
 * Calendar.DAY_OF_YEAR的主要作用是cal.get(DAY_OF_YEAR)，用来获得这一天在是这个年的第多少天。
 * 同样，还有DAY_OF_WEEK，用来获得当前日期是一周的第几天
 */
public class ComputeDate {

    @Test
    public void beforeDate(){
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1);
        Date date = ca.getTime();
        String dateStr = sdf.format(date);
        System.out.println(dateStr);
    }
}
