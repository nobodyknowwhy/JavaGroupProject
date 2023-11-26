package org.gdufs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private long projectNum;
    private String phone;
    private String name;
    private String type;
    private String meaning;
    private long totalTime;
    private double expenditure;
    private String status;
}
