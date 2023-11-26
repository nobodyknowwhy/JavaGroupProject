package org.gdufs.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChartMapper {
    @Select("select CONCAT(YEAR(`productDate`),'_',quarter(`productDate`)) qu,\n" +
            "       name,\n" +
            "       sum(`quantity`) as number,\n" +
            "       sum(`investment`) as t_price,\n" +
            "       sum(`unitPrice`) as price ,\n" +
            "       sum(`salesVolume`) as sl,\n" +
            "       MAX(`unitPrice` * `salesVolume`) as max\n" +
            "from product\n" +
            "WHERE `productDate` BETWEEN '2020-01-01' AND '2025-12-31' group by qu ORDER BY qu limit 10")
    List<Map<String, Object>> selectProductData();

    @Insert("INSERT INTO summary (quarter, product, productionQuantity, productionSalesVolume, investment, profit) " +
            "VALUES (#{qu}, #{name}, #{number}, #{sl}, #{t_price}, #{profit})")
    void insertSummary(@Param("qu") String qu, @Param("name") String name, @Param("number") int number,
                       @Param("sl") int sl, @Param("t_price") double t_price, @Param("profit") double profit);
}
