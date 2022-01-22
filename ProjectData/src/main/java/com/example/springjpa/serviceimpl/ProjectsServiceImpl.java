package com.example.springjpa.serviceimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.springjpa.entity.Projects;
import com.example.springjpa.exceptionhandlers.ProjectNotFoundException;
import com.example.springjpa.model.Employee;
import com.example.springjpa.repository.ProjectsRepository;
import com.example.springjpa.service.ProjectsService;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

@Service
public class ProjectsServiceImpl implements ProjectsService {

	@Autowired
	ProjectsRepository projectsRepository;
	
	@Autowired
	@Qualifier("jdbcScheduler")
	private Scheduler jdbcScheduler;

	

	@Override
	public Projects add(String name) {
		Projects project = new Projects(null, name);
		//project.setProjectName(name);
		return projectsRepository.save(project);
	}

	@Override
	public List<Projects> getByName(String name) throws ProjectNotFoundException {
		if (projectsRepository.findByProjectName(name).isEmpty()
				|| projectsRepository.findByProjectName(name).size() == 0) {
			throw new ProjectNotFoundException("Project not found.");
		}
		return projectsRepository.findByProjectName(name);
	}

	@Override
	public List<Projects> getAll() {
		return projectsRepository.findAll();
		// return projectsRepository.findAllProjectNames();
	}

	/*
	 * @Override public Projects addEmployeeToProject(String employeeName, String
	 * projectName) {
	 * 
	 * Projects project = new Projects(); project.setProjectName(projectName);
	 * 
	 * List<Employee> emp = employeeRepository.findByName(employeeName) ;
	 * if(emp.isEmpty()||emp.size()==0) { throw new
	 * EmployeeNotFoundException("This employee does not exist.");
	 * 
	 * } else {
	 * 
	 * 
	 * project.setEmployee(emp.get(0)); }
	 * 
	 * 
	 * return projectsRepository.save(project);
	 * 
	 * return null;}
	 */

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return projectsRepository.getCount();
	}
	/*
	 * @Override public Projects addEmployeeToProject(String employeeName, String
	 * projectName) throws ClientProtocolException, IOException { HttpUriRequest
	 * request = new HttpGet( "http://localhost:8086//employee//"+employeeName );
	 * 
	 * CloseableHttpResponse response = HttpClientBuilder.create().build().execute(
	 * request ); HttpEntity entity = response.getEntity();
	 * 
	 * String res = null;
	 * 
	 * InputStream instream = entity.getContent();
	 * 
	 * byte[] bytes = IOUtils.toByteArray(instream);
	 * 
	 * res = new String(bytes, "UTF-8");
	 * 
	 * instream.close(); // Long emloyeeId= res.substring(0, 0); //Projects project
	 * = new Projects(null, projectName, null);
	 * 
	 * return null; }
	 */

	@Override
	public Flux<Projects> getByNameRx(String name) {
		Flux<Projects> defer=Flux.defer(()->Flux.fromIterable(this.projectsRepository.findByProjectName(name)));
		return defer.subscribeOn(jdbcScheduler);
	}
	
	private String res;
	//Implementation not yet complete
	//Need to properly send the request with body as the body is being passed as null to the Employee Service
	@Override
	public String addEmployee(String employeeName) throws ClientProtocolException, IOException {
		
		HttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost("http://localhost:8086//employee//save//");
		
		httpPost.setEntity(new Employee(null,employeeName,null));

		HttpResponse response = httpClient.execute(httpPost);
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		  InputStream instream = entity.getContent(); 
		  byte[] bytes = IOUtils.toByteArray(instream);
			 
		  res = new String(bytes, "UTF-8");  
		}
		return res;
	}

}
