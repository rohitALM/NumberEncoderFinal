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
	
	public String getDisplayWordFinal(String wordToFormat) {
		
		String[] words = wordToFormat.split(" ");
		 List<String> wordList = Arrays.asList(words);  
		 List<String> displayWord = new ArrayList<String>();
		 
		 for(String word : wordList) {
			 
			 	if(isNumeric(word)) {
			 		displayWord.add(word);
			 		continue;
			 	}
			 
				TrieNode lastNode = root;
				List<Integer> umlautPositions;
				for (int i = 0; i < word.length(); i++) {
					lastNode = lastNode.getNode(word.charAt(i));
					// If no node matches, then no words exist, return empty list

				}
				if (lastNode.isWord()) {
					umlautPositions = lastNode.getUmlautPositions();
					if(umlautPositions.size() == 0) {
						//return word;
						displayWord.add(word);
					} else {
						StringBuilder sb = new StringBuilder(word);
						int offsetIndex = 0;
						for(Integer position : umlautPositions) {
							sb.insert(position.intValue()+offsetIndex, '\"');
							offsetIndex++;
						}
						//return sb.toString();
						displayWord.add(sb.toString());
					}
				}
			 
		 }
		 
		 StringBuilder sb = new StringBuilder();
			for(String word : displayWord) {
				sb.append(word);
				sb.append(" ");
				
			}

		return sb.toString().trim();
		 
		 
		
//		TrieNode lastNode = root;
//		List<Integer> umlautPositions;
//		for (int i = 0; i < word.length(); i++) {
//			lastNode = lastNode.getNode(word.charAt(i));
//			// If no node matches, then no words exist, return empty list
//
//		}
//		if (lastNode.isWord()) {
//			umlautPositions = lastNode.getUmlautPositions();
//			if(umlautPositions.size() == 0) {
//				return word;
//			} else {
//				StringBuilder sb = new StringBuilder(word);
//				int offsetIndex = 0;
//				for(Integer position : umlautPositions) {
//					sb.insert(position.intValue()+offsetIndex, '\"');
//					offsetIndex++;
//				}
//				return sb.toString();
//			}
//		}
//		return word;
		
	}
	
	private  boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

	/**
	 * Returns word to be displayed which includes the special " character
	 * 
	 * @param word
	 * @return
	 */
	public String getDisplayWord(String word) {

		TrieNode currentNode = root;
		String currentDisplay = "";

		for (int i = 0; i < word.length(); i++) {
			boolean isLastCharacter = false;

			if (i == (word.length() - 1)) {
				isLastCharacter = true;
			} else if (word.charAt(i + 1) == ' ') {
				isLastCharacter = true;
			}

			if (word.charAt(i) == ' ') {
				currentDisplay += " ";
				currentNode = root;
				continue;
			}

			if (word.charAt(i) >= '0' && word.charAt(i) <= '9') {
				currentDisplay += word.charAt(i);
				continue;
			}
			currentNode = currentNode.getNode(word.charAt(i));
			if (currentNode == null)
				break;
			currentDisplay = currentDisplay + currentNode.returnDisplay(word.charAt(i) , isLastCharacter);
		}

		return currentDisplay;
	}

}