package org.gdufs.entity;


public class Position {

  private long positionNum;
  private long sectionNum;
  private long employeeNum;
  private String positionName;


  public long getPositionNum() {
    return positionNum;
  }

  public void setPositionNum(long positionNum) {
    this.positionNum = positionNum;
  }


  public long getSectionNum() {
    return sectionNum;
  }

  public void setSectionNum(long sectionNum) {
    this.sectionNum = sectionNum;
  }


  public long getEmployeeNum() {
    return employeeNum;
  }

  public void setEmployeeNum(long employeeNum) {
    this.employeeNum = employeeNum;
  }


  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }

}
