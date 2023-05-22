package projects1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import projects1.entity.Project;
import projects1.exception.DbException;
import provided.util.DaoBase;
@SuppressWarnings("unused")
public class ProjectDao extends DaoBase { //extends DaoBase

	
	
	//constants with the table names. It's a good idea to add constants for values that are used over and over again in a class. The table names are used by all the methods that write to or read from the tables.

private static final String CATEGORY_TABLE = "category";
private static final String MATERIAL_TABLE = "material";
private static final String PROJECT_TABLE = "project";
private static final String PROJECT_CATEGORY_TABLE = "project_category";
private static final String STEP_TABLE = "step";


public Project insertProject(Project project) {
	//@formatter:off
	String sql = ""//SQL statement will insert the values from the Project object passed to the insertProject() method.
			+ "INSERT INTO " + PROJECT_TABLE + " "
			+ "(project_name, estimated_hours, actual_hours, difficulty, notes) " //correct spaces between words
			+ "VALUES "
			+ "(?, ?, ?, ?, ?)";//question marks as placeholder values for the parameters passed to the PreparedStatement. 
	// @formatter:on
	
	//Obtain a connection from DbConnection.getConnection(). Assign it a variable of type Connection named conn in a try-with-resource statement.
	//Catch the SQLException in a catch block added to the try-with-resource. From within the catch block, throw a new DbException. The DbException 
	//constructor should take the SQLException object passed into the catch block.

	try(Connection conn = DbConnection.getConnection()) {
		startTransaction(conn);
		//Pass the SQL statement as a parameter to conn.prepareStatement(). 
		//Add a catch block to the inner try block that catches Exception. In the catch block, roll back the transaction and throw a DbException initialized with the Exception object passed into the catch block. 
		//This will ensure that the transaction is rolled back when an exception is thrown.

		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			//In this step you will set the project details as parameters in the PreparedStatement object. Inside the inner try block, set the parameters on the Statement. 
			//Use the convenience method in DaoBase setParameter(). This method handles null values correctly. Add these parameters: projectName, estimatedHours, actualHours, difficulty, and notes
			setParameter(stmt, 1, project.getProjectName(), String.class);
			setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
		    setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
		    setParameter(stmt, 4, project.getDifficulty(), Integer.class);
		 	setParameter(stmt, 5, project.getNotes(), String.class);
		 	stmt.executeUpdate();//save the project details. Perform the insert by calling executeUpdate() on the PreparedStatement object
		 	
		 	Integer projectId = getLastInsertId(conn, PROJECT_TABLE);//Obtain the project ID (primary key) by calling the convenience method in DaoBase, getLastInsertId(). Pass the Connection object and the constant PROJECT_TABLE to getLastInsertId(). Assign the return value to an Integer variable projectId.

		 	commitTransaction(conn);//Commit the transaction by calling the convenience method in DaoBase, commitTransaction(). Pass the Connection object to commitTransaction() as a parameter.

		 	
		 	project.setProjectId(projectId);//Set the projectId on the Project object that was passed into insertProject and return it.

		 	return project;
		}
		catch(Exception e) {
			rollbackTransaction(conn);
			throw new DbException(e);
		}
	}
	catch(SQLException e) {
		throw new DbException(e);
		}
	}

}
