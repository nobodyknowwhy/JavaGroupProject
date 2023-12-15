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
public class employeeSignin {
    private long employeeNum;
    private String name;
    private long signinNum;
    private String signinTime;
    private String checkoutTime;
    private double workHour;
}
