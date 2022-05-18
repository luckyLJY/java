package com.macro.mall.tiny.dto;/**
 * Project: mall-tiny-oss
 * Package: com.macro.mall.tiny.dto
 * Version: 1.0
 * Created by ljy on 2022-4-26 10:08
 */

/**
 * @ClassName MinioUploadDto
 * @Author: ljy on 2022-4-26 10:08
 * @Version: 1.0
 * @Description:
 */
public class MinioUploadDto {
    private String url;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
