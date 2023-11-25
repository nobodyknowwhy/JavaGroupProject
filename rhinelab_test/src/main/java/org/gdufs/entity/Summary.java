package org.gdufs.entity;


public class Summary {

  private long summaryNum;
  private String quarter;
  private String product;
  private long productionQuantity;
  private long productionSalesVolume;
  private double investment;
  private double profit;


  public long getSummaryNum() {
    return summaryNum;
  }

  public void setSummaryNum(long summaryNum) {
    this.summaryNum = summaryNum;
  }


  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }


  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }


  public long getProductionQuantity() {
    return productionQuantity;
  }

  public void setProductionQuantity(long productionQuantity) {
    this.productionQuantity = productionQuantity;
  }


  public long getProductionSalesVolume() {
    return productionSalesVolume;
  }

  public void setProductionSalesVolume(long productionSalesVolume) {
    this.productionSalesVolume = productionSalesVolume;
  }


  public double getInvestment() {
    return investment;
  }

  public void setInvestment(double investment) {
    this.investment = investment;
  }


  public double getProfit() {
    return profit;
  }

  public void setProfit(double profit) {
    this.profit = profit;
  }

}
