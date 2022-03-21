package com.feifei.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;

/**
 * @ClassName: HdfsRead
 * @Author chengfei
 * @Date 2020/12/14 11:27
 * @Description: TODO
 **/
public class HdfsRead {

    Configuration conf = null;
    FileSystem fs = null;


    @Before
    public void connect() throws Exception {
        conf = new Configuration(true);
//        fs = FileSystem.get(conf);
        //基于集群
        fs = FileSystem.get(URI.create("hdfs://mycluster/"), conf, "god");
    }

    @Test
    public void mkdir() throws Exception {
        this.connect();
        Path path = new Path("/god");
        if (fs.exists(path)) {
            fs.delete(path, true);
        }
        fs.mkdirs(path);
    }

    @Test
    public void upload() throws Exception {
        File file = new File("E:\\（英语）钟叔资料\\钟叔·逻辑英语 第4季等多个文件\\逻辑英语 第五季\\钟叔·逻辑英语S05E01 - 极致美学作文修辞（上） .mp4");
//        File file = new File("./data.txt");
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FSDataOutputStream fsDataOutputStream = fs.create(new Path("/god/data.mp4"));
        IOUtils.copyBytes(inputStream, fsDataOutputStream, conf, true);
    }

    @Test
    public void blocks() throws Exception {
        Path path = new Path("/data/data.txt");
        BlockLocation[] blockLocations = fs.getFileBlockLocations(path, 0, fs.getFileStatus(path).getLen());

        for (BlockLocation blockLocation : blockLocations) {
            System.out.println(blockLocation);
            System.out.println(blockLocation.getHosts());
            System.out.println(blockLocation.getOffset());
            System.out.println(blockLocation.getLength());
            System.out.println(blockLocation.getTopologyPaths());
            System.out.println(blockLocation.getNames());
            System.out.println(blockLocation.getCachedHosts());
        }

        //        0,        1048576,        node04,node02  A
//        1048576,  540319,         node04,node03  B
        //计算向数据移动~！
        //其实用户和程序读取的是文件这个级别~！并不知道有块的概念~！
        FSDataInputStream in = fs.open(path);

        //计算向数据移动后，期望的是分治，只读取自己关心（通过seek实现），同时，具备距离的概念（优先和本地的DN获取数据--框架的默认机制）
        in.seek(52);
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());
        System.out.println(in.readByte());


    }


}
