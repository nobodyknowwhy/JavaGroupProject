package org.gdufs.entity;


public class Purchase {

  private long purchaseNum;
  private long productNum;
  private String phone;
  private long quantity;
  private double totalPrices;
  private java.sql.Date purchaseDate;
  private String status;
  private String address;


  public long getPurchaseNum() {
    return purchaseNum;
  }

  public void setPurchaseNum(long purchaseNum) {
    this.purchaseNum = purchaseNum;
  }


  public long getProductNum() {
    return productNum;
  }

  public void setProductNum(long productNum) {
    this.productNum = productNum;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }


  public double getTotalPrices() {
    return totalPrices;
  }

  public void setTotalPrices(double totalPrices) {
    this.totalPrices = totalPrices;
  }


  public java.sql.Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(java.sql.Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
