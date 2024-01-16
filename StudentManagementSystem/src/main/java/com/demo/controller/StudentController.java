package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.Repository.StudentRepository;
import com.demo.entity.Student;
import com.demo.exceptioon.ResourceNotFoundException;

@Controller
@RequestMapping("/api")
public class StudentController{
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	// get all students
	@GetMapping("/students")
	public String getAllStudents(Model m){
		m.addAttribute("students", this.studentRepository.findAll());
		return "homePage";
	}
	
	
	// show student form
	@GetMapping("/students/add")
	public String showStudentForm(Model m) {
		Student student = new Student();
		m.addAttribute("student", student);
		return "showStudentForm";
	}
	
	
	// add student
	@PostMapping("/students")
	public String addStudent(@ModelAttribute Student student)  // using model Attribute we bind form with student entity.
	{
	 // System.out.println("test");
		this.studentRepository.save(student);
		return "redirect:/api/students";
	}
	
	
	// show update form
	@GetMapping("/student/edit/{id}")
	public String updateStudentRecord(@PathVariable Long id, Model m) {
		Student student = studentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not exits with id : " + id));
		// System.out.println(student);
		m.addAttribute("student", student);
		return "updateStudentForm";
	}
	
	
	// update student
	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id, @ModelAttribute Student student, Model m) {
		System.out.println("dddd");
		// get student from db using student id
		Student s = studentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not exits with id : " + id));
		
		// set the updated values
		s.setId(id);
		s.setFirstName(student.getFirstName());
		s.setLastName(student.getLastName());
		s.setGender(student.getGender());
		s.setEducation(student.getEducation());
		s.setDate(student.getDate());
		
		// save updated student obj
		this.studentRepository.save(s);
		
		return "redirect:/api/students";
	}
	
	
	@GetMapping("/student/delete/{id}")
	public String deleteStudent(@PathVariable Long id) {
		// get student from db using student id
		Student s = studentRepository.findById(id)
						.orElseThrow(()-> new ResourceNotFoundException("Employee not exits with id : " + id));
		this.studentRepository.delete(s);
		return "redirect:/api/students";
	}
	
	@GetMapping("/student/details/{id}")
	public String showStudentDetails(@PathVariable Long id, Model m) {
		
		// get student from db using student id
		Student s = studentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not exits with id : " + id));
		
		m.addAttribute("student", s);
		
		return "showStudentDetails";
	}
	
	
	

}
