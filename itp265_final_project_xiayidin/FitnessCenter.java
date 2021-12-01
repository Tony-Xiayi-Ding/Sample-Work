/**
 * FitnessCenter Class; read and write from PersonalTraining or GroupExercise Files;
 * 
 * @author Tony Ding
 * ITP 265, Fall 2020, Gelato Section
 * Final Project
 * Email: xiayidin@usc.edu
 */
package itp265_final_project_xiayidin;

import java.io.*;
import java.util.*;

public class FitnessCenter {
	private UI helper;
	private static ArrayList<Instructor> instructors;
	private ArrayList<PersonalTraining> pClassList = new ArrayList<>();
	private static int NEWKEY = 0;
	private static final String PERSONALTRAINING_FILE = "src/PersonalTraining.txt";
	private static final String GROUPEXERCISE_FILE= "src/GroupExercise.txt";
	
	
	//Here are all the instructors' information;
	//No need to add instructors in my program;
	//Reading and writing to file tasks are done on Members and PersonalTraining Class;
	public void setUpInstructor(){
		instructors = new ArrayList<>();
		Instructor i1 = new Instructor("Jennifer","jennifer@Tonyfitness.com","Group Cycling");
		Instructor i2 = new Instructor("Daniel","daniel@Tonyfitness.com","Dancing");
		Instructor i3 = new Instructor("Melody","melody@Tonyfitness.com","Yoga");
		Instructor i4 = new Instructor("Brian","brian@Tonyfitness.com","Swimming");
		Instructor i5 = new Instructor("Jason","jason@Tonyfitness.com","All Subjects");
		instructors.add(i1);
		instructors.add(i2);
		instructors.add(i3);
		instructors.add(i4);
		instructors.add(i5);
		
	}
	
	public Map<ClassType, List<FitnessClass>> setUpFitnessClass(){
		setUpInstructor(); //create an arrayList of instructors;
		Map<ClassType, List<FitnessClass>> classMap = new HashMap<>(); //keys are ClassTypes
		//initialize the map with keys going to empty lists
		for(ClassType type: ClassType.values()) {
			List<FitnessClass> classList = new ArrayList<>();
			classMap.put(type,classList);
		}
		readFile(PERSONALTRAINING_FILE,classMap); 
		readFile(GROUPEXERCISE_FILE,classMap);
		return classMap;
	}
	
	
	private static void readFile(String file, Map<ClassType, List<FitnessClass>> map) {
		try(FileInputStream fis = new FileInputStream(file); 
				Scanner sc = new Scanner(fis)){
			if(sc.hasNextLine()) {
				String header = sc.nextLine(); //skip the header in the file
				while(sc.hasNextLine()) {
					String line = sc.nextLine(); 
					FitnessClass fc = parseStringIntoClass(line); // turns each line into a fitnessClass
					if(fc != null) {
						addClassToMap(fc,map);
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			System.err.println("File couldn't be found.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		

	
	private static void addClassToMap(FitnessClass fc, Map<ClassType, List<FitnessClass>> map) {
		//add the class to the map of classes
		ClassType type = fc.getType();
		List<FitnessClass> classList = map.get(type);
		classList.add(fc);
		
	}


	private static FitnessClass parseStringIntoClass(String line) {
		FitnessClass fc = null;
		try {
			Scanner sc = new Scanner(line);
			sc.useDelimiter(",");
			String type = sc.next();
			String rest = sc.nextLine();
			if (type.equalsIgnoreCase("PersonalTraining")) {
				fc = parseTraining(rest);
			}
			else {
				fc = parseExercise(rest);
			}
			sc.close();
		}
		catch(Exception e){
			System.err.println("Error reading line: " + line + "\nerror; " + e);
		}

		return fc;
	}

	//Read the Personal Training file and create PersonalTraining objects
	private static PersonalTraining parseTraining(String restline) {
		PersonalTraining pt = null;
		try {
			Scanner sc = new Scanner(restline);
			sc.useDelimiter(",");
			String name = sc.next();
			String time = sc.next();
			String instructorName = sc.next();
			Instructor instructor = parseInstructor(instructorName);
			String description = sc.next();
			pt = new PersonalTraining(name,time,instructor, description);
		}
		catch(Exception e) {
			System.err.println("Error reading line: " + restline + "\nerror; " + e);
		}
		return pt;
	}
	
	//Read the Group Exercise file and create GroupExercise objects
	private static GroupExercise parseExercise(String restline) {
		GroupExercise ge = null;
		try {
			Scanner sc = new Scanner(restline);
			sc.useDelimiter(",");
			String name = sc.next();
			String time = sc.next();
			String instructorName = sc.next();
			Instructor instructor = parseInstructor(instructorName);
			String level = sc.next();
			int size = sc.nextInt();
			Level difficulty = parseLevel(level);
			ge = new GroupExercise(name,time,instructor,difficulty,size);
		}
		catch(Exception e) {
			System.err.println("Error reading line: " + restline + "\nerror; " + e);
		}
	
		return ge;
	}

	//Match the string in the file with corresponding difficulty level in the Enum class
	private static Level parseLevel(String level) {
		Level difficulty = Level.EASY;
		for(Level l: Level.values()) {
			if(l.toString().equalsIgnoreCase(level)){//compare each value in the Enum class with the String passed in
				difficulty = l;
			}
		}
		return difficulty;
	}
	
	//this method will match the name of the instructor in the file with corresponding instructor object in the arrayList
	private static Instructor parseInstructor(String i) {
		Instructor instructor = instructors.get(0);
		for(Instructor ins: instructors) {
			String name = ins.getName();
			if(i.equals(name)) {
				instructor = ins;
			}
		}
		return instructor;
	}
	
	
	public ArrayList<Instructor> getInstructorList() {
		return instructors;
	}
	
	public String viewInstructor() {
		Collections.sort(instructors);
		String str ="";
		int i = 1;
		for (Instructor trainer: instructors) {
			str += i;
			str += ". ";
			str += trainer;
			str += "\n";
			i ++;
		}
		return str;
	}
	
	public void setUpInitialPTrainingList() {
		//Written like this so that this will only be executed once and once only;
		while(NEWKEY==0) {
		pClassList.add(new PersonalTraining("Swimming","Sunday 10:00-11:00",instructors.get(3),"Swimming: butterfly stroke"));
		pClassList.add(new PersonalTraining("Yoga","Thursday 9:00-10:00",instructors.get(2),"Relaxing Yoga time to clear your mind and meditate"));
		pClassList.add(new PersonalTraining("Swimming","Thursday 17:00-18:00",instructors.get(3),"Swimming: freestyle stroke"));
		pClassList.add(new PersonalTraining("Yoga","Tuesday 11:00-12:00",instructors.get(2),"Relaxing Yoga time to clear your mind and meditate"));
		NEWKEY++;
		}
	}

	public PersonalTraining createPersonalTrainingClass(PersonalTraining className) {
		
		setUpInitialPTrainingList();
		
		//add new classes to this ArrayList
        pClassList.add(4+(NEWKEY-1),className);
        NEWKEY++;
		
		try (FileOutputStream fos = new FileOutputStream(PERSONALTRAINING_FILE);
				PrintWriter pw = new PrintWriter(fos)) {
			pw.println("Type,Name,Time,Instructor,Description");	
			for (int i = 0;i < pClassList.size();i++) {
					pw.print("PersonalTraining,");
					pw.println(pClassList.get(i).toFileString());
				}
								
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
		return className; 
	}
	
}


