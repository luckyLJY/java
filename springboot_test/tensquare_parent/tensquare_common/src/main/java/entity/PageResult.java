package entity;/**
 * Project: tensquare_parent
 * Package: entity
 * Version: 1.0
 * Created by ljy on 2021-12-25 21:15
 */

import lombok.Data;

import java.util.List;

/**
 * @ClassName PageResult
 * @Author: ljy on 2021-12-25 21:15
 * @Version: 1.0
 * @Description:分页结果类
 * 属性：
 *  total 总数
 *  row 行数
 */
@Data
public class PageResult<T> {
    private Long total;
    private List<T> row;

    public PageResult() {
    }

    public PageResult(Long total, List<T> row) {
        this.total = total;
        this.row = row;
    }


}
