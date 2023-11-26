package org.gdufs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summary {

    private long summaryNum;
    private String quarter;
    private String product;
    private long productionQuantity;
    private long productionSalesVolume;
    private double investment;
    private double profit;


}
