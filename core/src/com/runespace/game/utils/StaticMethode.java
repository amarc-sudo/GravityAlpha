package com.runespace.game.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StaticMethode {
	
	public static String getCommand(String command) {
		String line = null;
		try {
		    Process process = Runtime.getRuntime().exec(command);
		 
		    BufferedReader reader = new BufferedReader(
		            new InputStreamReader(process.getInputStream()));
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		 
		    reader.close();
		 
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return line;
	}
}
