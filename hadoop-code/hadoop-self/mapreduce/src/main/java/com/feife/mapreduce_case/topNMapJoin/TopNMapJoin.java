package com.feife.mapreduce_case.topNMapJoin;

import com.feife.mapreduce_case.topN.TGroupingComparator;
import com.feife.mapreduce_case.topN.TKey;
import com.feife.mapreduce_case.topN.TMapper;
import com.feife.mapreduce_case.topN.TPartitioner;
import com.feife.mapreduce_case.topN.TReduce;
import com.feife.mapreduce_case.topN.TSortComparator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import javax.xml.soap.Text;
import java.io.IOException;

/**
 * Description: 计算每月气温最高的两天,添加地区维度表，在map端进行join的操作，这个操作计算一定要在集群上跑
 * 因为它要把维度表分发到所有的map中
 * @Author chengfei
 * @Date 2020/12/22 19:26
 **/
public class TopNMapJoin {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //基础框架的准备
        Configuration conf = new Configuration();

        //基础框架的准备(跑jar包的时候输入个性化 的参数和-D对应的参数区别开)
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        conf.set("mapreduce.app-submission.cross-platform","true");

        //基础框架的准备
        Job job = Job.getInstance(conf);
        //基础框架的准备
        job.setJarByClass(TopNMapJoin.class);

        job.setJar("E:\\sturdy\\bigData\\hdoop\\code\\hadoop\\mapreduce\\target\\mapreduce-1.0-SNAPSHOT.jar");
        job.addCacheFile(new Path("/data/dict/dict.txt").toUri());
        job.setJobName("topNMapJoin");

        //MapReduce阶段的步骤
        //map端
        //0. maptask任务
        job.setMapperClass(TMapperMapJoin.class);

        //1.input 把数据读进来
        //一般情况下会有多个数据输入路径
        for (int i = 0; i < otherArgs.length; i++) {
            TextInputFormat.addInputPath(job,new Path(otherArgs[i]));
            if (i == otherArgs.length - 2){
                break;
            }
        }
        Path outputDir = new Path(otherArgs[otherArgs.length - 1]);
        if (outputDir.getFileSystem(conf).exists(outputDir)) outputDir.getFileSystem(conf).delete(outputDir,true);
        TextOutputFormat.setOutputPath(job, outputDir);

        //2.key 根据需要要自己定义key
        job.setMapOutputKeyClass(TKey.class);

        //3.partition 把数据进行分区，把每组数据打上P的分区号
        job.setPartitionerClass(TPartitioner.class);

        //4.sortComparator 数据进入环形缓冲区进行溢写，溢写出来的数据用快排的方式进行排序，这些不用管，在排序过程有个比较器，需要我们实现
        job.setSortComparatorClass(TSortComparator.class);

        //5.combine 在数据将要进行output的时候可以做一个combine这样一个优化步骤
//        job.setCombinerClass(TCombiner.class);

        //reduce端
        //6.shuffle阶段不需要管，框架自己按照每个分区把所有的数据拉取到一起，交给对应的reduce处理
        //7.groupingComparator 在reduce按照组（它是map端框架自动把相同的key放到个组的）为单位处理数据的时候，需要对这些组进行group分组
        job.setGroupingComparatorClass(TGroupingComparator.class);

        //8.reduceTask
        job.setReducerClass(TReduceMapJoin.class);

        //输出类型的设定
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //基础框架的准备
        job.waitForCompletion(true);
    }
}
