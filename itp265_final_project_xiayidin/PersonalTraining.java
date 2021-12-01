/**
 * Personal Training Class; extends from FitnessClass;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.util.*;
import java.io.*;

public class PersonalTraining extends FitnessClass {
	private String description;
	private Member participant;
	
	public PersonalTraining(String name, String time, Instructor instructor, String description) {
		super(name,time,instructor,false); //because Personal Training is not included in membership
		this.description = description;
		this.participant = null;
	}

	public PersonalTraining(String name, String time, String description) {
		super(name,time,false); //because Personal Training is not included in membership
		this.description = description;
		this.participant = null;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Member getParticipant() {
		return participant;
	}
	
	public ClassType getType() {
		return ClassType.PersonalTraining;
	}
	
	@Override
	public boolean addParticipant(Member member) {
		boolean added = false;
		if(participant==null){
			this.participant = member;
			added = true;
		}
		return added;
	}
	
	@Override
	public boolean removeParticipant(Member member) {
		boolean isRemoved = false;
		if (!(participant == null)) {
			this.participant = null;
			isRemoved = true;
		}
		return isRemoved;
	}
	
	public String toString() {
		return super.toString()+ "\n   Description: "+description;
	}
	
	public String toFileString() {
		String fileLine = getName() + "," + getTime() + "," + getInstructor().getName() + "," + getDescription();
		return fileLine;
	}

	@Override
	public double calculateCost(Member m) {
		//if the customer is a PaidMember, he/she will enjoy a 20% off
		//if the customer is a GuestCustomer, he/she will pay the full price of $49.99 per class
		double cost = 49.99;
		if(m.getClass() == PaidMember.class) {
			cost = 0.8*cost;
		}
		return cost;
	}

	@Override
	protected void viewParticipant() {
		System.out.println("The current participant is: "+participant);
		
	}


}



