package com.example.learn.api.master.service;

import com.example.learn.api.master.base.response.SearchResponse;
import com.example.learn.api.master.base.service.BaseService;
import com.example.learn.api.master.entity.EmployeeEntity;
import com.example.learn.api.master.request.EmployeSearchRequest;
import com.example.learn.api.master.request.EmployeeRequest;
import com.example.learn.api.master.response.EmployeResponse;

public interface EmployeeService extends BaseService {

   EmployeResponse createEmployee(EmployeeRequest req, String username);

   EmployeResponse update(EmployeeRequest req, String username);

   EmployeResponse get(String employeCd);

   SearchResponse<EmployeeEntity> search(EmployeSearchRequest req);

   EmployeResponse delet(String employeCd, String username);

}
