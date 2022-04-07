package com.example.learn.api.master.service.impl;

import com.example.learn.api.master.base.response.SearchResponse;
import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.entity.EmployeeEntity;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.repository.EmployeRepository;
import com.example.learn.api.master.request.EmployeSearchRequest;
import com.example.learn.api.master.request.EmployeeRequest;
import com.example.learn.api.master.response.EmployeResponse;
import com.example.learn.api.master.service.EmployeeService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {

   @Autowired
   EmployeRepository empRepo;

   @Override
   public EmployeResponse createEmployee(EmployeeRequest req, String username) {

      Boolean isValid = ValidationCommon(req);

      EmployeeEntity entity = new EmployeeEntity();

      if (isValid) {

         EmployeeEntity cekId = empRepo.findById(req.getEmployeeCd()).orElse(null);
         if (cekId != null && !cekId.getDeletedFlag()) {
            throw new ValidationException("COMMNERR00009", "Employee Code", req.getEmployeeCd());
         }

         entity.setEmployeCd(req.getEmployeeCd());
         entity.setEmployeName(req.getEmployeeName());
         entity.setEmployeContacNo(req.getEmployeeContacNo());
         entity.setEmployeDesc(req.getEmployeeDesc());
         this.setCreateCommon(entity, username);
         empRepo.save(entity);
      }

      EmployeResponse response = new EmployeResponse();
      BeanUtils.copyProperties(entity, response);
      return response;
   }

   private Boolean ValidationCommon(EmployeeRequest req) {

      Boolean valid = false;

      if (StringUtils.isEmpty(req.getEmployeeCd())) {
         throw new ValidationException("COMMNERR00034", "Employee Code");
      } else {
         valid = true;
      }
      if (StringUtils.isEmpty(req.getEmployeeName())) {
         throw new ValidationException("COMMNERR00034", "Employee Name");
      } else {
         valid = true;
      }
      if (StringUtils.isEmpty(req.getEmployeeContacNo())) {
         throw new ValidationException("COMMNERR00034", "Employee Contac No");
      } else {
         valid = true;
      }

      return valid;

   }

   @Override
   public EmployeResponse get(String employeCd) {

      if (StringUtils.isEmpty(employeCd)) {
         throw new ValidationException("COMMNERR00034", "Employee Code");
      }
      EmployeeEntity cekId = empRepo.findById(employeCd).orElse(null);
      if (cekId == null || cekId.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00006", "Employee Code", employeCd);
      }

      EmployeResponse response = new EmployeResponse();
      BeanUtils.copyProperties(cekId, response);
      return response;
   }

   @Override
   public EmployeResponse update(EmployeeRequest req, String username) {

      Boolean isValid = ValidationCommon(req);

      EmployeeEntity entity = new EmployeeEntity();

      EmployeeEntity cekId = empRepo.findById(req.getEmployeeCd()).orElse(null);
      if (cekId == null || cekId.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00006", "Employee Code", req.getEmployeeCd());
      }

      if (isValid) {

         entity.setEmployeCd(req.getEmployeeCd());
         entity.setEmployeName(req.getEmployeeName());
         entity.setEmployeContacNo(req.getEmployeeContacNo());
         entity.setEmployeDesc(req.getEmployeeDesc());
         this.setUpdateCommon(entity, username);

      }
      empRepo.save(entity);

      EmployeResponse response = new EmployeResponse();
      BeanUtils.copyProperties(entity, response);
      return response;
   }

   @SuppressWarnings("unchecked")
   @Override
   public SearchResponse<EmployeeEntity> search(EmployeSearchRequest req) {

      String employeCode = req.getEmployeCd().replace("_", "\\_");
      String employeName = req.getEmployeName().replace("_", "\\_");
      Page<EmployeeEntity> page = empRepo.search(
            employeCode != null ? employeCode : StringUtils.EMPTY,
            employeName != null ? employeName : StringUtils.EMPTY,
            this.getPageable(req));

      return this.createSearchResponse(page, req);
   }

   @Override
   public EmployeResponse delet(String employeCd, String username) {

      if (StringUtils.isEmpty(employeCd)) {
         throw new ValidationException("COMMNERR00034", "Employee Code");
      }

      EmployeeEntity cekId = empRepo.findById(employeCd).orElse(null);
      if (cekId == null || cekId.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00006", "Employee Code", employeCd);
      }
      cekId.setDeletedFlag(true);
      this.setDeleteCommon(cekId, username);

      EmployeResponse response = new EmployeResponse();
      BeanUtils.copyProperties(cekId, response);
      return response;

   }

}
