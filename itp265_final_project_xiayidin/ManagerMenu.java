/**
 * Manager side Enum Menu;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

public enum ManagerMenu {
	VIEW_INSTRUCTORS("View all instructors"),
	VIEW_MEMBERS("View all members"),
	RENT_LOCKER("Rent lockers for instructors"),
	ADD_CLASS("Add new Personal Training class"),
	QUIT("Quit");
	
	private String option;
	
	private ManagerMenu(String option) {
		this.option = option; 
	}
	
	public String DisplayOption() {
		return this.option;
	}
	
	public static String getMenuOptions() {
		String str = "******** Menu for Managers ********";
		for(ManagerMenu o: ManagerMenu.values()) {
			str+= "\n" + o.ordinal() + ": " +o.DisplayOption();
		}
		return str;
	}
	
}

