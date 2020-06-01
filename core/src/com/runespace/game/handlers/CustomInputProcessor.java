package com.runespace.game.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class CustomInputProcessor extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
			CustomInputHandling.setKey(keycode, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
        CustomInputHandling.setKey(CustomInputHandling.UP_KEYS, false);
		return true;
	}

}
