package org.gdufs.service.impl;

import java.util.List;

import org.gdufs.entity.Student;
import org.gdufs.mapper.RhineLabMapper;
import org.gdufs.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class employeeServiceImpl implements EmployeeService {
	
	// 依赖注入
	@Autowired
	RhineLabMapper rhineLabMapper;
	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return rhineLabMapper.findAll();
	}

}
