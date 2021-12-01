/**
 * Fitness Class; implements comparable; 
 * Abstract class because will never instantiate a Fitness Class, only the more detailed type of Class;
 * Also have some abstract methods in this class; waiting for sub class to implement;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.util.*;
import java.io.*;

public abstract class FitnessClass implements Comparable<FitnessClass> {
	private String className;
	private String time;
	private Instructor instructor;
	
	private boolean isincludedMembership; //indicate whether the class is included in membership
	
	public FitnessClass(String className, String time, Instructor instructor, boolean isincludedMembership) {
		this.className = className;
		this.time = time;
		this.instructor = instructor;
		this.isincludedMembership = isincludedMembership;
	}
	
	public FitnessClass(String className, String time,  boolean isincludedMembership) {
		this.className = className;
		this.time = time;
		this.instructor = new Instructor("Jason","jason@Tonyfitness.com","All Subjects");
		this.isincludedMembership = isincludedMembership;
	}

	public String getName() {
		return className;
	}

	public void setName(String className) {
		this.className = className;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public boolean isIsincludedMembership() {
		return isincludedMembership;
	}

	public void setIsincludedMembership(boolean isincludedMembership) {
		this.isincludedMembership = isincludedMembership;
	}
	
	//these are the abstract methods which the subclasses of fitnessClass will implement
	public abstract double calculateCost(Member m); 
	//if the class is included in membership, the cost will be 0 for paid member
	//if the class is not included in membership, paid member can get a 20% off
	
	protected abstract ClassType getType(); 
	
	protected abstract boolean addParticipant(Member m);
	
	protected abstract boolean removeParticipant(Member m);
	
	protected abstract void viewParticipant();
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instructor == null) ? 0 : instructor.hashCode());
		result = prime * result + (isincludedMembership ? 1231 : 1237);
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}
	
	public final int compareTo(FitnessClass f) {
		int num;
		if (this.equals(f)) {
			return 0;}
		//fitnessClass are ordered according to their className
		num = this.className.compareTo(f.className);
		if(num == 0) {//if className is the same, compare time
			num = this.time.compareTo(f.time);
		}
		return num;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		FitnessClass other = (FitnessClass) obj;
		
		if (instructor == null) {
			if (other.instructor != null)
				return false;
		} 
		else if (!instructor.equals(other.instructor))
			return false;
		if (isincludedMembership != other.isincludedMembership)
			return false;
		
		if (className == null) {
			if (other.className != null)
				return false;
		} 
		else if (!className.equals(other.className))
			return false;
		
		if (time == null) {
			if (other.time != null)
				return false;
		} 
		else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	public String toString() {
		String str= "Class's Name: "+this.className;
		if (isincludedMembership) {
			str += " (Included in membership)";
		}
		else {
			str += " (Not included in membership)";
		}
		str+= "\n   Time: "+this.time+"\n   Instructor's name: " + this.instructor.getName();
		return str;
	}
	
}
