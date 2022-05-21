package com.macro.cloud.controller;/**
 * Project: mall-sclearning
 * Package: com.macro.cloud.controller
 * Version: 1.0
 * Created by ljy on 2022-5-20 12:25
 */

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Author: ljy on 2022-5-20 12:25
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }
}

