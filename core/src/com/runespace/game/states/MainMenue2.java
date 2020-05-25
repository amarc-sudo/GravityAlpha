package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.utils.Constants;

public class MainMenue2 extends GameState {

	
	/*
	private Texture background, selectLevel;
	private GlyphLayout playGlyph, glyph2;
	private BitmapFont font, welcome, gameOver, level, level2;
	*/
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private FreeTypeFontGenerator generator;
	/*
	private Boolean gameEnd;
	*/
	
	Stage stage;
    TextButton button, button1, buttonTab[];
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    Image backImage;
	Texture background;
	

	
	public MainMenue2(GameStateManager gsm, Boolean gameEnd) {
		super(gsm);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
        background = LaunchGame.assetManager.get("background.jpg");
		backImage = new Image(background);
		stage.addActor(backImage);
		backImage.setSize(Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
		Gdx.input.setInputProcessor(stage);
        
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);
        
        font.setColor(new Color( Color.BLACK));
        skin = new Skin();
   
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;

        buttonTab = new TextButton[5];

    
        for(int i = 0 ; i < 5 ; i++){
        	if(i != 2)
        		buttonTab[i] = new TextButton("Level " +(i+1), textButtonStyle);
			else
				buttonTab[i] = new TextButton("highscore ", textButtonStyle);
        	buttonTab[i].setWidth(150f);
			buttonTab[i].setHeight(100f);
			buttonTab[i].setPosition(Gdx.graphics.getWidth()/1.5f, Gdx.graphics.getHeight()/1.5f - ((buttonTab[0].getHeight()*1.5f)*i)/2);
			stage.addActor(buttonTab[i]);
        }
        
  
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		if(buttonTab[0].isPressed())
			this.gsm.set(new Level3(gsm, Constants.GRAVITY_WORLD));
		if(buttonTab[1].isPressed())
			this.gsm.set(new Level4(gsm, Constants.GRAVITY_WORLD));
        if(buttonTab[2].isPressed())
            this.gsm.set(new ScoreVieweur(gsm));
		if(buttonTab[3].isPressed())
			this.gsm.set(new Level4(gsm, Constants.GRAVITY_WORLD));
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		 Gdx.input.setInputProcessor(stage);
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
        stage.draw();
       
	}

	@Override
	public void dispose() {
		stage.dispose();

	}

	@Override
	public void resize(int width, int height) {
		System.out.println("resize");
		stage.getViewport().update(width, height, true);
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
	}

}
