package org.gdufs.entity;


public class Section {

  private long sectionNum;
  private String name;
  private String director;
  private long sectionPeople;


  public long getSectionNum() {
    return sectionNum;
  }

  public void setSectionNum(long sectionNum) {
    this.sectionNum = sectionNum;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }


  public long getSectionPeople() {
    return sectionPeople;
  }

  public void setSectionPeople(long sectionPeople) {
    this.sectionPeople = sectionPeople;
  }

}
