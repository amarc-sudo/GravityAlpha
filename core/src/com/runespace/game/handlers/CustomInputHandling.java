package com.runespace.game.handlers;

public class CustomInputHandling {
	//etats touches actuelle
	public static boolean[] keys;
	//etats touches frame
	public static boolean[] previousKeys;
	
	public static int NUM_KEYS = 3;
	
	public static int LEFT_KEY = 0;
	public static int RIGHT_KEY = 1;
	public static int UP_KEYS = 2;
	
	static {
		keys = new boolean[NUM_KEYS];
		previousKeys = new boolean[NUM_KEYS];
	}
	
	//mise  jour de previous keys
	public static void update() {
		for ( int i = 0 ; i < NUM_KEYS; i++) {

			previousKeys[i] = keys[i];
		}
	}
	
	public static void setKey(int keyIndex, boolean keyState) {
		keys[keyIndex] = keyState;
	}
	
	public static boolean isDown(int keyIndex, boolean keyState) {
		return keys[keyIndex];
	}
	public static boolean isPressed(int keyIndex) {

		return keys[keyIndex] && !previousKeys[keyIndex];
	}
	public static boolean isLongPressed(int keyIndex) {
		return keys[keyIndex];
	}
}
