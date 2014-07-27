package de.number.Dictionary;

import java.util.HashMap;

public class TrieNode {

	private TrieNode parent;
	private HashMap<Character, TrieNode> children;

	private boolean isWord; // Does this node represent the last character of a
							// word
	private char character; // The character this node represents

	private boolean IsFollowedByUmlaut;// Checks if the preceding character is
										// an umlaut

	/**
	 * Constructor for top level root node.
	 */
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
		isWord = false;
		IsFollowedByUmlaut = false;
	}

	/**
	 * Constructor for child node.
	 */
	public TrieNode(char character) {
		this();
		this.character = character;
	}

	/**
	 * Adds a word to this node. This method is called recursively and adds
	 * child nodes for each successive letter in the word, therefore recursive
	 * calls will be made with partial words.
	 * 
	 * @param word
	 *            the word to add
	 */
	protected void add(String word) {

		char firstChar = word.charAt(0);

		TrieNode childrenCharPos = children.get(firstChar);

		if (word.equalsIgnoreCase("\"")) {

			childrenCharPos.add(word.substring(1));

		} else {

			if (childrenCharPos == null) {

				childrenCharPos = new TrieNode(firstChar);
				childrenCharPos.parent = this;
				children.put(firstChar, childrenCharPos);
			}

			if (word.length() > 1) {

				if (word.charAt(1) == '\"') {
					childrenCharPos.IsFollowedByUmlaut = true;
					if (word.length() > 2)
						word = word.substring(2);
					else {
						childrenCharPos.isWord = true;
						return;
					}
				} else
					word = word.substring(1);

				childrenCharPos.add(word);
			} else {
				childrenCharPos.isWord = true;
				return;

			}
		}

	}

	/**
	 * Returns the child TrieNode representing the given char, or null if no
	 * node exists.
	 * 
	 * @param c
	 * @return
	 */

	protected TrieNode getNode(char c) {

		return children.get(c);
	}

	protected String returnDisplay(char word) {

		if (IsFollowedByUmlaut)
			return word + "\"";
		else
			return word + "";

	}

	protected TrieNode returnChild(char word) {

		return children.get(word);

	}

	public boolean isWord() {
		return isWord;
	}

	/**
	 * Gets the String that this node represents. For example, if this node
	 * represents the character t, whose parent represents the charater a, whose
	 * parent represents the character c, then the String would be "cat".
	 * 
	 * @return
	 */
	public String toString() {
		if (parent == null) {
			return "";
		} else {
			return parent.toString() + new String(new char[] { character });
		}
	}
}