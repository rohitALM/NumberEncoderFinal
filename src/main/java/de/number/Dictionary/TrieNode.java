package de.number.Dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class TrieNode {

	private TrieNode parent;
	// private TrieNode[] children;
	private HashMap<Character, TrieNode> children;

	private boolean isLeaf; // Quick way to check if any children exist
	private boolean isWord; // Does this node represent the last character of a
							// word
	private char character; // The character this node represents
	
	private boolean isFollowedByUmlaut;

	/**
	 * Constructor for top level root node.
	 */
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
		isLeaf = true;
		isWord = false;
		isFollowedByUmlaut = false;
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
		boolean umlautAddCondition = false;
		isLeaf = false;
		if (word.length() < 1)
			return;

		char firstChar = word.charAt(0);

		TrieNode childrenCharPos = children.get(firstChar);

		if (childrenCharPos == null) {
			umlautAddCondition = true;
			childrenCharPos = new TrieNode(firstChar);
			childrenCharPos.parent = this;
			children.put(firstChar, childrenCharPos);
		}

		if (word.length() > 1) {
			if(umlautAddCondition && '\"' == word.charAt(1)) {
				this.isFollowedByUmlaut = true;
			} else {
				this.isFollowedByUmlaut = false;
			}
			childrenCharPos.add(word.substring(1));
		} else {
			childrenCharPos.isWord = true;
			this.isFollowedByUmlaut = false;
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
//		if(this.isFollowedByUmlaut) {
//			TrieNode tempNode = children.get(c);
//			if(null == tempNode) {
//				System.out.println("waaaaa\"");
//			}
//			return tempNode.children.get(tempNode.character);
//		}
//		TrieNode tempNode = children.get(c);
//		if(null != tempNode)
//		System.out.println(tempNode.character);
//		if(null != tempNode && '\"' == tempNode.character) {
//			System.out.println("Woop");
//		}
		return children.get(c);
	}

	/**
	 * Returns a List of String objects which are lower in the hierarchy that
	 * this node.
	 * 
	 * @return
	 */
	protected List<String> getWords() {
		// Create a list to return
		List<String> list = new ArrayList<String>();

		// If this node represents a word, add it
		if (isWord) {
//			String temp  = this.toString().replaceAll("[^A-Za-z0-9]", "");
//			System.out.println(temp);
			list.add(this.toString());
		}

		// If any children
		if (!isLeaf) {
			Set<Entry<Character, TrieNode>> set = children.entrySet();
			Iterator<Entry<Character, TrieNode>> i = set.iterator();
			while (i.hasNext()) {
				TrieNode child = i.next().getValue();
				if (child != null) {
					list.addAll(child.getWords());
				}

			}
		}
		return list;
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