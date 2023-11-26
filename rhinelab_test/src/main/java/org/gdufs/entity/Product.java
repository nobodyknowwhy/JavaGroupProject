package org.gdufs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private long productNum;
    private String name;
    private long quantity;
    private double unitPrice;
    private String productFunction;
    private java.sql.Date productDate;
    private String introduction;
    private String userComment;
    private long salesVolume;
    private double investment;


}
