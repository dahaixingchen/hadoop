package com.feifei.test;

import org.junit.Test;

import javax.swing.plaf.OptionPaneUI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: TestNum
 * @Author chengfei
 * @Date 2020/12/20 18:25
 * @Description: TODO
 **/
public class TestNum {
    @Test
    public void test(){
        final int sortmb = 100;
        System.out.println(sortmb);
        int maxMemUsage = sortmb << 20;
        System.out.println(maxMemUsage);
    }

    @Test
    public void test1(){
        List<String> asList = Arrays.asList("sdfa", "fsd", "fsdgge", "op", "[");
        List<String> arrayList = new ArrayList<>();
        arrayList = asList;

        for (int i = 0; i < asList.size(); i++) {
            if (i == asList.size() -1){
                break;
            }
            System.out.println(asList.get(i));
        }
    }

    @Test
    public void test2() throws ParseException {
        String str = "2019-6-1 22:22:22";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date parse = sdf.parse(str);
//        System.out.println(parse);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);

        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

    }
}