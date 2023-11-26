package org.gdufs.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
