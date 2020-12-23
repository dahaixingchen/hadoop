package com.feifei.test;

import org.junit.Test;

import javax.swing.plaf.OptionPaneUI;
import java.util.ArrayList;
import java.util.Arrays;
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
}