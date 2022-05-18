package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 8:57
 */

import lombok.Cleanup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName ClearUpExample
 * @Author: ljy on 2022-5-7 8:57
 * @Version: 1.0
 * @Description:
 */
public class ClearnUpExample {
    public static void main(String[] args)throws IOException {
        String inStr = "Hello World";
        //使用输入输出流自动关闭，无需编写try catch和close()方法
        @Cleanup ByteArrayInputStream in = new ByteArrayInputStream(inStr.getBytes(StandardCharsets.UTF_8));
        @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] bytes = new byte[1024];
        while (true){
            int r = in.read(bytes);
            if (r == -1) break;
            out.write(bytes,0,r);
        }
        String outStr = out.toString("UTF-8");
        System.out.println(outStr);
    }
}
