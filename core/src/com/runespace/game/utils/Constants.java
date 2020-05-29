package com.runespace.game.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	public static String NAME;
	public final static int VIEWPORT_WIDTH = 960+64*2;
	public final static int VIEWPORT_HEIGHT = 512+64*2;

	public final static int WINDOW_WIDTH = 1280;
	public final static int WINDOW_HEIGHT = 720;
	
	public final static String GAME_TITLE = "Gravity";
	
	public final static float PIXEL_METER = 100;
	
	public final static float GRAVITY = -9.8f;
	
	//bit filtering
	public final static short PLARTFORM_BIT = 2;
	public final static short BOX_BIT = 4;
	public final static short SPHERE_BIT = 8;
	public final static short free1 = 16;
	public final static short free2 = 32;
	public final static short free3 = 64;
	public final static short free4 = 128;
	public final static short HEAD_BIT = 256;
	public final static short COINT_BIT = 512;
	public final static short COIN_USE_BIT = 1024;
	public final static short STARS_BIT = 2048;
	public final static short TORCH_BIT = 4096;

	public final static short SPEED = 20;
	public final static short JUMP_FORCE = 400;

	public final static int FILTER_BACKGROUND = 0;
	public final static int FILTER_GROUND_BOX = 1;
	public final static int FILTER_DEAD = 2;
	public final static int FILTER_SENSORG = 3;
	public final static int FILTER_COIN = 4;
	public final static int FILTER_WIN = 5;
	public final static int FILTER_TILE_COIN = 10;
	public final static int FILTER_LIGHTSTARS = 11;
	public final static int FILTER_SPAWN = 12;
	
	public final static int WIDTH_PLAYER = 72;
	public final static int HEIGHT_PLAYER = 97;
	
	public final static Vector2 GRAVITY_WORLD = new Vector2(0, GRAVITY);
}
