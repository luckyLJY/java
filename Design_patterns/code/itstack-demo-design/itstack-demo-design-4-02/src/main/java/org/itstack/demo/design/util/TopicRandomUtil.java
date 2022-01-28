package org.itstack.demo.design.util;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.design.util
 * Version: 1.0
 * Created by ljy on 2022-1-27 16:27
 */

import java.util.*;

/**
 * @ClassName TopicRandomUtil
 * @Author: ljy on 2022-1-27 16:27
 * @Version: 1.0
 * @Description:
 * 乱序工具类
 */
public class TopicRandomUtil {

    /**
     * 乱序Map元素，记录对应答案key
     * @param option 题目
     * @param key    答案
     * @return Topic 乱序后 {A=c., B=d., C=a., D=b.}
     */
    static public Topic random(Map<String, String> option, String key) {
        Set<String> keySet = option.keySet();
        ArrayList<String> keyList = new ArrayList<String>(keySet);
        Collections.shuffle(keyList);
        HashMap<String, String> optionNew = new HashMap<String, String>();
        int idx = 0;
        String keyNew = "";
        for (String next : keySet) {
            String randomKey = keyList.get(idx++);
            if (key.equals(next)) {
                keyNew = randomKey;
            }
            optionNew.put(randomKey, option.get(next));
        }
        return new Topic(optionNew, keyNew);
    }
}
