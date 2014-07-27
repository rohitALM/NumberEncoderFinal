/**
 * 
 */
package de.number.Dictionary;


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

			if (word.charAt(i) == ' ') {
				currentDisplay += " ";
				currentNode = root;
				continue;
			}

			if (word.charAt(i) >= '0' && word.charAt(i) <= '9') {
				currentDisplay += word.charAt(i);
				continue;
			}
			currentNode = currentNode.returnChild(word.charAt(i));
			if (currentNode == null)
				break;
			currentDisplay = currentDisplay + currentNode.returnDisplay(word.charAt(i));
		}

		return currentDisplay;
	}

}