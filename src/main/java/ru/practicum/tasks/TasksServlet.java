package ru.practicum.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class Task {
  private Integer id;
  private String name;
  private String status;

  public Task() {
  }

  public Task(Integer id, String name, String status) {
	this.id = id;
	this.name = name;
	this.status = status;
  }

  public Integer getId() {
	return id;
  }

  public void setId(Integer id) {
	this.id = id;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public String getStatus() {
	return status;
  }

  public void setStatus(String status) {
	this.status = status;
  }
}

// client, order, item
public class TasksServlet extends HttpServlet {

  private Map<Integer, Task> idToTask = new HashMap<>();
  private ObjectMapper objectMapper = new ObjectMapper();
  private int counter = 0;

  {
	idToTask.put(25, new Task(25, "Задача 25", "NEW"));
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String requestUri = req.getRequestURI();

	String[] split = requestUri.split("/");
	String taskId = split[3];

	Task task = idToTask.get(Integer.parseInt(taskId));

	String taskAsJson = objectMapper.writeValueAsString(task);

	resp.getWriter().write(taskAsJson);
	resp.getWriter().flush();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	BufferedReader bodyReader = req.getReader();

	String bodyContent = bodyReader.readLine();

	Task task = objectMapper.readValue(bodyContent, Task.class);
	task.setId(counter++);
	idToTask.put(task.getId(), task);

	String addedTask = objectMapper.writeValueAsString(task);

	resp.getWriter().write(addedTask);
	resp.getWriter().flush();
  }
  
}
