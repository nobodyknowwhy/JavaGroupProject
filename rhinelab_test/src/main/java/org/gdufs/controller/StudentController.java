package org.gdufs.controller;

 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.gdufs.entity.Student;
import org.gdufs.mapper.StudentMapper;
import org.gdufs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping(value="/")
public class StudentController {
    @Autowired
    StudentMapper studentMapper;
    
    @Autowired
    StudentService studentService;
    //添加
    @RequestMapping("/addStudent")
    public String listStudent(Student student) throws Exception {
    	studentMapper.save(student);
        return "redirect:listStudent";
    }
    //删除
    @RequestMapping("/deleteStudent")
    public String deleteStudent(int id) throws Exception {
    	studentMapper.delete(id);
        return "redirect:listStudent";
    }
    //修改
    @RequestMapping("/updateStudent")
    public String updateStudent(Student student) throws Exception {
    	studentMapper.update(student);
        return "redirect:listStudent";
    }
    //查找(用于修改)
    @RequestMapping("/findStudent")
    public String findStudent(int id, Model model) throws Exception {
    	Student student= studentMapper.get(id);
        model.addAttribute("student", student);
        return "editstudent";
    }
    
    //查找(用于修改)
    @RequestMapping("/searchStudentUi")
    public String searchStudentByName( Model model) throws Exception {
        return "searchStudent";
    }
    
    //查找(用于修改)，参数名同表单元素名要相同
    @RequestMapping("/findStudentByName")
    public String findUserByName(String name, Model model) throws Exception {
    	Student student= studentMapper.getStudentByName(name);
    	if(student==null)
    		return "redirect:searchStudentUi";
        model.addAttribute("student", student);
        return "editstudent";
    }
    
    //遍历
    @RequestMapping(value = "/listStudent", method = { RequestMethod.POST,	RequestMethod.GET })
    public String listUser(Model model, @RequestParam(value = "start", defaultValue = "0") int start,
                           @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        PageHelper.startPage(start,size,true);
//        List<Student> studentList=studentMapper.findAll();
        List<Student> studentList=studentService.getAllStudents();
        PageInfo<Student> page = new PageInfo<Student>(studentList);
        model.addAttribute("pages", page);
       
        return "liststudent";
    }
}


