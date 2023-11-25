package org.gdufs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.gdufs.entity.Student;

@Mapper
public interface StudentMapper {
    @Select("select * from student")
    List<Student> findAll();

    @Insert(" insert into student  (studentNo,studentName,password) values (#{studentno},#{studentname},#{password}) ")
    public int save(Student student);

    @Delete(" delete from student where id= #{id} ")
    public void delete(int id);

    @Select("select * from student where id= #{id} ")
    public Student get(int id);

    @Update("update student set studentName=#{studentname}, password=#{password} where studentNo=#{studentno} ")
    public int update(Student student);
    
    @Select("select * from student where studentName= #{studentName} ")
    public Student getStudentByName(String studentName);

}
