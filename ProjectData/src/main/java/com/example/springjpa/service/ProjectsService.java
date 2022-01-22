package com.example.springjpa.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.example.springjpa.entity.Projects;
import com.example.springjpa.exceptionhandlers.ProjectNotFoundException;

import reactor.core.publisher.Flux;


public interface ProjectsService {

	Projects add(String name);

	List<Projects> getByName(String name) throws ProjectNotFoundException;

	List<Projects> getAll();

	//Projects addEmployeeToProject(String empoyeeName, String projectName) ;

	long getCount();

	Flux<Projects> getByNameRx(String name);

	String addEmployee(String employeeName) throws ClientProtocolException, IOException;

	//Projects addEmployeeToProject(String employeeName, String projectName) throws ClientProtocolException, IOException;

}
