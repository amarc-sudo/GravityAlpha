package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.utils.Constants;


public abstract class GameState {
	protected Screen screen;
	protected OrthographicCamera cam;
	protected GameStateManager gsm ;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		cam = new OrthographicCamera();

	}

	public  void pause()
	{
	}


	public void resume()
	{
	}
	protected abstract void handleInput();

	public abstract void update(float dt);

	public abstract void render(SpriteBatch sb);

	public abstract void dispose();

	public abstract void resize(int width, int height);

	public Boolean checkResize(){
		if(Gdx.graphics.getWidth() != Constants.WINDOW_WIDTH || Gdx.graphics.getHeight() != Constants.WINDOW_HEIGHT) {
			return true;
		}
		else
			return false;

	}
}
