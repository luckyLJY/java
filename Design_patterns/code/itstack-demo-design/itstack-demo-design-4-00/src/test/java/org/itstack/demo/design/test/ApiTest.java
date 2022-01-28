package org.itstack.demo.design.test;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.test
 * Version: 1.0
 * Created by ljy on 2022-1-27 10:45
 */

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ApiTest
 * @Author: ljy on 2022-1-27 10:45
 * @Version: 1.0
 * @Description:
 */
public class ApiTest {
    @Test
    public void test_clone() throws CloneNotSupportedException {
        ArrayList<String> l01 = new ArrayList<String>();
        l01.add("xxx");
        l01.add("yyy");

        ArrayList<String> l02 = (ArrayList<String>) l01.clone();
        l02.add("zzz");

        System.out.println(JSON.toJSONString(l01));
        System.out.println(JSON.toJSONString(l02));

        l01.remove("xxx");

        System.out.println(JSON.toJSONString(l01));
        System.out.println(JSON.toJSONString(l02));

    }


}
