package org.gdufs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String studentno;
    private String studentname;
    private String studentclass;
    private String sex;
    private String major;
    private String password;
    private String telephone;
    private String email;
    private String photo;


}