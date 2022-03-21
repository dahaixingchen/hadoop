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

/**
 * @ClassName: WordCount
 * @Author chengfei
 * @Date 2020/12/14 19:54
 * @Description: TODO daf df
 **/
public class WordCountWindowLocal {

    /**
     * hadoop 如果要想本地运行，必须要做三步动作
     *  1. 一定要在window上安装hadoop---拿到线上的hadoop-2.6.0.tar.gz的 jar包（或直接把服务器上的hadoop-2.6.0文件直接拿下来也可以）
     *      ，把它解压到一个没有中文和空格的目录中
     *  2. 配置HADOOP_HOME这样的环境变量，它的value值，就是你上面的文件的位置
     *  3. 最后把hadoop.dll这样一个文件放到 C:\Windows\System32  目录下

     */
    public static void main(String[] args) throws Exception {


        Configuration configuration = new Configuration();

        //window异构平台一定需要这个配置的
        configuration.set("mapreduce.app-submission.cross-platform","true");
        //设置本地运行
        configuration.set("mapreduce.framework.name","local");
        configuration.set("mapreduce.input.fileinputformat.split.maxsize","1024");


        // Create a new Job
        Job job = Job.getInstance(configuration);
        job.setJarByClass(WordCountWindowLocal.class);

        // Specify various job-specific parameters
        job.setJobName("myjob");


//     源码中提示的过时了    *     job.setInputPath(new Path("in"));
//     源码中提示的过时了    *     job.setOutputPath(new Path("out"));
        Path in = new Path("/data/data.txt");
        TextInputFormat.addInputPath(job,in);

        Path out = new Path("/data/out4");

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
