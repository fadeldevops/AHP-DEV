package com.example.learn.api.master.service;

import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.response.LoginResponse;

public interface LoginService extends BaseService {

   LoginResponse login(String username, String password);

}
