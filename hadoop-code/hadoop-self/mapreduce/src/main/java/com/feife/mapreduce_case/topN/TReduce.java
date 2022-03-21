package com.feife.mapreduce_case.topN;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * Description: 把分组后的数据取日期不同的最高的两天的气温
 *
 * @ClassName: TReduce
 * @Author chengfei
 * @Date 2020/12/24 9:55
 **/
public class TReduce extends Reducer<TKey, IntWritable, Text,IntWritable> {
    private Text rKey = new Text();
    private IntWritable rval = new IntWritable();

    /**
     * 2019-6-1 22:22:22	1	39
     * 2019-6-1 22:22:22	1	38
     * 2019-6-2 22:22:22	2	31
     * 到了reduce环节就是如上的数据，按照年月，且温度倒序的数据，
     * reduce方法需要拿着这样一组数据进行操作
     */
    @Override
    protected void reduce(TKey key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<IntWritable> ite = values.iterator();

        int flg = 0 ;
        int day = 0;
        //这里注意对value进行迭代，key也是跟着变化的，相当于同时也能拿到key的数据，
        //因为从源码汇总可以看到ite.next -->调用的是context.netKey--->对key和value更新值
        while (ite.hasNext()){
            //只为了指针往下一行走
            ite.next();
            if(flg == 0){
                rKey.set(key.getYear()+"-"+key.getMonth()+"-"+key.getDay());
                rval.set(key.getWd());
                context.write(rKey,rval);
                flg++;
                day = key.getDay();

            }

            if(flg != 0 &&  day != key.getDay()){
                rKey.set(key.getYear()+"-"+key.getMonth()+"-"+key.getDay());
                rval.set(key.getWd());
                context.write(rKey,rval);
                break;
            }
        }

    }
}
