/**
 * PaidMember Class; extends from Member;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.util.*;
import java.io.*;

public class PaidMember extends Member implements rentLocker{
	
	private boolean hasLocker;
	private static double MEMBERSHIP_FEE = 89.99;
	private static double LOCKER_FEE = 20.00;
	private static int lockerNum = 101; //PaidMembers' locker number start from 101
	
	public PaidMember(String name, String email, String password, String question, String answer) {
		super(name, email, password, question, answer, MEMBERSHIP_FEE); //billing includes the initial membership fee of $89.99
		this.hasLocker = false;
	}
	
	public PaidMember(Member m) {
		this(m.getName(),m.getEmail(),m.getPassword(),m.getQuestion(),m.getAnswer());

	}
	
	public double getMembershipFee() {
		return MEMBERSHIP_FEE;
	}
	
	public double getRentalFee() {
		return LOCKER_FEE;
	}
	
	public boolean gethasLocker() {
		return hasLocker;
	}
	
	public void sethasLocker(boolean hasLocker) {
		this.hasLocker = hasLocker;
		
	}
	
	public String toString() {
		return "Paid Member: " + super.toString();
	}

	//PaidMembers can rent Lockers and their number start with 101;
	@Override
	public void rentLockers() {
		if (hasLocker == false) { //each member can only rent 1 locker
			int rentalFee = 20;
			System.out.println("Your locker number is " + lockerNum);
			super.addBilling(rentalFee);
			sethasLocker(true);
			lockerNum ++;
		}
		else {
			System.out.println("You have already rent a locker");
		}
	}
}

