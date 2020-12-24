package com.feife.mapreduce_case.topN;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Description: 这里注意不是，implements RawComparator（类型的时候实现接口），而是 extends WritableComparator（比较的时候继承类）
 * 通过看源码中WriteableComparator方法实现，可以知道，它的3个对应的compar方法
 * 其中：public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) 是比较模板，我们只需要实现它方法里面调用的
 * public int compare(WritableComparable a, WritableComparable b) 方法就可以了
 * 但是在这之前需要注意的是，我们要初始化父类的比较对象
 *
 * @ClassName: TGroupingComparator
 * @Author chengfei
 * @Date 2020/12/24 9:55
 **/
public class TGroupingComparator extends WritableComparator {
    public TGroupingComparator(){
        super(TKey.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        //类型强转，父类向子类的强转，方便调用子类的方法
        TKey k1 = (TKey)a;
        TKey k2 = (TKey)b;
        //比较的时候是要按照年月，的方式分组
        int c1 = Integer.compare(k1.getYear(), k2.getYear());
        if (c1 == 0){
            c1 = Integer.compare(k1.getMonth(),k2.getMonth());
        }
        return c1;
    }
}
