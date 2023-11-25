package org.gdufs.entity;


public class User {

  private long usersNum;
  private String name;
  private String gender;
  private String password;
  private String email;


  public long getUsersNum() {
    return usersNum;
  }

  public void setUsersNum(long usersNum) {
    this.usersNum = usersNum;
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


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
