package com.example.springjpa.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjpa.entity.Projects;
import com.example.springjpa.exceptionhandlers.ProjectNotFoundException;
import com.example.springjpa.service.ProjectsService;

import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/")
public class ProjectsRestController {
	
	@Autowired
	ProjectsService projectsService;
	
	@PostMapping("/project/add/{name}")
	public Projects add(@PathVariable String name) {
		
		
		return projectsService.add(name);
		
	}
	
	@GetMapping("/project/{name}")
	public List<Projects> getProjectByName(@PathVariable String name) throws ProjectNotFoundException{
		
		return projectsService.getByName(name);
	}
	
	@GetMapping("/reactive/project/{name}")
	public Flux<Projects> getProjectByNameRx(@PathVariable String name) throws ProjectNotFoundException{
		
		return projectsService.getByNameRx(name);
	}
	
	@GetMapping(value= "/project",produces = {"application/json"})
	public List<Projects> getAllProjects(){
		
		return projectsService.getAll();
	}
	
	@GetMapping(value="/project/count", produces = {"application/json"})
	public long getProjectCount() {
		return projectsService.getCount();
	}
	
	@PostMapping(value="/project/postEmployee/{employeeName}")
	public String addEmployee(@PathVariable String employeeName) throws ClientProtocolException, IOException {
		return projectsService.addEmployee(employeeName);
		
	}

	/*
	 * @PostMapping("/project/add/employee/{employeeName}/{projectName}") public
	 * Projects addEmployee(@PathVariable String employeeName, @PathVariable String
	 * projectName ) throws ClientProtocolException, IOException {
	 * 
	 * 
	 * return projectsService.addEmployeeToProject(employeeName,projectName); }
	 */
	
	
	/*
	 * @PostMapping("/employee/project/add/{employeeName}/{projectName}") public
	 * Projects addEmpoyeeToProject(@PathVariable String employeeName, @PathVariable
	 * String projectName) {
	 * 
	 * 
	 * Projects projects=
	 * projectsService.addEmployeeToProject(employeeName,projectName);
	 * 
	 * 
	 * return projects; }
	 */
	
	
}
