package de.number.Dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieNode {

	private TrieNode parent;
	private HashMap<Character, TrieNode> children;

	private boolean isWord; // Does this node represent the last character of a
							// word
	private char character; // The character this node represents

	private boolean IsFollowedByUmlaut;// Checks if the preceding character is
										// an umlaut
	private List<Integer> umlautPositions; // If this node represents the last character of a word , store the positions of the umlaut

	/**
	 * Constructor for top level root node.
	 */
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
		isWord = false;
		IsFollowedByUmlaut = false;
		umlautPositions = new ArrayList<Integer>();
	}

	/**
	 * Constructor for child node.
	 */
	public TrieNode(char character) {
		this();
		this.character = character;
	}
	
	protected void add(String word)  {
		add(word, new ArrayList<Integer>() , 1 );
	}

	/**
	 * Adds a word to this node. This method is called recursively and adds
	 * child nodes for each successive letter in the word, therefore recursive
	 * calls will be made with partial words.
	 * 
	 * @param word
	 *            the word to add
	 */
	private void add(String word , List<Integer> umlautPosition , int index) {
		
		if (word.length() < 1)
			return;

		char firstChar = word.charAt(0);

		TrieNode childrenCharPos = children.get(firstChar);

//		if (word.equalsIgnoreCase("\"")) {
//
//			childrenCharPos.add(word.substring(1));
//
//		} else {

			if (childrenCharPos == null) {

				childrenCharPos = new TrieNode(firstChar);
				childrenCharPos.parent = this;
				children.put(firstChar, childrenCharPos);
			}

			if (word.length() > 1) {

				if (word.charAt(1) == '\"') {
					childrenCharPos.IsFollowedByUmlaut = true;
				
					if (word.length() > 2) {
						word = word.substring(2);
						umlautPosition.add(index);
						childrenCharPos.add(word ,umlautPosition ,++index);
					}
					else {
						childrenCharPos.umlautPositions.add(index);
						childrenCharPos.isWord = true;
						return;
					}
				} else
					word = word.substring(1);

					childrenCharPos.add(word , umlautPosition , ++index);
			} else {
				childrenCharPos.umlautPositions = umlautPosition;
				childrenCharPos.isWord = true;
				return;

			}
//		}

	}

	public List<Integer> getUmlautPositions() {
		return umlautPositions;
	}

	/**
	 * Returns the child TrieNode representing the given char, or null if no
	 * node exists.
	 * 
	 * @param c
	 * @return
	 */

	protected TrieNode getNodeWithUmlaut(char c) {
		if(this.IsFollowedByUmlaut) {
			TrieNode umlautNode = children.get('\"');
			return umlautNode.children.get(c);
		}
		return children.get(c);
	}
	
	protected TrieNode getNode(char c) {
		return children.get(c);
	}
	
	public boolean isIsFollowedByUmlaut() {
		return IsFollowedByUmlaut;
	}
	
	

	/**
	 * Returns the character to be displayed
	 * Appends " if required
	 * 
	 * @param word
	 * @param isLastCharacter
	 * @return
	 */
	protected String returnDisplay(char word , boolean isLastCharacter) {

		if (IsFollowedByUmlaut && !isLastCharacter)
			return word + "\"";
		else
			return word + "";

	}
	
	/**
	 * Returns the children 
	 * 
	 * @param word
	 * @return
	 */
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