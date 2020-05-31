package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.GameStateManager;


public class LoadingScreen extends GameState {

	private BitmapFont font;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private FreeTypeFontGenerator generator;

	

	public LoadingScreen(GameStateManager gsm) {
		super(gsm);
		
		
		//setup
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		font = generator.generateFont(parameter);
		
		queueAssets();
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		if(LaunchGame.assetManager.update()){
			this.gsm.set(new MainMenu(gsm, false));
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		//setup sb cam
		sb.setProjectionMatrix(LaunchGame.hudCam.combined);
		sb.begin();
		font.draw(sb, "LOADING : " + (int) (LaunchGame.assetManager.getProgress()*100),  (LaunchGame.hudCam.viewportWidth*0.5f), LaunchGame.hudCam.viewportHeight * 0.1f);
		sb.end();


	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {

	}

	public void queueAssets(){
		LaunchGame.assetManager.setLoader(TiledMap.class,  new TmxMapLoader(new InternalFileHandleResolver()));
		LaunchGame.assetManager.load("maps/level1.tmx", TiledMap.class);
		LaunchGame.assetManager.setLoader(TiledMap.class,  new TmxMapLoader(new InternalFileHandleResolver()));
		LaunchGame.assetManager.load("maps/level2.tmx", TiledMap.class);
		LaunchGame.assetManager.setLoader(TiledMap.class,  new TmxMapLoader(new InternalFileHandleResolver()));
		LaunchGame.assetManager.load("maps/level3.tmx", TiledMap.class);

		//load Textures
		LaunchGame.assetManager.load("Sprites/player.png", Texture.class);
		LaunchGame.assetManager.load("Sprites/p1_stand.png", Texture.class);
		LaunchGame.assetManager.load("Sprites/p1_hurt.png", Texture.class);
		LaunchGame.assetManager.load("Sprites/p1_jump.png", Texture.class);
		LaunchGame.assetManager.load("Sprites/frame.png", Texture.class);
		LaunchGame.assetManager.load("Sprites/sprite_de_base.png", Texture.class);
		LaunchGame.assetManager.load("Sprites/sprite_de_base2.png", Texture.class);
        LaunchGame.assetManager.load("Sprites/sprite_de_chute.png", Texture.class);
        LaunchGame.assetManager.load("Sprites/sprite_de_saut.png", Texture.class);
        LaunchGame.assetManager.load("Sprites/run.png", Texture.class);
		LaunchGame.assetManager.load("button.png", Texture.class);
		LaunchGame.assetManager.load("background.jpg", Texture.class);
		LaunchGame.assetManager.load("background/aled.png", Texture.class);
		LaunchGame.assetManager.load("music/music.mp3", Music.class);

	}

}
