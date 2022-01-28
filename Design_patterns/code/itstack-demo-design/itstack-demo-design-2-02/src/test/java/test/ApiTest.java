package test;/**
 * Project: itstack-demo-design
 * Package: test
 * Version: 1.0
 * Created by ljy on 2022-1-26 16:17
 */

import org.itstack.demo.design.CacheService;
import org.itstack.demo.design.factory.JDKProxy;
import org.itstack.demo.design.factory.impl.EGMCacheAdapter;
import org.itstack.demo.design.factory.impl.IIRCacheAdapter;
import org.itstack.demo.design.impl.CacheServiceImpl;
import org.junit.Test;

/**
 * @ClassName ApiTest
 * @Author: ljy on 2022-1-26 16:17
 * @Version: 1.0
 * @Description:
 *
 */
public class ApiTest {

    @Test
    public void test_CacheService()throws Exception{

        CacheService proxy_EGM =  JDKProxy.getProxy(CacheServiceImpl.class,new EGMCacheAdapter());
        proxy_EGM.set("user_name_01", "小傅哥");
        String val01 = proxy_EGM.get("user_name_01");
        System.out.println("测试结果：" + val01);

        CacheService proxy_IIR = JDKProxy.getProxy(CacheServiceImpl.class, new IIRCacheAdapter());
        proxy_IIR.set("user_name_01", "小傅哥");
        String val02 = proxy_IIR.get("user_name_01");
        System.out.println("测试结果：" + val02);
    }
}
