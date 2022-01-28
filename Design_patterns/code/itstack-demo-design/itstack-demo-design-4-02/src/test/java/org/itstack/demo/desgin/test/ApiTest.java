package org.itstack.demo.desgin.test;/**
 * Project: itstack-demo-design
 * Package: org.itstack.demo.desgin.test
 * Version: 1.0
 * Created by ljy on 2022-1-28 10:23
 */

import org.itstack.demo.design.QuestionBankController;
import org.junit.Test;

/**
 * @ClassName ApiTest
 * @Author: ljy on 2022-1-28 10:23
 * @Version: 1.0
 * @Description:
 */
public class ApiTest {

    @Test
    public void test_QuestionBank() throws CloneNotSupportedException {
        QuestionBankController questionBankController = new QuestionBankController();
        System.out.println(questionBankController.createPaper("花花", "1000001921032"));
        System.out.println(questionBankController.createPaper("豆豆", "1000001921051"));
        System.out.println(questionBankController.createPaper("大宝", "1000001921987"));
    }

}