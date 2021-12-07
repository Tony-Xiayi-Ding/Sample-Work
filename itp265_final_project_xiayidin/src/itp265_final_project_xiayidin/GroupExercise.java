/**
 * GroupExercise Class; extends from FitnessClass;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */

package itp265_final_project_xiayidin;

import java.util.*;
import java.io.*;

public class GroupExercise extends FitnessClass{
	private Level difficulty;
	private int size; // this is the size of each class
	private ArrayList<Member> participantList;
	
	//the only Constructor needed; no overloading;
	public GroupExercise(String name, String time, Instructor instructor, Level difficulty, int size) {
		super(name,time,instructor,true);//Set to default that group exercise is included in membership
		this.difficulty = difficulty;
		this.size = size;
		participantList = new ArrayList<Member>(size);
		
	}

	public Level getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Level difficulty) {
		this.difficulty = difficulty;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public ClassType getType() {
		return ClassType.GroupExercise;
	}
	
	public String toString() {
		return super.toString()+ "\n   Difficulty: "+difficulty+"\n   Size of class: "+size;
	}
	
	//Add participant to the class
	@Override
	public boolean addParticipant(Member member) {
		boolean add = false;
		if(!(participantList.contains(member))) { //check if the member has already registered for this class
			if(participantList.size() < size) {
				participantList.add(member);
				add = true;
			}
			else {
				System.out.println("Sorry, this class is now full. Please select other classes.");
			}
		}
		else {
			System.out.println("Sorry, but you have already registered for this class.");
		}
		return add;
	}
	
	@Override
	public boolean removeParticipant(Member m) {
		boolean removed = false;
		if(participantList.contains(m)) {//if the member is on the list
			participantList.remove(m);
			removed = true;
		}
		else {
			System.out.println("Remove Participant attempt failed, because you are not reserved for this class.");
		}
		return removed;
	}
	
	public void viewParticipant() {
		System.out.println("Here are all the participants for this class: ");
		for (Member members: participantList) {
			System.out.println(members);
		}
	}
	
	
	@Override
	public double calculateCost(Member member) {
		//if the person has membership, he/she can take the class with no extra cost
		//if the person does not have, he/she will have to pay $29.99 for each class
		double cost = 29.99;
		if(member.getClass() == PaidMember.class) {
			cost = 0;
		}
		return cost;
	}
	
	
}

