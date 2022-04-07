package com.example.learn.api.master.base.controller;

import com.example.learn.api.master.vo.UserInfo;

import org.springframework.security.core.Authentication;

public class BaseController {
    protected UserInfo getLoginUserInfo(Authentication authentication) {

        return (UserInfo) authentication.getPrincipal();
    }

    protected String getLoginUsername(Authentication authentication) {

        return this.getLoginUserInfo(authentication).getUsername();
    }

}
