package de.number.NumberEncoder;

import java.util.ArrayList;
import java.util.List;



/**
 * Entry Point Class
 * 
 * @author Rohit
 *
 */
public class EntryPoint {
	public static void main(String[] args) {

		Encoder encoder = new Encoder();
		encoder.encode();
		List<String> wassup = new ArrayList<String>();
		wassup.add("Do");
		wassup.add("Re");
		wassup.add("Mi");
		StringBuilder sb = new StringBuilder();
		for(String word : wassup) {
			sb.append(word);
			sb.append(" ");
			
		}
		System.out.println(sb.toString().trim());

	}
}
