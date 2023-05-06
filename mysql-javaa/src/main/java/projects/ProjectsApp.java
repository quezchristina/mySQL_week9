package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;


public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectsService = new ProjectService();
	
	
		//writing the code that holds the list of operations
		
		//@formatter:off
		private List<String> operations = List.of(
				"1) Add a project"
				);
		//@formatter:on

 	public static void main(String[] args) {
 		new ProjectsApp().processUserSelections();
 		
 	}
	//instance method create
 	/*Method displays:
 	 * menu selections, gets a selection from the user, and acts on the 
 	 * selection
 	 */
 	private void processUserSelections() {
 		boolean done = false;
 		while(!done) {
 			try { 
 				int selection = getUserSelection();
 				
 				switch(selection) {
 				case - 1:
 					done = exitMenu();
 					break;
 					
 				case 1 :
 					createProject();
 					break;
 					
 				default:
 					System.out.println("\n" + selection + " is not a valid section. Try again.");
 					break;
 				}
 			}
 			catch(Exception e) {
 				System.out.println("\nError: " + e + "Try again.");
 			}
 		}	
	}
 	
	private void createProject() {
	String projectName = getStringInput("Enter the project name");
	BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
	BigDecimal actualHours = getDecimalInput("Enter the actual hours");
	Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
	String notes = getStringInput ("Enter the project notes");
	
	Project project = new Project();
	
	project.setProjectName(projectName);
	project.setEstimatedHours(estimatedHours);
	project.setActualHours(actualHours);
	project.setDifficulty(difficulty);
	project.setNotes(notes);
	
	Project dbProject = projectsService.addProject(project);
System.out.println("You have successfully created project: " + dbProject);	

	
		
	}
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if (Objects.isNull(input)) {
			return null;
		}
		try {
			//Creates the BigDecimal object and set it to 2 decimal places
			return new BigDecimal (input).setScale(2);
		}
		catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}
	private boolean exitMenu() {
		System.out.println("Existing the menu.");
		return true;
	}
	//Method call getUserSelection
 	// returns nothing. but prints operations & accepts user input as an Integer
	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter a menu selection");
// if value is null pass the string "Enter menu"
// return if input is null return -1 exits menu if not value of input return
		return Objects.isNull(input) ? -1 : input;
	}
	//method accepts input from the user and converts it to an Integer (can be null)
	// gets call getUserSelection()
	private Integer getIntInput(String prompt) {
	String input = getStringInput(prompt);
	
	if (Objects.isNull(input)) {
		return null;
	}
	try {
		return Integer.valueOf(input);
	}
	catch (NumberFormatException e) {
		throw new DbException(input + " is not a valid number.");
	}
	}
	//Method prints the prompt and gets the input from the user
	// other input methods call this method convert the input value to the appropriate type
	//other method call this method to collect String data from the user 
	private String getStringInput(String prompt) {
		System.out.println(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
 }
	//method prints each available section on a separate line
	private void printOperations() {
	System.out.println("\nThese are the available selections. Press the Enter key to quit");
	operations.forEach(line -> System.out.println("  " + line));
		
	}
}

