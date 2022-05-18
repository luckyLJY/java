package com.macro.mall.tiny.mbg;/**
 * Project: mall-tiny-stream
 * Package: com.macro.mall.tiny.mbg
 * Version: 1.0
 * Created by ljy on 2022-4-25 10:53
 */

import com.macro.mall.tiny.config.MyBatisConfig;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * @ClassName Generator
 * @Author: ljy on 2022-4-25 10:53
 * @Version: 1.0
 * @Description:
 * 1. 新建警告信息list
 * 2. 覆盖源代码标识
 * 3. 读取配置
 * 4. 构建mybatis对象生成代码
 * 5. 打印警告信息
 */
public class Generator {
    public static void main(String[] args) throws Exception{
        //MBG 执行过程中的警告信息
        ArrayList<String> warnings = new ArrayList<>();
        boolean overwerite = true;
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwerite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
