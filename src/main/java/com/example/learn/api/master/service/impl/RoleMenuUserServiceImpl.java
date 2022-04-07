package com.example.learn.api.master.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.entity.MenuEntity;
import com.example.learn.api.master.entity.RoleEntity;
import com.example.learn.api.master.entity.RoleMenuEntity;
import com.example.learn.api.master.entity.RoleUserEntity;
import com.example.learn.api.master.entity.UserEntity;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.repository.MenuRepo;
import com.example.learn.api.master.repository.RoleMenuRepo;
import com.example.learn.api.master.repository.RoleRepository;
import com.example.learn.api.master.repository.RoleUserRepository;
import com.example.learn.api.master.repository.UserRepository;
import com.example.learn.api.master.request.RoleMenuReq;
import com.example.learn.api.master.request.RoleRequest;
import com.example.learn.api.master.request.RoleUserReq;
import com.example.learn.api.master.response.DeletedCommonResp;
import com.example.learn.api.master.service.RoleMenuUserService;
import com.example.learn.api.master.vo.RoleMenuUserVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class RoleMenuUserServiceImpl extends BaseServiceImpl implements RoleMenuUserService {

   @Autowired
   RoleRepository repoRole;

   @Autowired
   RoleMenuRepo repoRoleMenu;

   @Autowired
   RoleUserRepository repoRoleUser;

   @Autowired
   UserRepository repoUser;

   @Autowired
   MenuRepo repoMenu;

   private Boolean ValidationMethod(RoleRequest req) {

      Boolean valid = false;

      if (StringUtils.isEmpty(req.getRoleCd())) {
         throw new ValidationException("messageCode", "Role Code");
      } else {
         valid = true;
      }
      if (StringUtils.isEmpty(req.getRoleName())) {
         throw new ValidationException("messageCode", "Role Name");
      } else {
         valid = true;
      }
      if (req.getAdminFlag() == null) {
         throw new ValidationException("messageCode", "Role Name");
      } else {
         valid = true;
      }

      List<RoleMenuReq> roleMenuReq = req.getListRoleMenu();
      if (roleMenuReq.isEmpty()) {

         throw new ValidationException("messageCode", "Role Code");

      } else {

         valid = true;
      }
      for (RoleMenuReq roleMenu : roleMenuReq) {

         MenuEntity cekMenu = repoMenu.findById(roleMenu.getMenuCd()).orElse(null);
         if (cekMenu == null || cekMenu.getDeletedFlag()) {
            throw new ValidationException("messageCode", "Menu Code");
         } else {
            valid = true;
         }

         if (StringUtils.isEmpty(roleMenu.getMenuCd())) {
            throw new ValidationException("messageCode", "Menu Code");
         } else {
            valid = true;
         }

      }

      UserEntity cekUser = repoUser.findById(req.getUsername()).orElse(null);
      if (cekUser == null) {
         throw new ValidationException("messageCode", "Username");
      } else {
         valid = true;
      }
      if (StringUtils.isEmpty(req.getUsername())) {
         throw new ValidationException("messageCode", "Username");
      } else {
         valid = true;
      }

      return valid;

   }

   @Override
   public RoleMenuUserVo create(RoleRequest req, String username) {

      RoleEntity cekRole = repoRole.findById(req.getRoleCd()).orElse(null);
      if (cekRole != null && cekRole.getDeletedFlag()) {
         throw new ValidationException("messageCode", "Role Code");
      }
      Boolean isValid = this.ValidationMethod(req);
      RoleEntity entityRole = new RoleEntity();
      List<RoleMenuEntity> resultRoleMnu = new ArrayList<>();
      RoleUserEntity entityRU = new RoleUserEntity();
      RoleMenuUserVo response = new RoleMenuUserVo();
      if (isValid) {
         entityRole.setRoleCd(req.getRoleCd());
         entityRole.setRoleName(req.getRoleName());
         entityRole.setAdminFlag(req.getAdminFlag());
         this.setCreateCommon(entityRole, username);

         // UserEntity getUser = repoUser.findById(ru.getUsername()).orElse(null);

         entityRU.setRoleCd(req.getRoleCd());
         entityRU.setUsername(req.getUsername());
         this.setCreateCommonD(entityRU, username);

         if (req.getAdminFlag()) {
            req.setListRoleMenu(null);
         } else {
            for (RoleMenuReq menuReq : req.getListRoleMenu()) {
               RoleMenuEntity entityRoleMenu = new RoleMenuEntity();
               entityRoleMenu.setRoleCd(req.getRoleCd());
               entityRoleMenu.setMenuCd(menuReq.getMenuCd());
               this.setCreateCommon(entityRoleMenu, username);

               if (!resultRoleMnu.isEmpty()) {
                  for (RoleMenuEntity cekDuplicate : resultRoleMnu) {
                     if (StringUtils.equals(cekDuplicate.getMenuCd(), menuReq.getMenuCd())) {
                        throw new ValidationException("COMMNERR00019", "Menu Cd", menuReq.getMenuCd());
                     }
                  }
               }

               resultRoleMnu.add(entityRoleMenu);

            }
         }
         repoRole.save(entityRole);
         repoRoleUser.save(entityRU);
         repoRoleMenu.saveAll(resultRoleMnu);

      }

      response.setRoleCd(entityRole.getRoleCd());
      response.setRoleName(entityRole.getRoleName());
      response.setAdminFlag(entityRole.getAdminFlag());
      response.setUsername(entityRU.getUsername());
      response.setListRoleMenu(req.getListRoleMenu());

      return response;
   }

   @Override
   public RoleMenuUserVo update(RoleRequest req, String username) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public RoleMenuUserVo get(String roleCd) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public DeletedCommonResp delet(String roleCd) {
      // TODO Auto-generated method stub
      return null;
   }

}
