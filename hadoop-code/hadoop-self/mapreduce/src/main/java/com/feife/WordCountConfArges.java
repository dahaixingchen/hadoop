package com.feife;

import com.feife.map.MyMapper;
import com.feife.reduce.MyReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * @ClassName: WordCount
 * @Author chengfei
 * @Date 2020/12/14 19:54
 * @Description: TODO
 **/
public class WordCountConfArges {
    public static void main(String[] args) throws Exception {


        Configuration configuration = new Configuration();

        //window异构平台一定需要这个配置的
        configuration.set("mapreduce.app-submission.cross-platform","true");

        //为了辨别是系统的参数，还是输入的main用的参数
        GenericOptionsParser parser = new GenericOptionsParser(configuration, args);
        String[] otherArgs = parser.getRemainingArgs();

        // Create a new Job
        Job job = Job.getInstance(configuration);
        job.setJar("E:\\sturdy\\bigData\\hdoop\\code\\hadoop\\mapreduce" +
                "\\target\\mapreduce-1.0-SNAPSHOT.jar");
        job.setJarByClass(WordCountConfArges.class);

        // Specify various job-specific parameters
        job.setJobName("myjob");


//     源码中提示的过时了    *     job.setInputPath(new Path("in"));
//     源码中提示的过时了    *     job.setOutputPath(new Path("out"));
        Path in = new Path(otherArgs[0]);
        TextInputFormat.addInputPath(job,in);

        Path out = new Path(otherArgs[1]);

        //判斷输出文件是否存在，如果存在就删除
        if (in.getFileSystem(configuration).exists(out)){
            out.getFileSystem(configuration).delete(out,true);
        }
        TextOutputFormat.setOutputPath(job,out);



        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Submit the job, then poll for progress until the job is complete
        job.waitForCompletion(true);

    }
}
