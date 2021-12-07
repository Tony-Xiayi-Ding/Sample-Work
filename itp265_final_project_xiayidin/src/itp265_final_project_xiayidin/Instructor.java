/**
 * Instructor Class; extends from Person Class and implements rentLocker Interface;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.util.*;
import java.io.*;

public class Instructor extends Person implements rentLocker{
	private String teachingSubject;
	private ArrayList<FitnessClass> instructedClass;
	private boolean hasLocker;
	private static int lockerNum = 1; //Their locker number starts from 1 vs. PaidMember start with 101

	

	public Instructor(String name, String email, String teachingSubject) {
		super(name,email);
		this.teachingSubject = teachingSubject;
		this.hasLocker = false;
		this.instructedClass = new ArrayList<FitnessClass>();
	}
	
	
	// called this specialty before, but teachingSubject sounds more formal;
	public String getSpecialty() {
		return teachingSubject;
	}

	// called this specialty before, but teachingSubject sounds more formal;
	public void setSpecialty(String teachingSubject) {
		this.teachingSubject = teachingSubject;
	}

	public ArrayList<FitnessClass> getInstructedClass() {
		return instructedClass;
	}
	
	public boolean gethasLocker() {
		return hasLocker;
	}
	
	public void sethasLocker(boolean hasLocker) {
		this.hasLocker = hasLocker;
		
	}

	public void setInstructedClass(ArrayList<FitnessClass> instructedClass) {
		this.instructedClass = instructedClass;
	}
	
	
	public int compareTo(Instructor i) {
		int num;
		if (this.equals(i)) {
			return 0;}
		//instructors are ordered according name
		num = this.getName().compareTo(i.getName());
		if(num == 0) {//if name is the same, compare teachingSubject
			num = this.getSpecialty().compareTo(i.getSpecialty());
		}
		return num;
	}
	
	@Override
	public String toString() {
		return "Instructor name: " + super.getName() + ", email: " + super.getEmail() +", and teaching subject is: " + teachingSubject;
	}
	
	//Instructors can rentLockers;
	@Override
	public void rentLockers() {
		if (hasLocker == false) {
			System.out.println("Request approved!");
			System.out.println(super.getName()+"'s locker number is: " + lockerNum);
			sethasLocker(true);
			lockerNum ++;
		}
		else {
			System.out.println(super.getName()+" already has a locker.");
			
		}
	}

}
