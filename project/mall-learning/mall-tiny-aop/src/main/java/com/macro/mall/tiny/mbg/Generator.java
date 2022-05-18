package com.macro.mall.tiny.mbg;/**
 * Project: mall-learning
 * Package: com.macro.mall.tiny.mbg
 * Version: 1.0
 * Created by ljy on 2022-4-24 14:59
 */

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * @ClassName Generator
 * @Author: ljy on 2022-4-24 14:59
 * @Version: 1.0
 * @Description:生成model的主方法代码
 *
 */
public class Generator {
    public static void main(String[] args) throws Exception{
        //MBG执行过程中的警告信息
        ArrayList<String> warnings = new ArrayList<>();
        //当生成的代码重复时，覆盖原代码
        boolean overwrite = true;
        //读取我们的MBG配置文件
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //执行生成代码
        myBatisGenerator.generate(null);
        //输出警告信息
        for (String warning: warnings){
            System.out.println(warning);
        }
    }
}
