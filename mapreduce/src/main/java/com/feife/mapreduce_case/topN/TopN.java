package com.feife.mapreduce_case.topN;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

/**
 * Description: 计算每月气温最高的两天
 * @Author chengfei
 * @Date 2020/12/22 19:26
 **/
public class TopN {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();

        //1.准备环境
        conf.set("mapreduce.framework.name","local");
        conf.set("mapreduce.app-submission.cross-platform","true");

        Job job = Job.getInstance(conf);

        job.setJar("E:\\sturdy\\bigData\\hdoop\\code\\hadoop\\mapreduce" +
                "\\target\\mapreduce-1.0-SNAPSHOT.jar");
        job.setJarByClass(TopN.class);
        job.setJobName("my topN");
        //2.MapReduce的步骤
    }
}
