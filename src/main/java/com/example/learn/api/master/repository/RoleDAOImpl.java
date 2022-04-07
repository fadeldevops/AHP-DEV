package com.example.learn.api.master.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.learn.api.master.vo.RoleVo;

import org.springframework.stereotype.Repository;
import javax.persistence.Query;

@Repository
public class RoleDAOImpl implements RoleDAO {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public List<RoleVo> getActiveRoles(String username) {
      List<RoleVo> roles = new ArrayList<>();
      StringBuilder sb = new StringBuilder();

      sb.append("select ");
      sb.append("	tru.role_cd, ");
      sb.append("	tr.role_name, ");
      sb.append("	tr.admin_flag ");
      sb.append("from ");
      sb.append("	public.tb_role_user tru ");
      sb.append("inner join public.tb_role tr on ");
      sb.append("tru.role_cd = tr.role_cd ");
      sb.append("inner join public.tb_m_user tmu on ");
      sb.append("tru.username = tmu.username ");
      sb.append("where tr.deleted_flag = false and tru.username = :username ");

      Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter("username", username);

      @SuppressWarnings("unchecked")
      List<Object[]> rows = query.getResultList();

      for (Object[] row : rows) {
         RoleVo roleVO = new RoleVo();

         roleVO.setRoleCd((String) row[0]);
         roleVO.setRoleName((String) row[1]);
         roleVO.setAdminFlag((Boolean) row[2]);

         roles.add(roleVO);
      }

      return roles;
   }

}
