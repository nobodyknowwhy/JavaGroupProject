package org.gdufs.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
  private long purchaseNum;
  private long productNum;
  private String phone;
  private long quantity;
  private double totalPrices;
  private Date purchaseDate;
  private String status;
  private String address;
}
