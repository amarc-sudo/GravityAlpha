package com.runespace.game.handlers;

import java.util.Stack;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.runespace.game.states.GameState;

public class GameStateManager implements Screen, ApplicationListener {
	Stack<GameState> gameStates;
	
	public GameStateManager() {
		gameStates = new Stack<GameState>();
	}
	
	public void push(GameState gameState) {
		gameStates.push(gameState);
	}
	public void set(GameState gameState) {
		gameStates.pop().dispose();
		gameStates.push(gameState);
	}
	public void update(float dt) {
		gameStates.peek().update(dt);
	}
	public void render(SpriteBatch sb) {
		gameStates.peek().render(sb);
	}
	public void pause() {
		gameStates.peek().pause();
	}

	/**
	 * @see ApplicationListener#resume()
	 */
	@Override
	public void resume() {

	}

	/**
	 * Called when this screen is no longer the current screen for a {@link Game}.
	 */
	@Override
	public void hide() {

	}

	/**
	 * Called when this screen should release all resources.
	 */
	@Override
	public void dispose() {
		gameStates.peek().dispose();
	}

	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	@Override
	public void show() {

	}

	/**
	 * Called when the screen should render itself.
	 *
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {

	}

	/**
	 * Called when the {@link Application} is first created.
	 */
	@Override
	public void create() {

	}

	public void resize(int width, int height){gameStates.peek().resize(width, height);}

	/**
	 * Called when the {@link Application} should render itself.
	 */
	@Override
	public void render() {

	}
}
