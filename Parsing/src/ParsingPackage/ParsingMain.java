package ParsingPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParsingMain {
	
	public static String COMMA_DELIMITER = ",";

		public static void main(String[] args) {
			
			int namesWithLetter = 0;
			int assignmentWorkes = 0;
			String letterToCheckFor = "a";
			String workAssignmentToCheckFor = "Android App";

			List<List<String>> records = new ArrayList<>();
			try (Scanner scanner = new Scanner(new File("sample.csv"));) {
				while (scanner.hasNextLine()) {
					records.add(getRecordFromLine(scanner.nextLine()));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			records.remove(0);//removes first the first row of the csv file
			
			namesWithLetter = checkNamesForA(records, letterToCheckFor); //checks how many names contains chosen letter
			System.out.println("Amount of names containing the letter '" + letterToCheckFor + "' is: " +namesWithLetter);
			assignmentWorkes = checkForWorkAssignment(records, workAssignmentToCheckFor); //checks amount of people working with a certain assignment
			System.out.println("Amount of persons working with: " + workAssignmentToCheckFor + " is: " +assignmentWorkes);
			System.out.println();
			ceckTime(records); //checks for same timestamps
			nameAndEmail(records); //sort outs every persons name and email, and warns if an email has already been used
			
			for(List<String> b:records){ 
				//System.out.println(b);
				
			}
			

		}
		
		private static int checkNamesForA(List<List<String>> list, String letter)
		{
			int i = 0;
			for(List<String> row:list) //Iterates the whole list
			{
				if(row.get(1).toLowerCase().contains(letter)) //Checks both "name rows" for certain letter, and ignores casing in names
				{
					i++;
				}
				if(row.get(2).toLowerCase().contains(letter))
				{
					i++;
				}
			}
			
			
			return i;
		}
		
		private static int checkForWorkAssignment(List<List<String>> list, String assignment)
		{
			int i = 0;
			for(List<String> row:list)
			{
				if(row.get(6).equals(assignment)) //checks the assignment row for wanted assignments
				{
					i = i+2; //if it contains wanted assignment (each row contains 2 people)
				}
			}
			return i; //returns number of people with the wanted assignment
		}
		
		private static void ceckTime(List<List<String>> list)
		{
			boolean usersWithSameTimestamp = false;
			
			for(List<String> row:list)
			{
				if(!row.get(0).equals("")) //if the row doesnt contain any timestamp, it ignores it
				{
					int i = 0;
					for(List<String> row2:list)
					{
						if(row.get(0).equals(row2.get(0))) //chekcs if there are any timestamps that are the same
						{
							i++;
							if (i > 1)
							{
								System.out.println("Same timestamp found at:");
								usersWithSameTimestamp = true;
								System.out.println(row.get(0) + ", " + row.get(1));
							}
						}
					}
				}
			}
			if (usersWithSameTimestamp == false)
			{
				System.out.println("no users had the same timestamp!");
			}
			System.out.println();
		}
		
		private static void nameAndEmail(List<List<String>> list)
		{
			List<String> emails = new ArrayList<>();
			for(List<String> row:list)
			{
				if(!row.get(0).equals("")) //ignores rows where there is no text
				{
				System.out.println(row.get(1) + ": " + row.get(3)); //takes first person on the row and matches with first mail
				if(emails.contains(row.get(3))) //checks if mail has aldready been entered
					System.out.println("WARNING! " +row.get(1) + "s email has already been entered by another person: " + row.get(3));
				System.out.println(row.get(2) + ": " + row.get(4)); //same but for second person				
				if(emails.contains(row.get(4)))
					System.out.println("WARNING! " +row.get(2) + "s email has already been entered by another person: " + row.get(4));
				
				emails.add(row.get(3)); //adds the emails to a list
				emails.add(row.get(4));
				}
			}
		}
		
		private static List<String> getRecordFromLine(String line) {
			List<String> values = new ArrayList<String>();
			try (Scanner rowScanner = new Scanner(line)) {
				rowScanner.useDelimiter(COMMA_DELIMITER);
				while (rowScanner.hasNext()) {
					values.add(rowScanner.next());
				}

			}
			
			return values;
		}


}
