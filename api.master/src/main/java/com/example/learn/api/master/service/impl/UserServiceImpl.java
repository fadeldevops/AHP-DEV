package com.example.learn.api.master.service.impl;

import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.entity.UserEntity;
import com.example.learn.api.master.entity.UserProfileEntity;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.repository.UserProfileRepo;
import com.example.learn.api.master.repository.UserRepository;
import com.example.learn.api.master.request.UserProfileReq;
import com.example.learn.api.master.request.UserRequest;
import com.example.learn.api.master.response.DeletedCommonResp;
import com.example.learn.api.master.response.UserResponse;
import com.example.learn.api.master.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class UserServiceImpl extends BaseServiceImpl implements UserService {

   @Autowired
   UserRepository userRepo;

   @Autowired
   UserProfileRepo userProfilRepo;

   private Boolean ValidationUser(UserRequest req) {

      Boolean valid = false;

      if (StringUtils.isEmpty(req.getUsername())) {
         throw new ValidationException("COMMNERR00034", "Username");
      } else {
         valid = true;
      }

      if (StringUtils.isEmpty(req.getPassword())) {
         throw new ValidationException("COMMNERR00034", "Password");
      } else {
         valid = true;
      }
      UserProfileReq reqProfile = req.getUserProfile();
      if (reqProfile == null) {
         throw new ValidationException("COMMNERR00034", "reqProfile");
      } else {
         valid = true;
      }

      if (StringUtils.isEmpty(reqProfile.getPersonName())) {
         throw new ValidationException("COMMNERR00034", "Person Name");
      } else {
         valid = true;
      }

      if (StringUtils.isEmpty(reqProfile.getEmail())) {
         throw new ValidationException("COMMNERR00034", "Email");
      } else {
         valid = true;
      }

      return valid;

   }

   @Override
   public UserResponse create(UserRequest req, String username) {

      UserEntity cekId = userRepo.findById(req.getUsername()).orElse(null);
      if (cekId != null) {
         throw new ValidationException("COMMNERR00009", "username", req.getUsername());
      }
      Boolean isValid = this.ValidationUser(req);
      UserEntity entity = new UserEntity();
      if (isValid) {

         BeanUtils.copyProperties(req, entity);
         entity.setUsername(req.getUsername());
         entity.setPass(req.getPassword());
         this.setCreateCommonD(entity, username);
         userRepo.save(entity);

         UserProfileEntity cekIdProfile = userProfilRepo.findById(req.getUsername()).orElse(null);
         if (cekIdProfile != null && !cekIdProfile.getDeletedFlag()) {
            throw new ValidationException("COMMNERR00009", "username", req.getUserProfile().toString());
         }
         UserProfileEntity entityProfile = new UserProfileEntity();
         UserProfileReq reqProfile = req.getUserProfile();

         BeanUtils.copyProperties(reqProfile, entityProfile);
         entityProfile.setUsername(entity.getUsername());

         this.setCreateCommon(entityProfile, username);
         userProfilRepo.save(entityProfile);

      }

      UserResponse response = new UserResponse();
      BeanUtils.copyProperties(entity, response);
      response.setPassword(entity.getPass());
      response.setUserProfile(req.getUserProfile());
      return response;
   }

   @Override
   public UserResponse update(UserRequest req, String username) {

      UserEntity cekId = userRepo.findById(req.getUsername()).orElse(null);
      if (cekId == null) {
         throw new ValidationException("COMMNERR00006", "username", req.getUsername());
      }

      Boolean isValid = this.ValidationUser(req);
      if (isValid) {

         BeanUtils.copyProperties(req, cekId);
         cekId.setPass(req.getPassword());
         this.setUpdateCommonD(cekId, username);
         userRepo.save(cekId);

         UserProfileEntity cekIdProfile = userProfilRepo.findById(req.getUsername()).orElse(null);

         UserProfileEntity entityProfile = new UserProfileEntity();
         if (cekId != null && cekIdProfile == null) {

            UserProfileReq reqProfile = req.getUserProfile();
            BeanUtils.copyProperties(reqProfile, entityProfile);
            entityProfile.setUsername(cekId.getUsername());

            this.setCreateCommon(entityProfile, username);
            userProfilRepo.save(entityProfile);
         } else {
            if (cekId != null && cekIdProfile != null) {
               UserProfileReq reqProfile = req.getUserProfile();
               cekIdProfile.setPhoneNo(reqProfile.getPhoneNo());
               cekIdProfile.setPersonName(reqProfile.getPersonName());
               cekIdProfile.setEmail(reqProfile.getEmail());
               this.setUpdateCommon(cekIdProfile, username);
               userProfilRepo.save(cekIdProfile);
            }
         }

      }

      UserResponse response = new UserResponse();
      BeanUtils.copyProperties(cekId, response);
      response.setPassword(cekId.getPass());
      response.setUserProfile(req.getUserProfile());
      return response;
   }

   @Override
   public UserResponse get(String username) {

      UserEntity cekId = userRepo.findById(username).orElse(null);
      if (cekId == null) {
         throw new ValidationException("COMMNERR00009", "username", username);
      }
      UserProfileEntity cekIdProfile = userProfilRepo.findById(username).orElse(null);
      if (cekIdProfile == null || cekIdProfile.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00009", "username", username);
      }

      UserProfileReq userProfileReq = new UserProfileReq();
      userProfileReq.setEmail(cekIdProfile.getEmail());
      userProfileReq.setPersonName(cekIdProfile.getPersonName());
      userProfileReq.setPhoneNo(cekIdProfile.getPhoneNo());

      UserResponse response = new UserResponse();
      BeanUtils.copyProperties(cekId, response);
      response.setPassword(cekId.getPass());
      response.setUserProfile(userProfileReq);
      return response;
   }

   @Override
   public DeletedCommonResp delet(String username) {

      UserProfileEntity cekIdProfile = userProfilRepo.findById(username).orElse(null);
      if (cekIdProfile == null || cekIdProfile.getDeletedFlag()) {

         throw new ValidationException("COMMNERR00006", "username", username);

      }
      userProfilRepo.deletUserProfile(cekIdProfile.getUsername());

      UserEntity cekId = userRepo.findById(username).orElse(null);
      if (cekId == null) {
         throw new ValidationException("COMMNERR00006", "username", username);

      }

      userRepo.deletUser(cekId.getUsername());

      DeletedCommonResp respon = new DeletedCommonResp();
      respon.setSuccess("Processed Successfully");
      respon.setFailed("0");

      return respon;
   }

}
