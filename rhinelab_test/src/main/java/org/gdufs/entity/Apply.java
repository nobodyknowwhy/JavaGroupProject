package org.gdufs.entity;


public class Apply {

  private long applyNum;
  private long employeeNum;
  private String name;
  private String origionSection;
  private String newSection;
  private double origionSalary;
  private double newSalary;
  private String applyReason;
  private String status;


  public long getApplyNum() {
    return applyNum;
  }

  public void setApplyNum(long applyNum) {
    this.applyNum = applyNum;
  }


  public long getEmployeeNum() {
    return employeeNum;
  }

  public void setEmployeeNum(long employeeNum) {
    this.employeeNum = employeeNum;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getOrigionSection() {
    return origionSection;
  }

  public void setOrigionSection(String origionSection) {
    this.origionSection = origionSection;
  }


  public String getNewSection() {
    return newSection;
  }

  public void setNewSection(String newSection) {
    this.newSection = newSection;
  }


  public double getOrigionSalary() {
    return origionSalary;
  }

  public void setOrigionSalary(double origionSalary) {
    this.origionSalary = origionSalary;
  }


  public double getNewSalary() {
    return newSalary;
  }

  public void setNewSalary(double newSalary) {
    this.newSalary = newSalary;
  }


  public String getApplyReason() {
    return applyReason;
  }

  public void setApplyReason(String applyReason) {
    this.applyReason = applyReason;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
