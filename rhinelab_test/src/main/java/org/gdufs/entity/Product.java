package corg.gdufs.entity;


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


  public long getProductNum() {
    return productNum;
  }

  public void setProductNum(long productNum) {
    this.productNum = productNum;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }


  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }


  public String getProductFunction() {
    return productFunction;
  }

  public void setProductFunction(String productFunction) {
    this.productFunction = productFunction;
  }


  public java.sql.Date getProductDate() {
    return productDate;
  }

  public void setProductDate(java.sql.Date productDate) {
    this.productDate = productDate;
  }


  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }


  public String getUserComment() {
    return userComment;
  }

  public void setUserComment(String userComment) {
    this.userComment = userComment;
  }


  public long getSalesVolume() {
    return salesVolume;
  }

  public void setSalesVolume(long salesVolume) {
    this.salesVolume = salesVolume;
  }


  public double getInvestment() {
    return investment;
  }

  public void setInvestment(double investment) {
    this.investment = investment;
  }

}
