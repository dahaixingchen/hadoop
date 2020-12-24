package com.feife.mapreduce_case.topN;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 *
 * @ClassName: TMapper
 * @Author chengfei
 * @Date 2020/12/23 20:16
 **/
public class TMapper extends Mapper<LongWritable, Text,TKey, IntWritable> {

    //因为每条数据都会调用这个map方法，为了减少gc，应该在外面设置key和value
    TKey mkey = new TKey();
    IntWritable mval = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // map过来的数据:  2019-6-1 22:22:22	1	31
        String[] split = value.toString().split("\t");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(split[0]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            //组织key
            mkey.setYear(cal.get(Calendar.YEAR));
            mkey.setMonth(cal.get(Calendar.MONTH) + 1);
            mkey.setDay(cal.get(Calendar.DATE));
            mkey.setWd(Integer.parseInt(split[split.length - 1]));

            context.write(mkey, mval);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
