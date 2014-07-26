package de.number.NumberEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.number.Dictionary.Trie;

/**
 * Read the dictionary input file and store in memory
 * 
 * @author Rohit
 * 
 */
public class DictionaryReader {

	private Map<String, String> dictMap = new HashMap<String, String>(75000);
	private Trie dictTrie = new Trie();

	public void readFile() {
		File file = new File("Dictionary.txt");

		try {

			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				String word = scan.next();
				//feedDictMap(word);

				dictTrie.addWord(word);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//populateDictTrie();
		
		List<String> words = dictTrie.getWords("o");
		for(String word : words) {
			System.out.println(word);
		}
	}

	public Trie getDictTrie() {
		return dictTrie;
	}

	/**
	 * This method formats the words from the dictionary It removes special
	 * characters to store as the key and keeps the orogonal word as the value
	 * 
	 * @param word
	 */
	private void feedDictMap(String word) {
		String formattedDictWord = word.replaceAll("[^a-zA-Z]+", "");
		dictMap.put(formattedDictWord, word);
	}

	/**
	 * Method populates the trie from the keyset of the dictionary map
	 */
	private void populateDictTrie() {
		for (String word : dictMap.keySet()) {
			dictTrie.addWord(word);
		}
	}

}
