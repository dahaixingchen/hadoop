package com.feife.mapreduce_case.topNMapJoin;

import com.feife.mapreduce_case.topN.TKey;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Description:
 *
 * @ClassName: TMapper
 * @Author chengfei
 * @Date 2020/12/23 20:16
 **/
public class TMapperMapJoin extends Mapper<LongWritable, Text,TKey, IntWritable> {

    //因为每条数据都会调用这个map方法，为了减少gc，应该在外面设置key和value
    TKey mkey = new TKey();
    IntWritable mval = new IntWritable();

    //装维度表的数据映射
    public Map<String,String> dict = new HashMap<String,String>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] files = context.getCacheFiles();
        Path path = new Path(files[0].getPath());
        BufferedReader reader = new BufferedReader(new FileReader(new File(path.getName())));

        String line = reader.readLine();
        //文件中如何去掉空行
        while (line != null){
            String[] split = line.split("\t");
            dict.put(split[0],split[1]);
            line = reader.readLine();
        }
    }

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
            //替换map读到的码值，替换成具体的值
            mkey.setJoinId(dict.get(split[1]));
            mkey.setWd(Integer.parseInt(split[split.length - 1]));

            context.write(mkey, mval);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
