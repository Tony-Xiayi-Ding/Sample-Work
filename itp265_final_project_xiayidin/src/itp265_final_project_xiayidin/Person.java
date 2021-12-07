/**
 * Person Class; implements Comparable<Person>;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */

package itp265_final_project_xiayidin;


public class Person implements Comparable<Person>{
	
	private String name;
	private String email;
	
	public Person(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User: " + name + ", email=" + email;
	}
	
	public final int compareTo(Person p) {
		int i;
		if (this.equals(p)) {
			return 0;}
		//People are ordered according to their name
		i = this.name.compareTo(p.name);
		if(i == 0) {//if same name, then compare email
			i = this.email.compareTo(p.email);
		}
		return i;
	}
}

