package com.feife.mapreduce_case.topN;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Description:
 *
 * @ClassName: TMapper
 * @Author chengfei
 * @Date 2020/12/23 20:16
 **/
public class TMapper extends Mapper<LongWritable, Text,TKey, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        super.map(key, value, context);
    }
}
