/**
 * Member Class; extends from Person;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.io.*;
import java.util.*;

public class Member extends Person{
	
	private String password;
	private String securityQuestion;
	private String securityAnswer;
	private ArrayList<FitnessClass> registeredClass;
	private double billing;
	
	public Member(String name, String email, String password, String question, String securityAnswer, double billing) {
		super(name,email);
		this.password = password;
		this.securityQuestion = question;
		this.securityAnswer = securityAnswer;
		this.billing = billing;
		registeredClass = new ArrayList<FitnessClass>();
	}
	
	//initial billing: 0
	public Member(String name, String email, String password, String question, String securityAnswer) {
		this(name, email, password,question,securityAnswer,0.0);
	}
	
	//for Members that do not want to set up security question and answers
	public Member(String name, String email, String password) {
		super(name, email);
		this.password = password;
		this.securityQuestion = "";
		this.securityAnswer = "";
		
	}
	
	//check password 
	public boolean verifyPassword(String pword) {
		return password.equals(pword);
			
	}
	
	
	//for writing to file formatting purpose;
	public String toFileString() {
		String fileLine = getEmail() + "/" + getName() + "/" + password;
		if(!securityQuestion.isEmpty()) {
			fileLine += "/" + securityQuestion + "/" + securityAnswer;
		}
		return fileLine;
	}
	

	/**
	 * @return question
	 */
	public String getQuestion() {
		return securityQuestion;
	}


	public void setQuestion(String question) {
		this.securityQuestion = question;
	}
	/**
	 * @return the securityAnswer
	 */
	public String getAnswer() {
		return securityAnswer;
	}


	public void setAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	public double getBilling() {
		return billing;
	}

	public void setBilling(double billing) {
		this.billing = billing;
	}
	
	public void addBilling(double cost) {
		this.billing += cost;
	}

	public ArrayList<FitnessClass> getRegisteredClass(){
		return registeredClass;
	}
	
	public void addClass(FitnessClass f) {
		registeredClass.add(f);
	}
	
	public void dropClass(FitnessClass f) {
		registeredClass.remove(f);
	}
	
	public String viewClass() {
		Collections.sort(registeredClass);
		int c = 1;
		String str = "You have scheduled the following class(es):\n";
		for (FitnessClass fitnessClassName: registeredClass) {
			str += c;
			str += ". " ;
			str += fitnessClassName;
			str += "\n";
			c++;
		}
		return str;
	}
	
	
	public int compareTo(Member m) {
		int num;
		if (this.equals(m)) {
			return 0;
		}
		//customers are ordered according to their name first
		num = this.getName().compareTo(m.getName());
		if(num == 0) {//if same name, then compare email
			num = this.getEmail().compareTo(m.getEmail());
		}
		return num;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		if(!securityQuestion.isEmpty()) {
			s += ", security question is" + securityQuestion + ", and answer is " + securityAnswer;
		}
		return s;
	}
	
	

}
