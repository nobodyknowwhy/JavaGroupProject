package org.gdufs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long usersNum;
    private String name;
    private String gender;
    private String password;
    private String email;


}
