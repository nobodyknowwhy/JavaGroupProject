package org.gdufs.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.gdufs.entity.*;


@Mapper
public interface RhineLabMapper {
//    @Select("select * from student")
//    List<Student> findAll();
//
//    @Insert(" insert into student  (studentNo,studentName,password) values (#{studentno},#{studentname},#{password}) ")
//    public int save(Student student);
//
//    @Delete(" delete from student where id= #{id} ")
//    public void delete(int id);

    @Select("select * from employee")
    List<Employee> findAllMember();

    @Insert("insert into application (name, gender, photo, birthday, politicalStatus, degree, marriage, birthplace, IDNum, phone, email, applicationSection, applicationForm) values (#{name}, #{gender}, " +
            "#{photo}, #{birthday}, #{politicalStatus}, #{degree}, #{marriage}, #{birthplace}, #{IDNum}, #{phone}, #{email}, #{applicationSection}, #{applicationForm})")
    public int applicationSave(Application application);

    @Insert("insert into application (name, gender, photo, birthday, politicalStatus, degree, marriage, birthplace, IDNum, phone, email, applicationSection, applicationForm) values (#{name}, #{gender}, " +
            "#{photo}, #{birthday}, #{politicalStatus}, #{others}, #{marriage}, #{birthplace}, #{IDNum},  #{phone}, #{email}, #{applicationSection}, #{applicationForm});")
    public int applicationSaveO(Application application);

    @Select("select * from user where email=#{email} and password=#{password}")
    List<User> checkUser(@Param("email") String email, @Param("password") String password);

    @Select("select * from user where email=#{email}")
    List<User> checkEmail(@Param("email") String email);






}
