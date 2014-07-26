/**
 * 
 */
package de.number.NumberEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rohit
 * 
 */
public class Encoder {

	private DictionaryReader reader;
	private List<String> finalOutput = new ArrayList<String>();

	Encoder() {
		init();

	}

	private void init() {
		reader = new DictionaryReader();
		reader.readFile();
		DigitWordMap.buildMap();
		encode();
	}

	public void encode() {
		File file = new File("input.txt");

		try {

			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				String word = scan.next();
				computeOutputString(word);

			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void computeOutputString(String input) {
		input = input.replaceAll("\\D+", "");
		finalOutput.clear();
		List<String> possiblePrefixes = new ArrayList<String>();
		String currentWord = "";
		constructOutput(input, possiblePrefixes, currentWord);
		for (String output : finalOutput) {
			System.out.println(input + ": " + output);
		}

	}

	private boolean constructOutput(String input, List<String> possiblePrefixes, String currentWord) {

		// Exit from recursion if end of input is reached storing current word
		// in final output string
		if (0 == input.length()) {
			// Newpossibilities?
			if (0 == possiblePrefixes.size()) {
				finalOutput.add(currentWord);
				return true;
			}
			return false;
		}
		int num = Character.digit(input.charAt(0), 10);

		List<String> currentPrefixList = DigitWordMap.getDigitToWordMap().get(num);
		List<String> newPossibilities = multiplyPrefixes(possiblePrefixes, currentPrefixList);
		newPossibilities = validatePrefixes(newPossibilities);
		if (0 == newPossibilities.size()) {
			if (0 != possiblePrefixes.size() && possiblePrefixes.get(0).length() > 1) {
				return false;
			}
			if (0 != currentWord.length() && Character.isDigit(currentWord.charAt(currentWord.length() - 2))) {// Check
																												// for
																												// space
				return false;
			} else {
				currentWord = currentWord.concat(num + " ");
				constructOutput(input.substring(1), new ArrayList<String>(), currentWord);
			}
		} else {
			List<String> completeWords = checkForCompleteWords(newPossibilities);
			if (0 != completeWords.size()) {
				for (String word : completeWords) {
					String tempCurrentWord = currentWord;
					tempCurrentWord = tempCurrentWord.concat(word + " ");
					constructOutput(input.substring(1), new ArrayList<String>(), tempCurrentWord);
				}
			}

			if (!constructOutput(input.substring(1), newPossibilities, currentWord)) {
				if (0 != possiblePrefixes.size() && possiblePrefixes.get(0).length() > 1) {
					return false;
				}
				if (0 != currentWord.length() && Character.isDigit(currentWord.charAt(currentWord.length() - 2))) {
					return false;
				} else {
					currentWord = currentWord.concat(num + " ");
					constructOutput(input.substring(1), new ArrayList<String>(), currentWord);
				}
			}

		}
		return true;

		// Make Check for Complete Word

	}

	/**
	 * 
	 * @param possibleWords
	 * @return
	 */
	private List<String> checkForCompleteWords(List<String> possibleWords) {
		List<String> completeWords = new ArrayList<String>();
		for (String possibleWord : possibleWords) {
			if (reader.getDictTrie().containsWord(possibleWord)) {
				completeWords.add(possibleWord);
			}
		}
		return completeWords;

	}

	private void addDigitAsIs() {

	}

	/**
	 * Validate prefixes by checking against entries in dictionary Removes
	 * prefixes which are not present
	 * 
	 * @param prefixes
	 */
	private List<String> validatePrefixes(List<String> prefixes) {
		List<String> validatedPrefixes = new ArrayList<String>();
		for (String prefix : prefixes) {
			if (reader.getDictTrie().containsPrefix(prefix)) {
				validatedPrefixes.add(prefix);
			}
		}
		return validatedPrefixes;
	}

	/**
	 * 
	 * 
	 * @param possiblePrefixes
	 * @param currentPrefixList
	 * @return
	 */
	private List<String> multiplyPrefixes(List<String> possiblePrefixes, List<String> currentPrefixList) {
		if (0 == possiblePrefixes.size())
			return currentPrefixList;

		List<String> multipliedList = new ArrayList<String>();
		for (int i = 0; i < possiblePrefixes.size(); i++) {
			for (int j = 0; j < currentPrefixList.size(); j++) {
				multipliedList.add(possiblePrefixes.get(i).concat(currentPrefixList.get(j)));
			}
		}
		return multipliedList;

	}

}
