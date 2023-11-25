package org.gdufs.entity;


import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;

public class Application {

  private long applicationNum;

  private String name;
  private String gender;
  private String photo;
  private String nation;
  private java.sql.Date birthday;
  private String politicalStatus;
  private String degree;
  private String marriage;
  private String birthplace;
  private String IDNum;
  private String phone;
  private String email;
  private String applicationSection;
  private String applicationForm;

  public MultipartFile getApplicationForms() {
    return applicationForms;
  }

  public void setApplicationForms(MultipartFile applicationForms) {
    this.applicationForms = applicationForms;
  }

  private MultipartFile applicationForms;

  private MultipartFile photos;

  public MultipartFile getPhotos() {
    return photos;
  }

  public void setPhotos(MultipartFile photos) {
    this.photos = photos;
  }

  private String others;


  public long getApplicationNum() {
    return applicationNum;
  }

  public void setApplicationNum(long applicationNum) {
    this.applicationNum = applicationNum;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }


  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }


  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }


  public java.sql.Date getBirthday() {
    return birthday;
  }

  public void setBirthday(java.sql.Date birthday) {
    this.birthday = birthday;
  }


  public String getPoliticalStatus() {
    return politicalStatus;
  }

  public void setPoliticalStatus(String politicalStatus) {
    this.politicalStatus = politicalStatus;
  }


  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }


  public String getMarriage() {
    return marriage;
  }

  public void setMarriage(String marriage) {
    this.marriage = marriage;
  }


  public String getBirthplace() {
    return birthplace;
  }

  public void setBirthplace(String birthplace) {
    this.birthplace = birthplace;
  }


  public String getIDNum() {
    return IDNum;
  }

  public void setIDNum(String IDNum) {
    this.IDNum = IDNum;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getApplicationSection() {
    return applicationSection;
  }

  public void setApplicationSection(String applicationSection) {
    this.applicationSection = applicationSection;
  }


  public String getApplicationForm() {
    return applicationForm;
  }

  public void setApplicationForm(String applicationForm) {
    this.applicationForm = applicationForm;
  }

  public String getOthers() {
    return others;
  }

  public void setOthers(String others) {
    this.others = others;
  }

}
