package com.runespace.game.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class CustomInputProcessor extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Input.Keys.LEFT)
			CustomInputHandling.setKey(CustomInputHandling.LEFT_KEY, true);
		if (keycode == Input.Keys.RIGHT)
			CustomInputHandling.setKey(CustomInputHandling.RIGHT_KEY, true);
		if (keycode == Input.Keys.SPACE)
			CustomInputHandling.setKey(CustomInputHandling.UP_KEYS, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Input.Keys.LEFT)
			CustomInputHandling.setKey(CustomInputHandling.LEFT_KEY, false);
		if (keycode == Input.Keys.RIGHT)
			CustomInputHandling.setKey(CustomInputHandling.RIGHT_KEY, false);
		if (keycode == Input.Keys.SPACE)
			CustomInputHandling.setKey(CustomInputHandling.UP_KEYS, false);
		return true;
	}

}
