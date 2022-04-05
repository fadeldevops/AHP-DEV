package com.example.learn.api.master.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.learn.api.master.base.service.impl.BaseServiceImpl;
import com.example.learn.api.master.entity.CarSumMatriks;
import com.example.learn.api.master.exception.ValidationException;
import com.example.learn.api.master.repository.CarComparisonDAO;
import com.example.learn.api.master.repository.CarComparisonRepo;
import com.example.learn.api.master.repository.CarRepo;
import com.example.learn.api.master.service.CarComparisonMatriksService;
import com.example.learn.api.master.vo.CarValueVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Service
public class CarComparisonMatriksServiceImpl extends BaseServiceImpl implements CarComparisonMatriksService {

   @Autowired
   CarRepo repoCar;

   @Autowired
   CarComparisonRepo repoCarComparison;

   @Autowired
   CarComparisonDAO daoComparison;

   @Transactional
   @Override
   public void insertValue(String username) {

      String iIncreamentI;
      String iIncreamentJ;

      List<Object[]> getAllValue = repoCar.listAllCar();
      List<CarValueVo> resultListValues = new ArrayList<>();

      for (Object[] row : getAllValue) {
         CarValueVo vo = new CarValueVo();

         vo.setCarCd((String) row[0]);
         vo.setCarPrice((BigInteger) row[1]);
         vo.setCarAge((Short) row[2]);
         vo.setCarFuelConsumption((Double) row[3]);
         vo.setCarMileage((BigDecimal) row[4]);
         vo.setCarCapacity((Short) row[5]);
         resultListValues.add(vo);
      }

      if (resultListValues.isEmpty()) {
         throw new ValidationException("COMMNERR00035", "resultListValue");
      }
      List<CarValueVo> resultListValue = resultListValues.stream().sorted(Comparator.comparing(CarValueVo::getCarPrice))
            .collect(Collectors.toList());

      Map<String, Double> mapDeafault = new HashMap<>();
      Map<String, Double> mapSum = new HashMap<>();
      for (int i = 0; i < resultListValue.size(); i++) {
         Map<String, Double> maps = new HashMap<>();
         iIncreamentI = String.format("%02d", i);
         for (int j = 0; j < resultListValue.size(); j++) {
            iIncreamentJ = String.format("%02d", j);
            if (iIncreamentI.equalsIgnoreCase(iIncreamentJ)) {
               maps.put("val_" + iIncreamentI + iIncreamentJ, 1.00);
            }
            // comparison price
            this.comparisonMatriksPrice(i, j, resultListValue, iIncreamentI, iIncreamentJ, maps);
         }
         for (Map.Entry<String, Double> var : maps.entrySet()) {
            mapDeafault.put(var.getKey(), var.getValue());
         }

      }

      TreeMap<String, Double> sorteds = new TreeMap<>();
      sorteds.putAll(mapDeafault);

      // Sum Matriks Price
      this.matriksSumPrice(resultListValue, sorteds, mapSum);

      TreeMap<String, Double> sumSort = new TreeMap<>();
      sumSort.putAll(mapSum);

      // SaveComparisonPrice[start]
      CarSumMatriks matrikss = new CarSumMatriks();
      matrikss.setCarType("TYPE_PRICE");
      matrikss.setMapMatriks(sorteds);
      matrikss.setSumMatriks(sumSort);
      Integer cekId = repoCarComparison.cekCountCarType(matrikss.getCarType());
      if (cekId > 0) {
         daoComparison.deletCarType(matrikss.getCarType());
      }
      daoComparison.saveComparison(matrikss, username);
      // SaveComparisonPrice[End]

   }

   @Override
   public List<CarSumMatriks> getAllComparison() {
      List<CarSumMatriks> getAll = daoComparison.getAllCOmparisonValueMatriks();
      return getAll;
   }

   private void comparisonMatriksPrice(int i, int j, List<CarValueVo> resultListValue, String iIncreamentI,
         String iIncreamentJ, Map<String, Double> maps) {

      Integer value3 = 15000000;
      Integer value5 = 40000000;
      Integer value7 = 70000000;
      Integer value9 = 100000000;

      if (resultListValue.get(i).getCarPrice().intValue() < resultListValue.get(j).getCarPrice().intValue()) {
         Integer selisih = resultListValue.get(j).getCarPrice().intValue()
               - resultListValue.get(i).getCarPrice().intValue();
         if (selisih <= value3) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 1.00);
         }
         if (selisih > value3 && selisih <= value5) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 3.00);
         }
         if (selisih > value5 && selisih <= value7) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 5.00);
         }
         if (selisih > value7 && selisih <= value9) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 7.00);
         }
         if (selisih > value9) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 9.00);
         }
      }

      if (resultListValue.get(i).getCarPrice().intValue() > resultListValue.get(j).getCarPrice().intValue()) {
         Integer selisih = resultListValue.get(i).getCarPrice().intValue()
               - resultListValue.get(j).getCarPrice().intValue();
         if (selisih <= value3) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 1 / 1.00);
         }
         if (selisih > value3 && selisih <= value5) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 1 / 3.00);
         }
         if (selisih > value5 && selisih <= value7) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 1 / 5.00);
         }
         if (selisih > value7 && selisih <= value9) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 1 / 7.00);
         }
         if (selisih > value9) {
            maps.put("val_" + iIncreamentI + iIncreamentJ, 1 / 9.00);
         }
      }
   }

   private void matriksSumPrice(List<CarValueVo> resultListValue, TreeMap<String, Double> mapMatriks,
         Map<String, Double> mapSum) {

      NumberFormat fr = new DecimalFormat("0.000");
      String incJ;
      Double sum = 0.000;
      for (int j = 0; j < resultListValue.size(); j++) {
         incJ = String.format("%02d", j);
         String sumIncreament = String.format("%1d", j);
         for (Map.Entry<String, Double> sortListEntry : mapMatriks.entrySet()) {
            String key = sortListEntry.getKey();
            if (key.substring((key.length() - 2)).contains(incJ)) {

               sum += sortListEntry.getValue();
            }
            if (sum != null) {
               sumIncreament = String.format("%1d", j);
               mapSum.put("keySum_" + sumIncreament, Double.parseDouble(fr.format(sum)));
            }
         }

         sum = 0.000;
      }
   }

}
