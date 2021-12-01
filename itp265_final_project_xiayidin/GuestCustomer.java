/**
 * GuestCustomer Class; extends from Member;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.util.*;
import java.io.*;

public class GuestCustomer extends Member{

	//Constructor
	public GuestCustomer(String name, String email, String password, String question, String answer) {
		super(name,email,password,question,answer);
	}
	
	public GuestCustomer(Member m) {
		this(m.getName(),m.getEmail(),m.getPassword(),m.getQuestion(),m.getAnswer());
	}
	
	public String toString() {
		return "Guest Customer: " + super.toString();
	}
		
	

}


