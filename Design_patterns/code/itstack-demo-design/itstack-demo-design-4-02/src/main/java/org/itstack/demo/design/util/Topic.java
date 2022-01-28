package org.itstack.demo.design.util;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.util
 * Version: 1.0
 * Created by ljy on 2022-1-27 16:29
 */

import java.util.Map;

/**
 * @ClassName Topic
 * @Author: ljy on 2022-1-27 16:29
 * @Version: 1.0
 * @Description:
 * 试卷类
 */
public class Topic {
    private Map<String,String> option;//选项：A、B、C、D
    private String key;//答案:B

    public Topic() {
    }

    public Topic(Map<String, String> option, String key) {
        this.option = option;
        this.key = key;
    }

    public Map<String, String> getOption() {
        return option;
    }

    public void setOption(Map<String, String> option) {
        this.option = option;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}


















