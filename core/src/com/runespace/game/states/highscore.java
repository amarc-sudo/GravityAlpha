package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.utils.Constants;

public class highscore extends GameState {

	Stage stage;
    TextButton button;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private FreeTypeFontGenerator generator;
	public highscore(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);
        font.setColor(new Color( Color.BLACK));
        skin = new Skin();

        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;

        button = new TextButton("Button1", textButtonStyle);
        button.setWidth(150f);
        button.setHeight(100f);
        button.setPosition(Constants.VIEWPORT_WIDTH/1.5f, Constants.VIEWPORT_HEIGHT/1.5f);
        stage.addActor(button);
        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                
            }
        });

        
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		if(button.isPressed()) {
			//this.gsm.set(new Level1(gsm, Constants.GRAVITY_WORLD), "level1", 1);
		}
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		stage.draw();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		
	}

	@Override
	public void resize(int width, int height) {

	}

}
