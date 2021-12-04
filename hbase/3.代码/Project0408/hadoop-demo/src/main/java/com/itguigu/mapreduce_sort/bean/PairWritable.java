package com.itguigu.mapreduce_sort.bean;/**
 * Project: Project0408
 * Package: com.itguigu.mapreduce_sort.bean
 * Version: 1.0
 * Created by ljy on 2021-12-4 17:52
 */

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @ClassName PariWritable
 * @Author: ljy on 2021-12-4 17:52
 * @Version: 1.0
 * @Description: 排序实体类
 * 1.实现WritableComparable接口<ParirWritable>
 * 2，重写方法
 *      compareTo：实现排序规则
 *      write:实现序列化
 *      readFilelds：实现反序列化
 * 3.添加字段
 *      String first
 *      int second
 * 4.compareTo：实现排序规则
 *      需求：
 *          1.先按first排序 compareTo  等，0； 大于,>0;小于,<0;
 *          2.再按second排序
 * 5.write:实现序列化属性
 *      writeUTF
 *      writeInt
 * 6.readFilelds：实现反序列化
 *      readInputUTF
 *      readInt
 */
public class PairWritable implements WritableComparable<PairWritable>{
    private String first;
    private int second;

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    @Override
    public String toString() {
        return "PairWritable{" +
                "first='" + first + '\'' +
                ", second=" + second +
                '}';
    }

    /**
     *          1.先按first排序 compareTo  等，0； 大于,>0;小于,<0;
     *          2.再按second排序
     * @param o
     * @return
     */
    public int compareTo(PairWritable o) {
        int result = this.first.compareTo(o.first);
        if (result == 0){
            return this.second - o.second;
        }
        return result;
    }

    public void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeUTF(first);
            dataOutput.writeInt(second);
    }

    public void readFields(DataInput dataInput) throws IOException {
        this.first = dataInput.readUTF();
        this.second = dataInput.readInt();
    }
}
