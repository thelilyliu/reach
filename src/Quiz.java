/* 
 * Date: Monday, January 9, 2016
 * Author: Janujan Gathieswaran, Kevin Subhash, and Lily Liu
 * Description: The quiz class for creating quizzes, contributing questions and 
 * 				changing previously made quizzes and questions. 
 * Method List:
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Quiz {

	// list for questions
	private ArrayList<QuestionTF> questionsTF; // list of true and false questions
	private ArrayList<QuestionMC> questionsMC; // list of multiple choice questions
	private ArrayList<QuestionCB> questionsCB; // list of check box questions
	private ArrayList<Integer> order; // maintain order of questions
	
	// variables for quiz basic information
	private long quizID;
	private String category;
	private String quizName;
	private int size; // number of total questions

	// variables for quiz results
	private int numCorrect;
	private int numWrong;
	private double averageTime;
	
	// constructor for new quiz
	public Quiz(String category, String quizName) {
		this.quizID = generateQuizID();
		this.category = category;
		this.quizName = quizName;
		this.size = 0;
		this.questionsTF = new ArrayList<QuestionTF>();
		this.questionsMC = new ArrayList<QuestionMC>();
		this.questionsCB = new ArrayList<QuestionCB>();
	}
	
	// constructor for existing quiz
	public Quiz(long quizID, String category, String quizName, int size, 
			ArrayList<QuestionTF> questionsTF, 
			ArrayList<QuestionMC> questionsMC, 
			ArrayList<QuestionCB> questionsCB) {
		this.quizID = quizID;
		this.category = category;
		this.quizName = quizName;
		this.size = size;
		this.questionsTF = questionsTF;
		this.questionsMC = questionsMC;
		this.questionsCB = questionsCB;
	}
	
	/*
	 * ==============================
	 * Add Question to Quiz
	 * ==============================
	 */
	
	public void addTF(QuestionTF q) {
		questionsTF.add(q); // add question
		order.add(1); // 1 means true or false question
		size++; // increase size counter
	}
	
	public void addMC(QuestionMC q) {
		questionsMC.add(q); // add question
		order.add(2); // 2 means multiple-choice question
		size++; // increase size counter
	}
	
	public void addCB(QuestionCB q) {
		questionsCB.add(q); // add question
		order.add(3); // 3 means check box question
		size++; // increase size counter
	}
	
	/*
	 * ==============================
	 * Change Question for Quiz
	 * ==============================
	 */
	
	public boolean changeTF(String question, QuestionTF newQ) {
		int index = searchTF(question);
		
		if (index > -1) { // previous question found
			questionsTF.set(index, newQ); // replace old question with new question
			return true;
		}
		
		return false;	
	}
	
	public boolean changeMC(String question, QuestionMC newQ) {
		int index = searchMC(question);
		
		if (index > -1) { // previous question found
			questionsMC.set(index, newQ); // replace old question with new question
			return true;
		}
		
		return false;	
	}
	
	public boolean changeCB(String question, QuestionCB newQ) {
		int index = searchCB(question);
		
		if (index > -1) { // previous question found
			questionsCB.set(index, newQ); // replace old question with new question
			return true;
		}
		
		return false;	
	}
	
	/*
	 * ==============================
	 * Remove Question from Quiz
	 * ==============================
	 */
	
	public boolean removeTF(String question) {
		int index = searchTF(question);
		
		if (index > -1) { // previous question found
			questionsTF.remove(index); // remove question
			order.remove(index); // remove from order list
			size--; // decrease size counter
			return true;
		}
		
		return false;
	}
	
	public boolean removeMC(String question) {
		int index = searchMC(question);
		
		if (index > -1) { // previous question found
			questionsMC.remove(index); // remove question
			order.remove(index); // remove from order list
			size--; // decrease size counter
			return true;
		}
		
		return false;
	}
	
	public boolean removeCB(String question) {
		int index = searchCB(question);
		
		if (index > -1) { // previous question found
			questionsCB.remove(index); // remove question
			order.remove(index); // remove from order list
			size--; // decrease size counter
			return true;
		}
		
		return false;
	}
	
	/*
	 * ==============================
	 * Search Quiz for Question
	 * ==============================
	 */
	
	public int searchTF(String q) {
		int sizeTF = questionsTF.size();
		
		for (int i = 0; i < sizeTF; i++) {
			if (questionsTF.get(i).getQuestion().equals(q)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int searchMC(String q) {
		int sizeMC = questionsMC.size();
		
		for (int i = 0; i < sizeMC; i++) {
			if (questionsMC.get(i).getQuestion().equals(q)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int searchCB(String q) {
		int sizeCB = questionsCB.size();
		
		for (int i = 0; i < sizeCB; i++) {
			if (questionsCB.get(i).getQuestion().equals(q)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/*
	 * ==============================
	 * Other Functions
	 * ==============================
	 */
	
	public boolean changeQuizName(String newQuizName) {
		Category c = new Category(category); // read and load data from file
		c.readFromFile(c.getCategory() + ".txt");
		
		if (c.change(quizName, newQuizName)) { // change quiz name and update category file successful
			// **** update quiz file ****
			this.quizName = newQuizName;
			return true;
		}
		
		return false;
	}
	
	public long generateQuizID() {
		// http://stackoverflow.com/questions/5392693/java-random-number-with-given-length
		Random r = new Random(); // used to generate random numbers
		return 100000000 + r.nextInt(900000000); // generate random numbers from 100000000 to 999999999

		/*
		 * basic functionality of random
		 * starts from 0 to 5 for rnd.nexInt(6)
		 * to start from 1, 1 must added to the rnd.nextInt(6)
		 *
		 * to count from 100000000 to 999999999
		 * rnd.nextInt(999999999) won't work as it'll generate a number from 0 to 999999998
		 * adding 100000000 + rnd.nextInt(999999999) would yield 100000000 to 1,099,999,998
		 * so instead, subtract 999999999 - 100000000 and add 1 to get correct range
		 */
	}
	
	public boolean readFromFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			// read and load basic header information
			quizID = Long.parseLong(br.readLine());
			category = br.readLine();
			quizName = br.readLine();
			size = Integer.parseInt(br.readLine());
			
		    String line = br.readLine();
		    
		    while (line != null) {
		    	String[] info = line.split("|");
		    	
		    	switch(Integer.parseInt(info[0])) { // switch based on question type
		    		case 1: // true or false
		    			QuestionTF qTF = new QuestionTF(info);
		    			questionsTF.add(qTF);
		    			break;
		    			
		    		case 2: // multiple-choice
		    			QuestionMC qMC = new QuestionMC(info);
		    			questionsMC.add(qMC);
		    			break;
		    			
		    		case 3: // check box
		    			QuestionCB qCB = new QuestionCB(info);
		    			questionsCB.add(qCB);
		    			break;
		    			
		    		default:
		    			System.err.println("Error: Quiz readFromFile()");
		    	}
		    	
		        size++; // increase size
		        line = br.readLine(); // read next line
		    }
		    
		    br.close();
		    
		    return true;
		}
		catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			
			return false;
		}
	}
	
	public boolean writeToFile(String fileName, String contents, boolean append) {
		try {
		    FileWriter fw = new FileWriter(fileName, append); // true tells to append data
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(contents);
		    bw.close();
		    
		    return true;
		}
		catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			
			return false;
		}
	}
	
	public String toString() {
		String s = quizID + "\n" + category + "\n" + quizName + "\n" + size + "\n";
		
		for (int i = 0; i < size; i++) {
			switch(order.get(i)) {
				case 1: // true or false
					s += questionsTF.get(i).toString() + "\n";
					break;
					
				case 2: // multiple-choice
					s += questionsMC.get(i).toString() + "\n";
					break;
					
				case 3: // check box
					s += questionsCB.get(i).toString() + "\n";
					break;
					
				default:
					System.err.println("Error: Quiz toString()");
			}
		}
		
		return s;
	}
	
	/*
	 * ==============================
	 * Getters
	 * ==============================
	 */
	
	public ArrayList<QuestionTF> getQuestionsTF() {
		return questionsTF;
	}
	
	public ArrayList<QuestionMC> getQuestionsMC() {
		return questionsMC;
	}
	
	public ArrayList<QuestionCB> getQuestionsCB() {
		return questionsCB;
	}
	
	public long getQuizID() {
		return quizID;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getQuizName() {
		return quizName;
	}
	
	public int getSize() {
		return size; 
	}
	
	public int getNumCorrect() {
		return numCorrect;
	}
	
	public int getNumWrong() {
		return numWrong;
	}
	
	public double getAverageTime() {
		return averageTime;
	}
	
	/*
	 * ==============================
	 * Setters
	 * ==============================
	 */
	
	public void setQuestionsTF(ArrayList<QuestionTF> questionsTF) {
		this.questionsTF = questionsTF;
	}
	
	public void setQuestionsMC(ArrayList<QuestionMC> questionsMC) {
		this.questionsMC = questionsMC;
	}
	
	public void setQuestionsCB(ArrayList<QuestionCB> questionsCB) {
		this.questionsCB = questionsCB;
	}

	public void setQuizID (long quizID) {
		this.quizID = quizID;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setNumCorrect(int numCorrect) {
		this.numCorrect = numCorrect;
	}
	
	public void setNumWrong(int numWrong) {
		this.numWrong = numWrong;
	}
	
	public void setAverageTime(int averageTime) {
		this.averageTime = averageTime;
	}
	
	/*
	 * ==============================
	 * Self-Testing Main
	 * ==============================
	 */

	public static void main(String[] args) {
		boolean keepGoing = true;
		String[] button = {"Add", "Print", "Delete", "Change", "Load", "Save", "Quit"}; // array of button actions
		
		Quiz quiz = new Quiz("History", "American History");
		
		while(keepGoing) {
			// asks user some options with buttons
			int command = JOptionPane.showOptionDialog(null, 
					"What Would You Like To Do With The Account Records?","Account Records", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, button, button[0]);
			
			switch(button[command].charAt(0)) {
				case 'A': { // add
					int typeAdd = Integer.parseInt(JOptionPane.showInputDialog(null, 
							"Type of question: (1 ��TF, 2 � MC, 3 ��CB"));
					
					switch(typeAdd) {
						case 1: // true or false
							// get question
							String questionTF = JOptionPane.showInputDialog(null, 
									"Enter a true or false question to add:");
							
							// get answer
							boolean answerTF = java.lang.Boolean.parseBoolean(JOptionPane.showInputDialog(null, 
									"Enter the answer: (true or false)"));
							
							// create question and add to list
							QuestionTF qTF = new QuestionTF(questionTF, answerTF);
							quiz.addTF(qTF);
							break;
							
						case 2: // multiple-choice
							// get question
							String questionMC = JOptionPane.showInputDialog(null, 
									"Enter a multiple-choice question to add:");
							
							// get options
							int sizeMC = Integer.parseInt(JOptionPane.showInputDialog(null, 
									"Enter the number of options:"));
							ArrayList<String> optionsMC = new ArrayList<String>();
							for (int i = 0; i < sizeMC; i++) {
								String option = JOptionPane.showInputDialog(null, 
										"Enter option #" + (i + 1));
								optionsMC.add(option);
							}
							
							// get answer
							String answerMC = JOptionPane.showInputDialog(null, 
									"Enter the answer:");
							
							// create question and add to list
							QuestionMC qMC = new QuestionMC(questionMC, optionsMC, answerMC);
							quiz.addMC(qMC);
							break;
							
						case 3: // check box
							// get question
							String questionCB = JOptionPane.showInputDialog(null, 
									"Enter a check box question to add:");
							
							// get options
							int oSizeCB = Integer.parseInt(JOptionPane.showInputDialog(null, 
									"Enter the number of options:"));
							ArrayList<String> optionsCB = new ArrayList<String>();
							for (int i = 0; i < oSizeCB; i++) {
								String option = JOptionPane.showInputDialog(null, 
										"Enter option #" + (i + 1));
								optionsCB.add(option);
							}
							
							// get answers
							int aSizeCB = Integer.parseInt(JOptionPane.showInputDialog(null, 
									"Enter the number of answers:"));
							ArrayList<String> answersCB = new ArrayList<String>();
							for (int i = 0; i < aSizeCB; i++) {
								String answer = JOptionPane.showInputDialog(null, 
										"Enter answer #" + (i + 1));
								answersCB.add(answer);
							}
							
							// create question and add to list
							QuestionCB qCB = new QuestionCB(questionCB, optionsCB, answersCB);
							quiz.addCB(qCB);
							break;
							
						default:
							System.err.println("Error: Quiz main()");
					}
					break;
				}
					
				case 'P': // print
					JOptionPane.showMessageDialog(null, quiz.toString());
					break;
					
				case 'D': // delete
					int typeDelete = Integer.parseInt(JOptionPane.showInputDialog(null, 
							"Type of question: (1 ��TF, 2 � MC, 3 ��CB"));
					String question = JOptionPane.showInputDialog(null, 
							"Enter a question to delete:");
					
					switch(typeDelete) {
						case 1: // true or false
							quiz.removeTF(question);
							break;
							
						case 2: // multiple-choice
							quiz.removeMC(question);
							break;
							
						case 3: // check box
							quiz.removeCB(question);
							break;
							
						default:
							System.err.println("Error: Quiz main()");
					}
					break;
					
				case 'C': // change
					int typeChange = Integer.parseInt(JOptionPane.showInputDialog(null, 
							"Type of question: (1 ��TF, 2 � MC, 3 ��CB"));
					
					switch(typeChange) {
						case 1: // true or false
							// get old question
							String oldQuestionTF = JOptionPane.showInputDialog(null, 
									"Enter a true or false question to change:");
							
							// get new question
							String questionTF = JOptionPane.showInputDialog(null, 
									"Enter a true or false question to change:");
							
							// get answer
							boolean answerTF = java.lang.Boolean.parseBoolean(JOptionPane.showInputDialog(null, 
									"Enter the answer: (true or false)"));
							
							// create question to replace old question
							QuestionTF qTF = new QuestionTF(questionTF, answerTF);
							if (quiz.changeTF(oldQuestionTF, qTF)) {
								JOptionPane.showMessageDialog(null, "Done");
							}
							else {
								JOptionPane.showMessageDialog(null, "Fail");
							}
							break;
							
						case 2: // multiple-choice
							// get old question
							String oldQuestionMC = JOptionPane.showInputDialog(null, 
									"Enter a multiple-choice question to change:");
							
							// get new question
							String questionMC = JOptionPane.showInputDialog(null, 
									"Enter new multiple-choice question:");
							
							// get options
							int sizeMC = Integer.parseInt(JOptionPane.showInputDialog(null, 
									"Enter the number of options:"));
							ArrayList<String> optionsMC = new ArrayList<String>();
							for (int i = 0; i < sizeMC; i++) {
								String option = JOptionPane.showInputDialog(null, 
										"Enter option #" + (i + 1));
								optionsMC.add(option);
							}
							
							// get answer
							String answerMC = JOptionPane.showInputDialog(null, 
									"Enter the answer:");
							
							// create question to replace old question
							QuestionMC qMC = new QuestionMC(questionMC, optionsMC, answerMC);
							if (quiz.changeMC(oldQuestionMC, qMC)) {
								JOptionPane.showMessageDialog(null, "Done");
							}
							else {
								JOptionPane.showMessageDialog(null, "Fail");
							}
							break;
							
						case 3: // check box
							// get old question
							String oldQuestionCB = JOptionPane.showInputDialog(null, 
									"Enter a check box question to change:");
							
							// get new question
							String questionCB = JOptionPane.showInputDialog(null, 
									"Enter new check box question:");
							
							// get options
							int oSizeCB = Integer.parseInt(JOptionPane.showInputDialog(null, 
									"Enter the number of options:"));
							ArrayList<String> optionsCB = new ArrayList<String>();
							for (int i = 0; i < oSizeCB; i++) {
								String option = JOptionPane.showInputDialog(null, 
										"Enter option #" + (i + 1));
								optionsCB.add(option);
							}
							
							// get answers
							int aSizeCB = Integer.parseInt(JOptionPane.showInputDialog(null, 
									"Enter the number of answers:"));
							ArrayList<String> answersCB = new ArrayList<String>();
							for (int i = 0; i < aSizeCB; i++) {
								String answer = JOptionPane.showInputDialog(null, 
										"Enter answer #" + (i + 1));
								answersCB.add(answer);
							}
							
							// create question to replace old question
							QuestionCB qCB = new QuestionCB(questionCB, optionsCB, answersCB);
							if (quiz.changeCB(oldQuestionCB, qCB)) {
								JOptionPane.showMessageDialog(null, "Done");
							}
							else {
								JOptionPane.showMessageDialog(null, "Fail");
							}
							break;
							
						default:
							System.err.println("Error: Quiz main()");
					}
					break;
					
				case 'L': // load
					
					break;
					
				case 'S': // save
					
					break;
					
				case 'Q': // quit
					keepGoing = false; // break out of while loop
					break;
					
				default:
					System.err.println("Error: Quiz main()");
			}
		}
	}
}