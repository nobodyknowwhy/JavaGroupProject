package org.gdufs.mapper;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.annotations.*;
import org.gdufs.entity.*;


@Mapper
public interface RhineLabMapper {

    @Select("select * from product")
    List<Product> findAllProduct();
    @Select("select * from apply")
    List<Apply> findAllapply();

    @Insert("insert into signin (signinTime, checkoutTime, employeeNum, workHour) values (#{signinTime}, #{checkoutTime}, #{employeeNum}, #{workHour})")
    public void Tosignin(Signin signin);

    @Select("select signin.employeeNum,employee.name,signinTime,checkoutTime,workHour from signin JOIN employee ON signin.employeeNum=employee.employeeNum")
    List<employeeSignin> findAllSignin();

    @Select("select signin.employeeNum,employee.name,signinTime,checkoutTime,workHour from signin JOIN employee ON signin.employeeNum=employee.employeeNum where name=#{name}")
    List<employeeSignin> findAllSigninByName(String name);

    @Update("UPDATE employee SET degree=#{degree}, phone=#{phone}, email=#{email}, salary=#{salary}, sectionNum=#{sectionNum}, status=#{status} WHERE employeeNum=#{employeeNum}")
    void updateEmployee(Employee employee);

    @Update("UPDATE product SET quantity=#{quantity}, unitPrice=#{unitPrice}, introduction=#{introduction}, userComment=#{userComment}, investment=#{investment} WHERE productNum=#{productNum}")
    void updateProduct(Product product);

    //查询姓名
    @Select("select * from employee where name=#{name}")
    List<Employee> findEmployeeByName(String name);
    //查询产品
    @Select("select * from product where name=#{name}")
    List<Product> findproductByName(String name);

    @Select("select * from employee")
    List<Employee> findAllMember();

    @Insert("insert into application (name, gender, photo, nation, birthday, politicalStatus, degree, marriage, birthplace, IDNum, phone, email, applicationSection, applicationForm) values (#{name}, #{gender}, " +
            "#{photo}, #{nation}, #{birthday}, #{politicalStatus}, #{degree}, #{marriage}, #{birthplace}, #{IDNum}, #{phone}, #{email}, #{applicationSection}, #{applicationForm})")
    public int applicationSave(Application application);

    @Insert("insert into application (name, gender, photo, nation, birthday, politicalStatus, degree, marriage, birthplace, IDNum, phone, email, applicationSection, applicationForm) values (#{name}, #{gender}, " +
            "#{photo}, #{nation}, #{birthday}, #{politicalStatus}, #{others}, #{marriage}, #{birthplace}, #{IDNum},  #{phone}, #{email}, #{applicationSection}, #{applicationForm});")
    public int applicationSaveO(Application application);

    @Select("select * from user where email=#{email} and password=#{password}")
    List<User> checkUser(@Param("email") String email, @Param("password") String password);

    @Select("select * from user where email=#{email}")
    List<User> checkEmail(@Param("email") String email);

    @Select("select * from user where name=#{name}")
    List<User> checkUserName(@Param("name") String name);

    @Insert("insert into user (name, gender, password, email) values (#{name}, #{gender}, #{password}, #{email})")
    public int userSave(User user);

    @Select("SELECT * FROM purchase WHERE phone = #{phone} ORDER BY CASE status WHEN '待审核' THEN 1 WHEN '进行中' THEN 2 ELSE 3 END, purchaseDate DESC")
    List<Purchase> getPurchase(String phone);

    @Select("SELECT * FROM project WHERE phone = #{phone} ORDER BY CASE status WHEN '待审核' THEN 1 WHEN '进行中' THEN 2 ELSE 3 END")
    List<Project> getProject(String phone);

    @Select("select * from employee where email=#{email} and level='2'")
    List<Employee> checkAdmin(@Param("email") String email);

    @Select("select * from employee where email=#{email} and level='1'")
    List<Employee> checkEmployee(@Param("email") String email);

    @Select("select * from employee where employeeNum=#{id} and password=#{password}")
    Employee checkEmployeeByEmployee(@Param("id") long id, @Param("password") String password);

    @Update("UPDATE apply SET status='已同意' WHERE applyNum=#{applyNum}")
    public void applyUpdateAgree(int applyNum);

    @Update("UPDATE apply SET status='已拒绝' WHERE applyNum=#{applyNum}")
    public void applyUpdateDisgust(int applyNum);

    @Update("UPDATE employee SET sectionNum=#{sectionNum}, salary=#{salary} WHERE employeeNum=#{employeeNum}")
    public void employeeUpdateByApply(Employee employee);

    //更新申请表的数据
    @Insert("insert into apply (employeeNum, name, origionSection, newSection, origionSalary, newSalary, applyReason) " +
            "values (#{employeeNum}, #{name}, #{origionSection}, #{newSection}, #{origionSalary}, #{newSalary}, #{applyReason})")
    public void applySave(Apply apply);

    @Select("select employeeNum from apply where employeeNum=#{id} and applyReason=#{applyReason}")
    public long checkapply(@Param("id") long id,@Param("applyReason") String applyReason);

    @Select("SELECT * FROM project")
    List<Project> getProjectAll();

    @Delete("delete from project where projectNum= #{projectNum} ")
    public void deleteProject(int projectNum);

    @Select("SELECT employeeNum, name, photo, gender, nation, birthday, politicalStatus, birthplace, phone, email, entryTime, sectionNum, status from employee where employeeNum=#{bianhao} AND password=#{password}")
    List<Employee> getEmployeeAll(int bianhao, String password);

    @Select("SELECT * FROM purchase")
    List<Purchase> getPurchaseAll();

    @Select("SELECT * FROM application")
    List<Application> getApplicationAll();

    @Delete("delete from purchase where purchaseNum= #{purchase} ")
    public void deletePurchase(int purchase);

    @Select("SELECT * FROM product where productNum = #{productNum}")
    List<Product> getProductOne(@Param("productNum") long productNum);

    @Select("SELECT * FROM employee where email = #{email}")
    List<Employee> isAlreadyEmployee(@Param("email") String email);

    @Insert("insert into project (phone, name, type, meaning, totalTime, expenditure, status) values (#{phone}, #{name}, #{type}, #{meaning}, #{totalTime}, #{expenditure}, '待审核')")
    public int launch(Project project);
}
