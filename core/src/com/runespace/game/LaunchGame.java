package com.runespace.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.runespace.game.handlers.CustomInputHandling;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.states.LoadingScreen;

public class LaunchGame extends com.badlogic.gdx.Game implements Screen {
	public static SpriteBatch batch;
	GameStateManager gsm;
	
	//Asset Mananger
	public static AssetManager assetManager;
	
	//hudCam
	public static  OrthographicCamera hudCam;
	
	@Override
	public void create () {
		//System.out.println(Constants.NAME);
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		//AssetManager
		assetManager = new AssetManager();



		//setup hudCam
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gsm.push(new LoadingScreen(gsm));
		
		//Screen
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.render(batch);
		gsm.update(Gdx.graphics.getDeltaTime());
		
		CustomInputHandling.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    @Override
    public void pause() {
        super.pause();
        gsm.pause();
		gsm.dispose();
    }

    @Override
    public void resume() {
        super.resume();
    }

	/**
	 * Called when this screen is no longer the current screen for a {}.
	 */
	@Override
	public void hide() {

	}

	/**
	 * Called when this screen becomes the current screen for a {}.
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

	@Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gsm.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }
}
