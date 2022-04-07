package com.example.learn.api.master.repository;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.learn.api.master.entity.CarSumMatriks;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;

@Repository
public class CarComparisonDAOImpl implements CarComparisonDAO {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void saveComparison(CarSumMatriks req, String username) {

      StringBuilder sqlQuery = new StringBuilder();

      sqlQuery.append(" INSERT INTO public.tb_car_pairwise_comparison_matrix_sum ");
      sqlQuery.append(" ( car_type, ");
      String iIncreament;
      for (int i = 0; i < 26; i++) {
         iIncreament = String.format("%02d", i);
         sqlQuery.append(" key_00" + iIncreament + ",");
         sqlQuery.append(" val_00" + iIncreament + ",");
      }
      for (int i = 0; i < 25; i++) {
         iIncreament = String.format("%1d", i);
         sqlQuery.append(" keySum_" + iIncreament + ",");
         sqlQuery.append(" valSum_" + iIncreament + ",");
      }
      sqlQuery.append(" keySum_25, ");
      sqlQuery.append(" valSum_25 ");
      sqlQuery.append(" )");

      sqlQuery.append("VALUES( :carType ");

      NumberFormat fr = new DecimalFormat("0.00");
      for (Map.Entry<String, Double> entry : req.getMapMatriks().entrySet()) {

         sqlQuery.append(" ,'" + entry.getKey() + "', '" + Double.parseDouble(fr.format(entry.getValue())) + "' ");

      }
      Integer left = 26 - req.getMapMatriks().size();

      if (left != 0) {
         for (int i = 0; i < left; i++) {
            sqlQuery.append(" , null, null ");
         }
      }

      NumberFormat frSum = new DecimalFormat("0.000");
      for (Map.Entry<String, Double> entrySum : req.getSumMatriks().entrySet()) {
         sqlQuery.append(
               " ,'" + entrySum.getKey() + "', '" + Double.parseDouble(frSum.format(entrySum.getValue())) + "' ");
      }

      Integer leftSum = 26 - req.getSumMatriks().size();

      if (leftSum != 0) {
         for (int i = 0; i < leftSum; i++) {
            sqlQuery.append(" , null, null ");
         }
      }
      sqlQuery.append(" )");

      Query query = entityManager.createNativeQuery(sqlQuery.toString());
      query.setParameter("carType", req.getCarType());
      query.executeUpdate();

   }

   @Override
   public void saveSum(CarSumMatriks req, String username) {
      StringBuilder sqlQuery = new StringBuilder();

      sqlQuery.append(" INSERT INTO public.TB_CAR_SUM ");
      sqlQuery.append(" (car_cd,  ");
      String iIncreament;
      for (int i = 0; i < 15; i++) {
         iIncreament = String.format("%1d", i);
         sqlQuery.append(" keySum_" + iIncreament + ",");
         sqlQuery.append(" valSum_" + iIncreament + ",");
      }
      sqlQuery.append(" keySum_15, ");
      sqlQuery.append(" valSum_15 )");

      sqlQuery.append("VALUES( :carCd ");

      NumberFormat frSum = new DecimalFormat("0.000");
      for (Map.Entry<String, Double> entrySum : req.getSumMatriks().entrySet()) {
         sqlQuery.append(
               " ,'" + entrySum.getKey() + "', '" + Double.parseDouble(frSum.format(entrySum.getValue())) + "' ");
      }
      Integer leftSum = 16 - req.getSumMatriks().size();
      if (leftSum != 0) {
         for (int i = 0; i < leftSum; i++) {
            sqlQuery.append(" , null, null ");
         }
      }
      sqlQuery.append(" )");

      Query query = entityManager.createNativeQuery(sqlQuery.toString());

      query.executeUpdate();
   }

   @Override
   public void deletCarType(String carType) {
      StringBuilder sqlQuery = new StringBuilder();
      sqlQuery.append(" DELETE FROM public.tb_car_pairwise_comparison_matrix_sum WHERE car_type= :carType ");

      Query query = entityManager.createNativeQuery(sqlQuery.toString());
      if (!StringUtils.isEmpty(carType)) {
         query.setParameter("carType", carType);
      }
      query.executeUpdate();
   }

   @SuppressWarnings({ "unchecked" })
   @Override
   public List<CarSumMatriks> getAllCOmparisonValueMatriks() {
      StringBuilder sqlQuery = new StringBuilder();
      sqlQuery.append("select crp.car_type, ");
      String iIncreament;
      for (int i = 0; i < 26; i++) {
         iIncreament = String.format("%02d", i);
         sqlQuery.append(" crp.key_00" + iIncreament + ",");
         if (iIncreament.equals("25")) {
            sqlQuery.append(" crp.val_00" + iIncreament);
         } else {
            sqlQuery.append(" crp.val_00" + iIncreament + ",");
         }
      }
      sqlQuery.append(" FROM public.tb_car_pairwise_comparison_matrix_sum crp");
      Query query = entityManager.createNativeQuery(sqlQuery.toString());
      List<Object[]> rows = query.getResultList();
      List<CarSumMatriks> listResult = new ArrayList<>();
      for (Object[] row : rows) {
         CarSumMatriks entity = new CarSumMatriks();
         entity.setCarType((String) row[0]);
         TreeMap<String, Double> mapMatriks = new TreeMap<>();
         TreeMap<String, Double> sumMatriks = new TreeMap<>();
         int colAttrKey = 1;
         for (int i = 1; i < 53; i++) {
            if ((String) row[colAttrKey] != null) {
               mapMatriks.put((String) row[i], (Double) row[i + 1]);
               i += 1;
            } else {
               break;
            }
            colAttrKey += 2;
         }
         entity.setMapMatriks(mapMatriks);
         this.sumMatrikss(sumMatriks, (String) row[0]);
         entity.setSumMatriks(sumMatriks);
         listResult.add(entity);
      }
      return listResult;
   }

   private void sumMatrikss(TreeMap<String, Double> sumMatriks, String carType) {

      StringBuilder sqlQuery = new StringBuilder();
      sqlQuery.append("select ");
      String iIncreament;
      for (int i = 0; i < 25; i++) {
         iIncreament = String.format("%1d", i);
         sqlQuery.append(" keySum_" + iIncreament + ",");
         sqlQuery.append(" valSum_" + iIncreament + ",");
      }
      sqlQuery.append(" keySum_25, ");
      sqlQuery.append(" valSum_25 ");
      sqlQuery.append(" FROM public.tb_car_pairwise_comparison_matrix_sum WHERE car_type= :carType ");

      Query query = entityManager.createNativeQuery(sqlQuery.toString());
      query.setParameter("carType", carType);
      Object[] rows = (Object[]) query.getSingleResult();

      int colAttrKey = 0;
      for (int i = 0; i < 53; i++) {
         if ((String) rows[colAttrKey] != null) {
            sumMatriks.put((String) rows[i], (Double) rows[i + 1]);
            i += 1;
         } else {
            break;
         }
         colAttrKey += 2;
      }
   }

}
