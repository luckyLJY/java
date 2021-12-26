package entity;/**
 * Project: tensquare_parent
 * Package: entity
 * Version: 1.0
 * Created by ljy on 2021-12-25 21:09
 */

import lombok.Data;

/**
 * @ClassName Result
 * @Author: ljy on 2021-12-25 21:09
 * @Version: 1.0
 * @Description:
 *  创建结果类
 *  属性：
 *  flag 表示
 *  code 状态
 *  message 信息
 *  data 数据
 */
@Data
public class Result {

    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }


}
