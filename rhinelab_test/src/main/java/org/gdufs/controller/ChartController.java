package org.gdufs.controller;

import org.gdufs.mapper.ChartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class ChartController {
    @Autowired
    private ChartMapper chartMapper;

    @RequestMapping("/resultExhibition")
    public String resultExhibition(Model model){
        List<Map<String, Object>> dataList = chartMapper.selectProductData();
        model.addAttribute("arr", dataList);
        for (Map<String, Object> data : dataList) {
            String qu = (String) data.get("qu");
            String name = (String) data.get("name");
            int number = ((BigDecimal) data.get("number")).intValue();
            int sl = ((BigDecimal) data.get("sl")).intValue();
            double t_price = ((BigDecimal) data.get("t_price")).doubleValue();
            double profit = ((BigDecimal) data.get("price")).doubleValue() * number - t_price;
            chartMapper.insertSummary(qu, name, number, sl, t_price, profit);
        }
        return "product_chart";
    }
}
