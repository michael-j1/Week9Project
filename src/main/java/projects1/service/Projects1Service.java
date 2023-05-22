package projects1.service;
import projects1.dao.ProjectDao;
import projects1.entity.Project;
public class Projects1Service {
 private  ProjectDao projectDao = new ProjectDao();
 //method calls the DAO class to insert a project row
 public Project addProject(Project project) {
	 return projectDao.insertProject(project);
 }
}
