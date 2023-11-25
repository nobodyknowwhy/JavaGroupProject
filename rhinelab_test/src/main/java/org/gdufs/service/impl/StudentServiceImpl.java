package org.gdufs.service.impl;

import java.util.List;

import org.gdufs.entity.Student;
import org.gdufs.mapper.StudentMapper;
import org.gdufs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
	
	// 依赖注入
	@Autowired
	StudentMapper studentMapper;
	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentMapper.findAll();
	}

}
