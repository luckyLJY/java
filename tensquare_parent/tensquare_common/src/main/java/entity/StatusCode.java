package entity;/**
 * Project: tensquare_parent
 * Package: entity
 * Version: 1.0
 * Created by ljy on 2021-12-25 21:21
 */

/**
 * @ClassName StatusCode
 * @Author: ljy on 2021-12-25 21:21
 * @Version: 1.0
 * @Description:状态码类
 *  OK  20000成功
 *  ERROR 20001失败
 *  LOGINERROR 20002用户名或密码错误
 *  ACCESSERROR 20003权限不足
 *  REMOTEERROR 20004远程调用失败
 *  PEPERROR 20005重复操作
 */
public class StatusCode {
    public static final int OK=20000;
    public static final int ERROR=20001;
    public static final int LOGINERROR=20002;
    public static final int ACCESSERROR= 20003;
    public static final int REMOTEERROR = 20004;
    public static final int PEPERROR = 20005;
}
