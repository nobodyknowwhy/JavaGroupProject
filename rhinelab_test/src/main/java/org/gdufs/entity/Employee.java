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
public class Employee {

    private long employeeNum;
    private String name;
    private String gender;
    private String photo;
    private String nation;
    private Date birthday;
    private String politicalStatus;
    private String degree;
    private String marriage;
    private String birthplace;
    private String idNum;
    private String phone;
    private String email;
    private Date entryTime;
    private String level;
    private double salary;
    private long sectionNum;
    private String status;
    private String password;
    private String identity;


}
