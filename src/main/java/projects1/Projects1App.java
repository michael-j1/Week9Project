package projects1;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects1.entity.Project;
import projects1.exception.DbException;
import projects1.service.Projects1Service;



public class Projects1App {
private Scanner scanner = new Scanner(System.in);
private Projects1Service projects1Service = new Projects1Service();
	
	//Used Scanner to obtain user input. 
	//input source System.in, 
	//Used Scanner to read from the console.
	//User types in selections and the Scanner 
	//reads the input and gives it to the application.

	//Added a private instance variable scanner. type java.util.Scanner. 
    //initialized to a new Scanner object. System.in to the constructor. 
	//scanner accepts user input from the Java console.

        //@ formatter:off
		private List<String> operations = List.of(
				"1) Add a project"
				);
		// @formatter:0n
//entry point for java application
	public static void main(String[] args) {
		new Projects1App().processUserSelections();
		
	}

			
			private void processUserSelections() {
                  boolean done = false;           //added local variable 

                 while(!done) { //while loop until done is true  added try/catch to catch exception
	           //Concatenating the Exception object to a string literal 
	           //Java implicitly calls the toString() method
	try {
		int selection = getUserSelection(); // int variable named selection to return value from getUserSelection()  
		   switch(selection) {//switch statement code that will process the user's selection. Since the user enters an Integer value (the menu selection number) use a switch statement to process the selection. 

		   case -1:
			   done = exitMenu();
			   break;
		   
		   case 1:
			   createProject();
			   break;
			 default:
				 System.out.println("\n" + selection + " is not valid selection. Try again.");
				 break;
		   }
	
			   
	}
	catch(Exception e) {
		System.out.println("\nError: " + e + "Try again."); 
			
	}
	}
	}
	private void createProject() {	//gather user input for project row/call project service to create row
		String projectName = getStringInput("enter project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter difficulty (1-5)");
		String notes = getStringInput("Enter project notes");
		 
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projects1Service.addProject(project);
		System.out.println("you successfully created project! " + dbProject);
	}
	
	
	
	
	//prints prompt gets user input from console converts input to integer
	private Integer getIntInput(String prompt) { // @param prompt the prompt to print
String input = getStringInput(prompt);          //nothing entered {@code null} returned. or input converted to integer 

if(Objects.isNull(input)) {     //@throws DbException thrown if input is not valid integer
return null;
	}
	try {
		return Integer.valueOf(input);//try/catch  to test that the value returned by getStringInput() can be converted to an Integer
	}

	catch(NumberFormatException e) {
		throw new DbException(input + " is not a valid number.");
	}
	}
	
	
	
	
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
		return null;}
	try { //BigDecimal object set to two decimal places (the scale) 
		return new BigDecimal(input).setScale(2);
	}
	
	catch(NumberFormatException e) {
		throw new DbException(input + " is not a valid decimal number.");
	}
	}
	
	
	private boolean exitMenu() { //when user wants to exit return @CODE TRUE TO TERMINaTE APP
		System.out.println("Exit menu.");
		return true;
	}
		
	
	
	private String getStringInput(String prompt) {// prints prompt gets user info if nothin entered {@code null} otherwise trimmed input returned 
		System.out.println(prompt + ": ");
		String input =scanner.nextLine();
		return input.isBlank() ? null : input.trim();
	}
	
	
	
	private int getUserSelection() { //prints available menu selections  converts to int or -1 if no selection
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		return Objects.isNull(input) ? -1 : input;
	}
		
	
	
	private void printOperations() {		//	Print all the available menu selections, one on each line

			System.out.println("\nThese are available selections. Press enter to quit:");
			
		for(String line : operations) {
			System.out.println(" " + line);//prints each available selection on a separate line in the console. In the printOperations() method:
		}

		}
		
		
	

}

