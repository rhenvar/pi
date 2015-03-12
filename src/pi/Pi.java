/*
 * Program that maps numbers in pi to characters and prints them if they form words
 */

package pi;

import java.io.*;
import java.util.*;
public class Pi {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to the pi to word generator!");
		System.out.print("Enter your output file: ");
		String outputFile = s.nextLine();
		
		PrintStream output = new PrintStream(new File(outputFile));
		
		// change to appropriate files
		Scanner piReader = new Scanner(new File("pimillion.txt"));
		// Using 0's as spaces
		piReader.useDelimiter("0");
		
		
		
		Set<String> possibleWords = new HashSet<String>();
		Scanner dictionaryReader = new Scanner(new File("dictionary.txt"));
		
		while (dictionaryReader.hasNextLine()) {
			possibleWords.add(dictionaryReader.nextLine());
		}
		
		while (piReader.hasNext()) {
			String nextPhrase = piReader.next();
			String word = convert(nextPhrase);
			if (possibleWords.contains(word)) {
				output.println(word);
			}
		}
		
		// closing scanners
		dictionaryReader.close();
		output.close();
		piReader.close();
		s.close();
	}
	
	/*
	 * Post: Returns a new String with contents converted from numbers to letters
	 */
	public static String convert(String input) {
		String converted = "";
		
		for (int i = 0; i < input.length(); i += 3) {
			
			if (input.substring(i + 1).length() < 3) {
				if (i + 1 == input.length()) {
					converted += (char) (input.charAt(i) + 48);
				}
				else if (i + 2 == input.length()){
					if ((input.charAt(i) - 48) * 10 + (input.charAt(i + 1) - 48) < 27) {
						converted += (char) (((input.charAt(i) - 48) * 10 + (input.charAt(i + 1) - 48)) + 96);
					}
					else {
						converted += (char) (input.charAt(i) + 48);
						converted += (char) (input.charAt(i + 1) + 48);
					}
				}
			}
			
			else {
				// handling 3 characters, differentiating 1 (a) from 16 (p)
				int first = input.charAt(i) - 48;
				int second = input.charAt(i + 1) - 48;
				int third = input.charAt(i + 2) - 48;
				
				if (!isLetter(first, second) || isLetter(second, third)) {
					converted += (char) (first + 96);
					converted += (char) (second * 10 + third + 96);
				}
				else {
					converted += (char) (first * 10 + second + 96);
					converted += (char) (third + 96);
				}
			}
			
		}
		
		return converted;
	}
	
	/*
	 * Post: Returns whether the int combination is alphabetical, or < 27
	 */
	public static boolean isLetter(int tens, int ones) {
		return tens * 10 + ones < 27;
	}
	
}
