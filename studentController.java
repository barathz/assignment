package com.ibm.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class studentController {
	@Autowired
	studentRepo repo;
	
	@GetMapping("/students/count")
	int getCountOfLearners() {
		return repo.getCountOfLearners();
	}
	
	@GetMapping("/students/all")
	List<student> getallstudents(){
		return repo.getallstudents();
	}
	
	@GetMapping("/students/{id}")
	student getstudentsbyid(@PathVariable int id){
		return repo.getstudentsbyid(id);
	}
	@PostMapping("/students")
	void addnewstudent(@RequestBody student studentz)
	{
		repo.addnewstudent(studentz);
	}
	
	@PutMapping("/students/{id}")
	void updatedriversbyid(@RequestBody student studentz,@PathVariable int id)
	{
	repo.updatestudent(studentz, id);
	}
	
	@DeleteMapping("/students/{id}")
	void deletestudentsbyid(@PathVariable int id) {
		repo.deletestudentsbyid(id);
	}
	
}
