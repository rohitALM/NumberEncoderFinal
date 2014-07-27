package de.number.NumberEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import de.number.Dictionary.Trie;

/**
 * Read the dictionary input file and store in memory
 * 
 * @author Rohit
 * 
 */
public class DictionaryReader {

	private Trie dictTrie = new Trie();
	
	/**
	 * Reads the Dictionary and stores it in the trie
	 */
	public void readFile() {
		File file = new File("Dictionary.txt");

		try {

			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				String word = scan.next();
				dictTrie.addWord(word);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Trie getDictTrie() {
		return dictTrie;
	}

}
