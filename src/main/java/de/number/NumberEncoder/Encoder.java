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
 * Encapsulates the word builder logic
 * 
 * @author Rohit
 * 
 */
public class Encoder {

	private DictionaryReader reader;
	/** Stores the final output after every number is encoded */
	private List<String> finalOutput = new ArrayList<String>();

	/**
	 * Constructor
	 */
	Encoder() {
		init();

	}

	/**
	 * Initializes the Trie which stores he dictionary . Initializes the digit
	 * to character map
	 */
	private void init() {
		reader = new DictionaryReader();
		reader.readFile();
		DigitWordMap.buildMap();
		//System.out.println(reader.getDictTrie().containsPrefix("boss"));
//		System.out.println(reader.getDictTrie().containsWord("bosses"));
//		System.out.println(reader.getDictTrie().getDisplayWordFinal("blau"));
	}

	/**
	 * Reads the input file sequentially and encodes each number
	 */
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

	/**
	 * Encodes a single number
	 * 
	 * @param input
	 *            The input number
	 */
	private void computeOutputString(String input) {
		String displayNumber = input;
		// Retain only numbers
		input = input.replaceAll("\\D+", "");
		finalOutput.clear();
		List<String> possiblePrefixes = new ArrayList<String>();
		String currentWord = "";
		constructOutput(input, possiblePrefixes, currentWord);
		for (String output : finalOutput) {
			System.out.println(displayNumber + ": " + reader.getDictTrie().getDisplayWordFinal(output));
			//System.out.println(displayNumber + ": " +output);
		}

	}

	/**
	 * This method takes the input and recursively calculates all possible
	 * outputs
	 * 
	 * @param input
	 *            The input number
	 * @param possiblePrefixes
	 *            possible prefixes which can be used to form a word
	 * @param currentWord
	 *            Current word resolved from the number till now
	 * @return
	 */
	private boolean constructOutput(String input, List<String> possiblePrefixes, String currentWord) {

		// Exit from recursion if end of input is reached storing current word
		// in final output string
		if (0 == input.length()) {
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
		// If no prefixes exist - Check if digit can be encoded as is
		if (0 == newPossibilities.size()) {

			if (0 != possiblePrefixes.size() && possiblePrefixes.get(0).length() > 1) {
				return false;
			}

			if (0 != currentWord.length() && Character.isDigit(currentWord.charAt(currentWord.length() - 2))) {// Check
																												// for
																												// space
				return false;
			}

			else {

				String tempCurrentWord = currentWord;
				tempCurrentWord = tempCurrentWord.concat(num + " ");

				if (!constructOutput(input.substring(1), new ArrayList<String>(), tempCurrentWord))
					return false;
			}

		} else {

			boolean atleastOneAttempt = false;
			List<String> completeWords = checkForCompleteWords(newPossibilities);
			// If any complete dictionary words are found - Update Current word
			// and recurse
			if (0 != completeWords.size()) {
				for (String word : completeWords) {
					String tempCurrentWord = currentWord;
					tempCurrentWord = tempCurrentWord.concat(word + " ");
					boolean completeWordCall = constructOutput(input.substring(1), new ArrayList<String>(), tempCurrentWord);
					atleastOneAttempt = atleastOneAttempt || completeWordCall;
				}
			}

			boolean newCall = constructOutput(input.substring(1), newPossibilities, currentWord);
			atleastOneAttempt = atleastOneAttempt || newCall;

			if (!atleastOneAttempt) {
				if (0 != possiblePrefixes.size() && possiblePrefixes.get(0).length() > 1) {
					return false;
				}

				if (0 != newPossibilities.size() && newPossibilities.get(0).length() > 1) {
					return false;
				}

				if (0 != currentWord.length() && Character.isDigit(currentWord.charAt(currentWord.length() - 2)))
					return false;

				String tempCurrentWord = currentWord;
				tempCurrentWord = tempCurrentWord.concat(num + " ");
				if (!constructOutput(input.substring(1), new ArrayList<String>(), tempCurrentWord))
					return false;

			}

		}

		return true;

	}

	/**
	 * Checks if words exist in dictionary
	 * 
	 * @param possibleWords
	 *            words to check in dictionary
	 * @return Complete dictionary words
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

	/**
	 * Validate prefixes by checking against entries in dictionary Removes
	 * prefixes which are not present
	 * 
	 * @param prefixes
	 *            Prefixes to be validated
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
	 * Finds all combinations of prefixes which can be had from combining the
	 * two lists
	 * 
	 * @param possiblePrefixes
	 *            Prefixes obtained from previous digits
	 * @param currentPrefixList
	 *            Prefix list from Currnt digit
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
