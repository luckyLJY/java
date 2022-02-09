package org.itstack.demo.design.test;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.test
 * Version: 1.0
 * Created by ljy on 2022-2-9 16:47
 */

import com.alibaba.fastjson.JSON;
import org.itstack.demo.design.cuisine.MQAdapter;
import org.itstack.demo.design.cuisine.OrderAdapterService;
import org.itstack.demo.design.cuisine.RebateInfo;
import org.itstack.demo.design.cuisine.impl.InsideOrderService;
import org.itstack.demo.design.cuisine.impl.POPOrderAdapterServiceImpl;
import org.itstack.demo.design.mq.CreateAccount;
import org.itstack.demo.design.mq.OrderMq;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName ApiTest
 * @Author: ljy on 2022-2-9 16:47
 * @Version: 1.0
 * @Description:测试MQ适配器
 */
public class ApiTest {
    @Test
    public void test_MQAdapter() throws NoSuchMethodException,IllegalAccessException, InvocationTargetException, ParseException{

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = s.parse("2020-06-01 23:20:16");

        CreateAccount createAccount = new CreateAccount();
        createAccount.setNumber("100001");
        createAccount.setAddress("河北省.廊坊市.广阳区.大学里职业技术学院");
        createAccount.setAccountDate(parse);
        createAccount.setDesc("在校开户");

        HashMap<String, String> link01 = new HashMap<String, String>();
        link01.put("userId", "number");
        link01.put("bizId", "number");
        link01.put("bizTime", "accountDate");
        link01.put("desc", "desc");
        RebateInfo rebbateInfo01 = MQAdapter.filter(createAccount.toString(), link01);
        System.out.println("mq.create_account(适配前)" + createAccount.toString());
        System.out.println("mq.create_account(适配后)" + JSON.toJSONString(rebbateInfo01));

        System.out.println("");

        OrderMq orderMq = new OrderMq();
        orderMq.setUid("100001");
        orderMq.setSku("10928092093111123");
        orderMq.setOrderId("100000890193847111");
        orderMq.setCreateOrderTime(parse);

        HashMap<String, String> link02 = new HashMap<String, String>();
        link02.put("userId", "uid");
        link02.put("bizId", "orderId");
        link02.put("bizTime", "createOrderTime");
        RebateInfo rebateInfo02 = MQAdapter.filter(orderMq.toString(), link02);
        System.out.println("mq.orderMq(适配前)" + orderMq.toString());
        System.out.println("mq.orderMq(适配后)" + JSON.toJSONString(rebateInfo02));
    }

    @Test
    public void test_itfAdapter() {
        OrderAdapterService popOrderAdapterService = new POPOrderAdapterServiceImpl();
        System.out.println("判断首单，接口适配(POP)：" + popOrderAdapterService.isFirst("100001"));

        OrderAdapterService insideOrderService = new InsideOrderService();
        System.out.println("判断首单，接口适配(自营)：" + insideOrderService.isFirst("100001"));
    }
}
















