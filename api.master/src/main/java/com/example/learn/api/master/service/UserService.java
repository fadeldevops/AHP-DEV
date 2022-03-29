package com.example.learn.api.master.service;

import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.request.UserRequest;
import com.example.learn.api.master.response.DeletedCommonResp;
import com.example.learn.api.master.response.UserResponse;

public interface UserService extends BaseService {

   UserResponse create(UserRequest req, String username);

   UserResponse update(UserRequest req, String username);

   UserResponse get(String username);

   DeletedCommonResp delet(String username);

}
