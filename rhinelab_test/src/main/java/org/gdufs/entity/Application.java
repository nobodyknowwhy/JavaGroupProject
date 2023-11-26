package org.gdufs.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String others;
    private MultipartFile applicationForms;
    private MultipartFile photos;
}
