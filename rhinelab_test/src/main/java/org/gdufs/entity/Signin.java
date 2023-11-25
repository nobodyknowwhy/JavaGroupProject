package org.gdufs.entity;


public class Signin {

  private long signinNum;
  private long employeeNum;
  private String signinTime;
  private String checkoutTime;
  private double workHour;


  public long getSigninNum() {
    return signinNum;
  }

  public void setSigninNum(long signinNum) {
    this.signinNum = signinNum;
  }


  public long getEmployeeNum() {
    return employeeNum;
  }

  public void setEmployeeNum(long employeeNum) {
    this.employeeNum = employeeNum;
  }


  public String getSigninTime() {
    return signinTime;
  }

  public void setSigninTime(String signinTime) {
    this.signinTime = signinTime;
  }


  public String getCheckoutTime() {
    return checkoutTime;
  }

  public void setCheckoutTime(String checkoutTime) {
    this.checkoutTime = checkoutTime;
  }


  public double getWorkHour() {
    return workHour;
  }

  public void setWorkHour(double workHour) {
    this.workHour = workHour;
  }

}
