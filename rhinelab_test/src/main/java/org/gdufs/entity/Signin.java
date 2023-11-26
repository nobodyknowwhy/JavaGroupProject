package org.gdufs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Signin {

    private long signinNum;
    private long employeeNum;
    private String signinTime;
    private String checkoutTime;
    private double workHour;


}
