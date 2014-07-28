/**
 * 
 */
package de.number.NumberEncoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the Digit to Character Map in MemoryF
 * 
 * @author Rohit
 * 
 */
public class DigitWordMap {

	private static Map<Integer, List<String>> digitToWordMap = new HashMap<Integer, List<String>>(10);

	private DigitWordMap() {

	}
	/**
	 * Builds the digit word map
	 */
	public static void buildMap() {

		List<String> charList0 = Arrays.asList("e", "E");
		List<String> charList1 = Arrays.asList("j", "n", "q", "J", "N", "Q");
		List<String> charList2 = Arrays.asList("r", "w", "x", "R", "W", "X");
		List<String> charList3 = Arrays.asList("d", "s", "y", "D", "S", "Y");
		List<String> charList4 = Arrays.asList("f", "t", "F", "T");
		List<String> charList5 = Arrays.asList("a", "m", "A", "M");
		List<String> charList6 = Arrays.asList("c", "i", "v", "C", "I", "V");
		List<String> charList7 = Arrays.asList("b", "k", "u", "B", "K", "U");
		List<String> charList8 = Arrays.asList("l", "o", "p", "L", "O", "P");
		List<String> charList9 = Arrays.asList("g", "h", "z", "G", "H", "Z");
		digitToWordMap.put(0, charList0);
		digitToWordMap.put(1, charList1);
		digitToWordMap.put(2, charList2);
		digitToWordMap.put(3, charList3);
		digitToWordMap.put(4, charList4);
		digitToWordMap.put(5, charList5);
		digitToWordMap.put(6, charList6);
		digitToWordMap.put(7, charList7);
		digitToWordMap.put(8, charList8);
		digitToWordMap.put(9, charList9);

	}

	public static Map<Integer, List<String>> getDigitToWordMap() {
		return digitToWordMap;
	}
}
