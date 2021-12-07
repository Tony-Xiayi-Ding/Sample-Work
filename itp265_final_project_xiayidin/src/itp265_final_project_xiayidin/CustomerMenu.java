/**
 * Customer Menu Options using Enum;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

public enum CustomerMenu {
	LOG_IN("Sign in or create an account"),
	LOG_OUT("Log out of your account"),
	VIEW_CLASS("View all classes"),
	ADD_CLASS("Schedule a class"),
	DROP_CLASS("Cancel a class reservation"),
	VIEW_MYCLASS("View my class schedule"),
	VIEW_BILLING("View my current billing statement"),
	RENT_A_LOCKER("Rent a locker"),
	QUIT("Quit");
	
	private String description;
	
	private CustomerMenu(String description) {
		this.description = description;
	}
	
	//Originally called option; maybe description is better
	public String DisplayOption() {
		return this.description;
	}
	
		
	public static String getMenuOptions() {
		String str = "******** Menu For Customers ********";
		for(CustomerMenu m: CustomerMenu.values()) {
			str+= "\n" + m.ordinal() + ": " +m.DisplayOption();
		}
		return str;
	}
}

