/**
 * MemberLogin Class; focusing on reading and writing to member file
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.io.*;
import java.util.*;


public class MemberLogin {
	private Map<String, Member> members; //Use Map of members; result of peer feedback
	private static final String MEMBERINFO_FILE = "src/memberinfo.txt";
	private UI helper;
	
	
	public MemberLogin() {
		members = new HashMap<>();
		helper = new UI();
		readExistingMemberFromFile();
		
	}

	public Member createMember() {
		String email = helper.inputLine("Please enter your email address: ");
		Member newMember = createMember(email);
		writeMembersToFile();
		return newMember;
		
	}
	
	public Member createMember(String email) {
		// check if the user if already in the memberinfo.txt;
		if (members.containsKey(email)) {
			System.out.println("This account is already registered in the system. Please choose to login instead.");
			return null;
		}
		String name = helper.inputLine("Please enter your full name: ");
		String password = helper.inputLine("Please enter your password: ");
		
		String securityQuestion = "";
		String answer = "";
		boolean setSecurityQuestion = helper.inputYesNoAsBoolean("Would you like to set a security question? (y/n)");
		if (setSecurityQuestion) {
			securityQuestion = helper.inputLine("Please enter your security question: ");
			answer = helper.inputLine("Please enter the answer to " + securityQuestion);
		}
		Member member = new Member(name,email,password,securityQuestion,answer);
		
		//Ask if newly created member would like to purchase membership
		Member memberfinal = upgradeMembership(member); 
		return memberfinal; 
	}
	
	//read from file
	private void readExistingMemberFromFile() {
		try(FileInputStream fis = new FileInputStream(MEMBERINFO_FILE);
				Scanner scan = new Scanner(fis)){
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				Member member = parseLineToMember(line);
				members.put(member.getEmail(),member);
			}
		} catch (FileNotFoundException e) {
			System.err.print("File not found exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("IOException");
			e.printStackTrace();
		}
		
	}
	
	//parse info into a Member Class and then decide if it's PaidMember or GuestCustomer;
	private Member parseLineToMember(String line) {
		Member m;
		Member mtemp;
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter("/");
		String membership = lineReader.next();
		String email = lineReader.next();
		String name = lineReader.next();
		String password = lineReader.next();
		if (lineReader.hasNext()) {
			String question = lineReader.next();
			String answer = lineReader.next();
			mtemp = new Member(name,email,password,question,answer);
		}
		else {
			mtemp = new Member(name,email,password);
		}
		//check the type of membership
		if (membership.equalsIgnoreCase("paidmember")) { 
			m = new PaidMember(mtemp);
		}
		else {
			m = new GuestCustomer(mtemp);
		}
		lineReader.close();
		return m;
	}
	
	
	
	/**
	 * Every time a new user creates an account, ask if he/she wants to purchase a membership
	 * @param m
	 * @return a specific member type
	 */
	public Member upgradeMembership(Member m) {
		Member mfinal;
		boolean choice = helper.inputYesNoAsBoolean("Would you like to purchase an annual membership for $89.99? (y/n)\n"
				+ "By upgrading to our premium membership, you will have free access to Group Exercises and can also rent a locker.");
		if (choice) {
			mfinal = new PaidMember(m);
		}
		else {
			mfinal = new GuestCustomer(m);
		}
		
		//put the member into the map after specific member type is verified
		members.put(mfinal.getEmail(), mfinal); 
		return mfinal;
	}
	
	
	
	public String membersToString() {
		//A way to visualize the map of users....
		//Loop through the map, get all the keys, and print all the User objects
		String str = "Map of All Users --> Email: User Object\n";
		for(String key: members.keySet()) { // for each key in the user map of keys....
			Member value = members.get(key); // get the object associated with the key
			str += "\t" + key + ": "+ value + "\n";
		}
		return str;
	}
	
	
	
	/**
	 * This method handles member signing in
	 * Will check if the email has an account associated with it; if not, will ask user to create a new account
	 * @return
	 */
	public Member memberSignin() {
		Member m;
		String email = helper.inputLine("Please enter your email:");
		if(members.containsKey(email)) {//if the member exists in the members
			Member mtemp = members.get(email);//get the temporary member
			String password = helper.inputLine("Please enter your password:");//verify the password
			boolean pass = mtemp.verifyPassword(password);
			while (!pass) {
				System.out.println("Password incorrect. Please try again.");
				password = helper.inputLine("Please enter your password:");
				pass = mtemp.verifyPassword(password);
			}
			//if password is correct
			m = mtemp; 
			System.out.println("Welcome back "+m.getName()+" !");
		}
		else {
			System.out.println("Didn't find the member associated with that email.");//if the email could not be found;
			boolean createNew = helper.inputYesNoAsBoolean("Would you like to create a new account?(y/n)");
			if(createNew) {
				m = createMember(email);
				upgradeMembership(m);
			}
			else {
				m = null;
			}
		}
		return m;	
	}
	
	

	
	/**
	 * This method will write all the members in the map to the file
	 */
	//Used "/" as delimiter in the memberInfo.txt file.
	private void writeMembersToFile() {
		try (FileOutputStream fos = new FileOutputStream(MEMBERINFO_FILE);
			PrintWriter pw = new PrintWriter(fos)) {
			// take each member in the map and write it all to the file.
			for(String key: members.keySet()) {
				Member u = members.get(key);
				if(u.getClass().equals(PaidMember.class)){
					pw.print("PaidMember/");
				}
				else {
					pw.print("GuestCustomer/");
				}
				// print users email/name/password/q/a
				pw.println(u.toFileString());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		// automatically close at the end
	}
	
	public void viewAllMembers() {
		System.out.println("\nPaid Members:");
		int i = 1;
		for(Member m: members.values()) {
			if (m.getClass().equals(PaidMember.class)) {
				System.out.println(i+". "+m.getName()+", email=" +m.getEmail());
				i++;
			}
			
		}
		System.out.println("\nGuest Customers:");
		int c = 1;
		for(Member m: members.values()) {
			if(m.getClass().equals(GuestCustomer.class)) {
				System.out.println(c+". "+m.getName()+", email= "+m.getEmail());
				c++;
			}
		}
	}
}

