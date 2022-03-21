package com.feife.mapreduce_case.topN;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Description: partition的泛型，分区是在map端数据溢出的时候，所以它的泛型就是Mapper的输出类型
 *
 * @ClassName: TPartitioner
 * @Author chengfei
 * @Date 2020/12/24 9:54
 **/
public class TPartitioner extends Partitioner<TKey, IntWritable> {

    @Override
    public int getPartition(TKey tKey, IntWritable intWritable, int numPartitions) {
        //numPartitions表示的是分区的数量，也就是reduce的数量
        return tKey.getMonth() % numPartitions;
    }
}
