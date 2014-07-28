/**
 * 
 */
package de.number.Dictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trie {
	private TrieNode root;

	/**
	 * Constructor
	 */
	public Trie() {
		root = new TrieNode();
	}

	/**
	 * Adds a word to the Trie
	 * 
	 * @param word
	 */
	public void addWord(String word) {
		root.add(word);
	}

	/**
	 * Checks if the prefix is contained in the dictionary
	 * 
	 * @param prefix
	 * @return
	 */
	public boolean containsPrefix(String prefix) {

		TrieNode lastNode = root;
		for (int i = 0; i < prefix.length(); i++) {
			lastNode = lastNode.getNode(prefix.charAt(i));
			// If no node matches, then no words exist, return empty list
			if (lastNode == null)
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if input string is a complete word in the dictionary
	 * 
	 * @param word
	 * @return
	 */
	public boolean containsWord(String word) {

		TrieNode lastNode = root;
		for (int i = 0; i < word.length(); i++) {
			lastNode = lastNode.getNode(word.charAt(i));
			// If no node matches, then no words exist, return empty list
			if (lastNode == null)
				return false;
		}
		if (lastNode.isWord())
			return true;
		return false;
	}

	/**
	 * Returns word to be displayed which includes the special " character
	 * 
	 * @param wordToFormat
	 * @return
	 */
	public String getDisplayWordFinal(String wordToFormat) {

		String[] words = wordToFormat.split(" ");
		List<String> wordList = Arrays.asList(words);
		List<String> displayWord = new ArrayList<String>();

		for (String word : wordList) {
			// If digit - no encoding required
			if (isNumeric(word)) {
				displayWord.add(word);
				continue;
			}

			TrieNode lastNode = root;
			List<Integer> umlautPositions;
			for (int i = 0; i < word.length(); i++) {
				lastNode = lastNode.getNode(word.charAt(i));

			}
			// Get umlaut poitions from lastnode and insert umlaut in the given
			// indexes
			if (lastNode.isWord()) {
				umlautPositions = lastNode.getUmlautPositions();
				if (umlautPositions.size() == 0) {

					displayWord.add(word);
				} else {
					StringBuilder sb = new StringBuilder(word);
					int offsetIndex = 0;
					for (Integer position : umlautPositions) {
						sb.insert(position.intValue() + offsetIndex, '\"');
						offsetIndex++;
					}

					displayWord.add(sb.toString());
				}
			}

		}

		StringBuilder sb = new StringBuilder();
		for (String word : displayWord) {
			sb.append(word);
			sb.append(" ");

		}

		return sb.toString().trim();

	}

	/**
	 * Checks if the input string is numeric
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

}