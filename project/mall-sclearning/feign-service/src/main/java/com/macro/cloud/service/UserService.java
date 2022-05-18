package com.macro.cloud.service;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.service
 * Version: 1.0
 * Created by ljy on 2022-5-12 9:33
 */

import com.macro.cloud.domain.CommonResult;
import com.macro.cloud.domain.User;
import com.macro.cloud.service.impl.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserService
 * @Author: ljy on 2022-5-12 9:33
 * @Version: 1.0
 * @Description:
 */
@FeignClient(value = "user-service",fallback = UserFallbackService.class)
public interface UserService {

    @PostMapping("/user/create")
    CommonResult create(@RequestBody User user);

    @GetMapping("/user/{id}")
    CommonResult<User> getUser(@PathVariable Long id);

    @GetMapping("/user/getByUsername")
    CommonResult<User> getByUsername(@RequestParam String username);

    @PostMapping("/user/update")
    CommonResult update(@RequestBody User user);

    @PostMapping("/user/delete/{id}")
    CommonResult delete(@PathVariable Long id);
}
