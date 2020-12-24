package com.feife.mapreduce_case.topN;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Description: 自定义key ，必须要实现 writeableComparable接口
 *
 * @ClassName: TKey
 * @Author chengfei
 * @Date 2020/12/22 19:42
 **/
public class TKey implements WritableComparable<TKey> {
    private int year;
    private int month;
    private int day;
    private int wd;

    //进行map端的join操作的关键字
    private String joinId;


    @Override
    public int compareTo(TKey o) {
        //当当按照map端数据的分组来说，只需要年月相同就能得到最终的需要：找出每月气温最高的2天的气温
        //但是如果用这个进行后面的排序的话，reduce做的事情比较多，如果我们在map端溢出数据时排序的时候按照
        // ，年 月 且气温倒序的方式排序的话，reduce端就直接取数据了
        // ，所有我们还是得重新写分组排序的比较器的
        //这个比较器在我们业务中是用不到的
        int c1 = Integer.compare(this.year, o.getYear());
        if (c1 == 0){
            c1 = Integer.compare(this.month, o.getMonth());
            if (c1 == 0){
                c1 = Integer.compare(this.day,o.getDay());
            }
        }
        return c1;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(year);
        out.writeInt(month);
        out.writeInt(day);
        out.writeInt(wd);
        out.writeUTF(joinId);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
        this.wd = in.readInt();
        this.joinId = in.readUTF();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWd() {
        return wd;
    }

    public void setWd(int wd) {
        this.wd = wd;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }
}
