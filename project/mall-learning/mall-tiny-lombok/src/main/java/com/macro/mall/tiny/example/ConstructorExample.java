package com.macro.mall.tiny.example;/**
 * Project: mall-tiny-lombok
 * Package: com.macro.mall.tiny.example
 * Version: 1.0
 * Created by ljy on 2022-5-7 9:27
 */

import lombok.*;

/**
 * @ClassName ConstructorExample
 * @Author: ljy on 2022-5-7 9:27
 * @Version: 1.0
 * @Description:
 */

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ConstructorExample {


    @NonNull
    @ToString.Exclude
    private Long id;
    private String name;
    private Integer age;

    public static void main(String[] args) {
        //无参构造
        ConstructorExample example1 = new ConstructorExample();
        System.out.println(example1);
        //全参构造
        ConstructorExample example2 = new ConstructorExample(1L, "TEST", 10);
        System.out.println(example2);
        //@NonNULL必须构造参数
        ConstructorExample example3 =  ConstructorExample.of(1L);
        System.out.println(example3.getId());
    }
}
