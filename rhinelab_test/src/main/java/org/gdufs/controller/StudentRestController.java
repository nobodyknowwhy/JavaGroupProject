package org.gdufs.controller;

 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.gdufs.entity.Student;
import org.gdufs.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//直接返回内容，一般用于json等格式的数据，@Controller跳转到前端页面
@RestController
@RequestMapping(value="/rest")
public class StudentRestController {
    @Autowired
    StudentMapper studentMapper;

   
    //查找(用于修改)
    @RequestMapping("/getStudentByName")
    public Student findUserByName(String name, Model model) throws Exception {
    	Student student= studentMapper.getStudentByName(name);
        model.addAttribute("students", student);
        return student;
    }
    //遍历，默认返回josn格式
    @RequestMapping(value = "/getAllStudents", method = { RequestMethod.POST,	RequestMethod.GET })
    public List<Student> getAllUsers(Model model, @RequestParam(value = "start", defaultValue = "0") int start,
                           @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        PageHelper.startPage(start,size,true);
        List<Student> studentList=studentMapper.findAll();
        PageInfo<Student> pages = new PageInfo<Student>(studentList);
        model.addAttribute("pages", pages);
        return studentList;
    }
    
    //遍历，默认返回josn格式
    @RequestMapping(value = "/getAllStudentsPage", method = { RequestMethod.POST,	RequestMethod.GET })
    public PageInfo<Student> getAllUsersPage(Model model, @RequestParam(value = "start", defaultValue = "0") int start,
                           @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        PageHelper.startPage(start,size,true);
        List<Student> studentList=studentMapper.findAll();
        PageInfo<Student> pages = new PageInfo<Student>(studentList);
       
        model.addAttribute("pages", pages);
        return pages;
    }
    
	@PutMapping("/saveStudent")
	public void saveStudent(Student student){
		studentMapper.save(student);
	}
	   //删除
    @DeleteMapping("/deleteStudent")
    public String deleteStudent(@RequestParam(value = "id", required = true) int id) {
    	studentMapper.delete(id);
    	System.out.println(id);
        return "true";
    }
}


