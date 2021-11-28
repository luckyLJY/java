package com.guigu.jedis;/**
 * Project: redisDemo
 * Package: com.guigu.jedis
 * Version: 1.0
 * Created by ljy on 2021-11-20 21:43
 */

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @ClassName VerifiCode
 * @Author: ljy on 2021-11-20 21:43
 * @Version: 1.0
 * @Description:
 * 手机验证码
 *  需求：
 *      1、输入手机号，点击发送后随机生成6位数字码，2分钟有效
 *      2、输入验证码，点击验证，返回成功或失败
 *      3、每个手机号每天只能输入3次
 */
public class VerifiCode {

    /**
     * 1.使用Random生成数子验证码
     * 2.验证码设置2分钟过期时间
     * 3.判断验证码是否一致
     * 4.验证码有效性使用incr
     */

    public static void main(String[] args) {
        //模拟验证码发送
        verifyCode("18143776073");
        //getRedisCode("18143776073","302572");
    }

    /**
     * 3.验证码
     */
    public static void getRedisCode(String phone,String code){
        //从redis中获取验证码
        //连接redis
        Jedis  jedis = new Jedis("192.168.75.137",6379);

        //验证码key
        String codeKey = "VerifyCode"+phone+":code";

        System.out.println(codeKey);
        String redisCode = jedis.get(codeKey);
        //判断
        if(redisCode.equals(code)){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }

        jedis.close();
    }

    /**
     * 2.每个手机灭天只能发送三次，验证码发到redis中，设置过期时间
     */
    public  static void verifyCode(String phone){
        //连接redis
        Jedis  jedis = new Jedis("192.168.75.137",6379);

        //拼接key
        //手机发送次数Key
        String countKey = "VerifyCode"+phone+":count";

        //验证码key
        String codeKey = "VerifyCode"+phone+":code";

        //每个手机每天只能发送3次
        String count = jedis.get(countKey);

        if (count == null){
            //没有发送次数，第一次发送
            //设置发送次数为1
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count) <= 2){
            //发送次数+1
            jedis.incr(countKey);
        }else if(Integer.parseInt(count) > 2){
            //发送三次，不能再发送
            System.out.println("今天发送次数已经超过3此");
            jedis.close();
            return;
        }

        //发送的验证码放入到redis中
        String vcode = getCode();
        jedis.setex(codeKey,1200,vcode);
        jedis.close();
    }


    /**
     * 1.生成6为数字验证码
     */
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }
}
