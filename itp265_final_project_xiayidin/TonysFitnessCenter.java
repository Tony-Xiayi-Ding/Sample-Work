/**
 * TonysFitnessCenter, the ultimate program;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;
 import java.util.*;
 import java.io.*;
 
 public class TonysFitnessCenter {
		private FitnessCenter fitnessCenter; //takes care of all the reading and writing to file for FitnessClasses
		private Map<ClassType, List<FitnessClass>> classMap;
		private MemberLogin login; //takes care of all the login/create account/read from file/ write to file regarding members
		private UI helper; //Used Kendra's Code
		private Member currentMember; //this is the current member that's logged in 
		private static int PWD = 2020;
		
		/**
		 * The constructor will set up the classMap by reading from the class list file
		 * Will also set up login system which reads from a file of existing member information
		 */
		public TonysFitnessCenter() {
			fitnessCenter = new FitnessCenter();
			classMap = fitnessCenter.setUpFitnessClass(); 
			helper = new UI();
			login = new MemberLogin(); 
			
		}
		
		
		public void run() {
			System.out.println("Hi! Welcome to Tony's Fitness Center!");
			boolean isDone = false;
			while(!isDone) {
				int menuOption = helper.inputInt("Enter 1 for Customer's Menu and 2 for Manager's Menu", 1, 2);
				if (menuOption == 1) {
					runCustomerMenu();
				}
				else{
					int p = helper.inputInt("Please enter the 4-digit password to Manager's Menu. (Hint: Which year did Donald Trump lose in Presidential Election?)");
					while (p!=PWD) {
						System.out.println("Sorry. The password is wrong. Please try again.");
						p = helper.inputInt("Please enter the password to Manager's Menu. (Hint for grader: the password is at the top of the program)");
					}
					System.out.println("Password Correct! You have succesffully entered the Manager's Menu.");
					runManagerMenu();
				}
				isDone = helper.inputYesNoAsBoolean("Do you want to leave the system?(y/n)");
			}
			System.out.println("Thank you for using Tony's Fitness Center reservation system. Have a nice day! Bye!");
		}
		

		/**
		 * This menu is used for managing instructors and members; 
		 * Users have to input the correct password in order to get here;
		 */
		private void runManagerMenu() {
			int choice = helper.inputInt(ManagerMenu.getMenuOptions(),0,ManagerMenu.values().length-1);
			ManagerMenu option = ManagerMenu.values()[choice];
			while(option != ManagerMenu.QUIT) {
				switch(option) {
				case VIEW_INSTRUCTORS:
					viewInstructors();
					break;
				case VIEW_MEMBERS:
					viewMembers();
					break;
				case RENT_LOCKER:
					rentLockerForInstructors();
					break;
				case ADD_CLASS:
					addNewPersonalTrainingClass();
					break;
				case QUIT:
					System.out.println("Goodbye");
					break;
				}
				helper.inputLine("Hit enter to continue~");
				choice = helper.inputInt(ManagerMenu.getMenuOptions(), 0, ManagerMenu.values().length-1);
				option = ManagerMenu.values()[choice];
			}
			
		}

		
		/**
		 * This menu is used to interact with users;
		 * Used by members of Fitness Center;
		 */
		private void runCustomerMenu() {
			int choice = helper.inputInt(CustomerMenu.getMenuOptions(), 0, CustomerMenu.values().length-1);//get the choice from the user
			CustomerMenu option = CustomerMenu.values()[choice]; //get the corresponding menu option
			while(option != CustomerMenu.QUIT) {
				switch(option) {
					case LOG_IN:
						memberLogin();
						break;
					case LOG_OUT:
						memberLogout();
						break;
					case VIEW_CLASS:
						printClassByCategory();
						break;
					case ADD_CLASS:
						addClass();
						break;
					case DROP_CLASS:
						removeClass();
						break;
					case VIEW_MYCLASS:
						viewMyClass();
						break;
					case VIEW_BILLING:
						viewMyBill();
						break;
					case RENT_A_LOCKER:
						rentLockerForMembers();
						break;
					case QUIT:
						quit();
						break;
				}
				helper.inputLine("Hit enter to continue~");
				choice = helper.inputInt(CustomerMenu.getMenuOptions(), 0, CustomerMenu.values().length-1);
				option = CustomerMenu.values()[choice];
			}
		}


		/**
		 * This method allows the logged in member to view his/her bill, also ask if the user want to view details
		 */
		private void viewMyBill() {
			if (checkCurrentMember()) {
				calcualteBill();
				String str = ("Your total billing is ");
				str += String.format("$%.2f", currentMember.getBilling());//formatting the digits
				System.out.println(str);
				boolean viewDetail = helper.inputYesNoAsBoolean("Do you want to view details of your bill?(y/n)");
				if(viewDetail) {
					viewDetailedBilling();
				}
			}
		}
		
		
		/**
		 * This method will print out the detailed information of that member's bill
		 */
		public void viewDetailedBilling() {
			//get the list of registered class associated with the member
			ArrayList<FitnessClass> registeredClass = currentMember.getRegisteredClass();
			String str = "Here is your detailed billing:\n";
			//if the current member is a paid member, he/she will have membership fee and might have locker rental fee
			if(currentMember.getClass().equals(PaidMember.class)) {
				PaidMember pm = (PaidMember)currentMember;
				str += String.format("$%.2f: ",pm.getMembershipFee());
				str += "Membership Fee\n";
				if (pm.gethasLocker()) {
					str += String.format("$%.2f: ", pm.getRentalFee());
					str += "Locker Rental Fee\n";
				}
			}
			//iterate through the list of class and get the cost of each item
			for(FitnessClass fc: registeredClass) {
				double cost = fc.calculateCost(currentMember); 
				str += String.format("$%.2f: ", cost);//formatting the digits
				str += fc.getName();
				str += "\n";	
			}
			System.out.println(str);
		}
			

		/**
		 * This method will calculate the total billing for the current customer
		 */
		private void calcualteBill() {
			ArrayList<FitnessClass> registeredClass = currentMember.getRegisteredClass();
			//iterate through the registered class list of current member and calculate the cost
			for (FitnessClass fc: registeredClass) {
				double cost = fc.calculateCost(currentMember);
				currentMember.addBilling(cost);
			}
		}

		
		/**
		 * This method will log out the current member
		 */
		private void memberLogout() {
			if (currentMember != null) {
				boolean confirm = helper.inputYesNoAsBoolean("Are you sure you want to log out?(y/n)");
				if (confirm) {
					System.out.println("Logged "+ currentMember.getName() +" out of the system");
					currentMember = null;
				}
			}
			else {
				System.out.println("Attempt failed. There is no current member logged in.");
			}
		}

		
		/**
		 * This method will display the class current member has registered
		 */
		private boolean viewMyClass() {
			boolean hasClass = true;
			if(checkCurrentMember()) {
				if(!currentMember.getRegisteredClass().isEmpty()){
					String s = currentMember.viewClass();
					System.out.println(s);
				}
				else {
					System.out.println("You haven't registered for any class yet.");
					hasClass = false;
				}
			}
			return hasClass;
		}

		
		/**
		 * This method handles scheduling classes
		 * 
		 */
		private void addClass() {
			if (checkCurrentMember()) {
				displayCurrentMember();
				System.out.println("What type of classes are you interested in scheduling today?");
				FitnessClass fc = chooseClass();
				boolean add = fc.addParticipant(currentMember);
				if (add) {
					System.out.println("You have successfully scheduled the class.");
					currentMember.addClass(fc);
				}
			}
			
		}
		
		
		/**
		 * This method handles dropping classes
		 * 
		 */
		private void removeClass() {
			if (checkCurrentMember()) {
				displayCurrentMember();
				if(viewMyClass()){//check if the current member has registered for any class
					FitnessClass fc = chooseClass(currentMember);
					boolean remove = fc.removeParticipant(currentMember); //return whether the member is removed from the class list
					if (remove) {
						System.out.println("You have cancelled the class.");
						currentMember.dropClass(fc);
					}
				}	
			}
		}
		
		
		/**
		 * This method will print out all classes offered in a friendly way
		 */
		private void printClassByCategory() {
			boolean hasCurrentMember = checkCurrentMember();
			if(hasCurrentMember) {
				for(ClassType key: classMap.keySet()) {
					System.out.println(key+":");
					List<FitnessClass> classList = classMap.get(key);
					int number = 1;
					Collections.sort(classList);
					for(FitnessClass fc: classList) {
						System.out.println(number + ": "+fc);
						number+=1;
						System.out.println();
					}
					System.out.println();
				}
			}
		}

		
		//This method handles member login
		private void memberLogin() {
			if(currentMember == null) {
				String choice = helper.inputWord("Would you like to sign in or create a new account? (signin/create)","signin", "create");
				if (choice.equalsIgnoreCase("signin")) {
					currentMember = login.memberSignin();			
				}
				else {
					currentMember = login.createMember();
				}
			}
			else {
				System.out.println("You are already logged in. Please log out first if you want to sign in to another account.");
			}
		}

		
		//This method will display classes of specific type and ask the user to choose
		public FitnessClass chooseClass(){
			ClassType type;
			int i = 1;
			int choice = helper.inputInt("Enter 1 for Group Class or 2 for Personal Training", 1, 2);
			if(choice==1) {
				type = ClassType.GroupExercise;
			}
			else {
				type = ClassType.PersonalTraining;
			}
			List<FitnessClass> list = classMap.get(type); //get the list the user chooses		
			
			for(FitnessClass fc: list) {
				System.out.println(i+". "+fc);
				i ++;
			}
			int num = helper.inputInt("Which class would you like to choose?", 1, list.size());
			return list.get(num-1);
		}
		
		
		//This version of chooseClass is for removing classes
		public FitnessClass chooseClass(Member m) {
			ArrayList<FitnessClass> list = m.getRegisteredClass();
			int choice = helper.inputInt("Which class would you like to cancel today?");
			FitnessClass fc = list.get(choice-1);
			return fc;
		}
		
		
		public void displayCurrentMember(){
			System.out.println("Current member logged in is: "+currentMember.getName());
		}
		
		
		public boolean checkCurrentMember() {
			boolean hasCurrentMember = false;
			if (currentMember == null) {
				System.out.println("Please log in first.");
			}
			else {
				hasCurrentMember = true;
			}
			return hasCurrentMember;
		}
			

		/**
		 * This method handles renting lockers for PaidMembers
		 */
		private void rentLockerForMembers() {
			boolean hasCurrentMember = checkCurrentMember();
			if(hasCurrentMember) {
				if (currentMember.getClass().equals(PaidMember.class)) { //only paid member could rent a locker
					PaidMember paidMember = (PaidMember)currentMember;
					boolean confirm = helper.inputYesNoAsBoolean("Do you want to rent a locker for $" + paidMember.getRentalFee()+"?(y/n)");
					if (confirm) {
						paidMember.rentLockers();
					}
				}
				else {
					System.out.println("Sorry, but only paid members can rent a locker :(");
				}
			}
		}

		
		/**
		 * This method handles renting a locker for specific instructor
		 */
		private void rentLockerForInstructors() {
			Instructor i = chooseInstructor();
			i.rentLockers();
		}

		
		/**
		 * This method allows the manager to choose an instructor
		 */
		private Instructor chooseInstructor() {
			ArrayList<Instructor> list = fitnessCenter.getInstructorList(); //get the instructor list
			String str = "Please choose an instructor: ";
			int i = 1;
			for (Instructor instructor: list) {
				str += "\n";
				str += i;
				str += ". ";
				str += instructor.getName();
				i ++;
			}
			int choice = helper.inputInt(str, 1, list.size());
			Instructor instructor = list.get(choice-1);
			return instructor;
		}

		private void viewMembers() {
			System.out.println("Here's the information of all the members at Tony's Fitness Center:");
			login.viewAllMembers();
		}


		private void viewInstructors() {
			System.out.println("Here's the information of all the instructors at Tony's Fitness Center:");
			System.out.println(fitnessCenter.viewInstructor());
		}
		
		
		//write to PersonalTraining.txt file of the new Personal Training class added by Manager using Manager Menu
		private void addNewPersonalTrainingClass() {
			String className = helper.inputLine("Please enter the name of the Personal Training class you'd like to add: ");
			String time = helper.inputLine("Please enter the time for the class (eg: Friday 10:00-11:00): ");
			String description = helper.inputLine("Please enter the description for the Personal Training class: ");
			System.out.println("All new classes will be taught by instructor: Jason");
			
			PersonalTraining newClass = new PersonalTraining(className,time,description);
			fitnessCenter.createPersonalTrainingClass(newClass);
			
			System.out.println(newClass);
			
			//PersonalTraining newClass = FitnessCenter.createPersonalTrainingClass(className);
			System.out.println("You have successfully added "+newClass.getName()+" to Personal Training Class list.");
		}

		//Quit the Program
		private void quit() {
			if (!(currentMember == null)){ //check whether this member has logged out or not
				currentMember = null;
			}
			System.out.println("Goodbye!");
		}
			
		public static void main(String[] args) {
			TonysFitnessCenter tonysFitnessCenter = new TonysFitnessCenter();
			tonysFitnessCenter.run();
		}
		
	}



