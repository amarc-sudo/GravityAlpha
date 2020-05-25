/**
 * 
 */
package com.runespace.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author aurel
 *
 */
public class Hud  {
	
	public Stage stage;
	@SuppressWarnings("unused")
	private SpriteBatch sb;
	@SuppressWarnings("unused")
	private Viewport viewport;
	
	public int score;
	
	
	private Button buttonPause;
	private TextButtonStyle textButtonStyle;
	
	@SuppressWarnings("unused")
	private int jump;
	
	Label scoreLabel, jumpLabel;
	
	public Hud(Viewport viewport, SpriteBatch sb) {
		this.sb = sb;
		this.viewport = viewport;
		stage = new Stage();
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		Table tableBot = new Table();
	
		tableBot.setFillParent(true);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		BitmapFont font;
		font = generator.generateFont(parameter);
		scoreLabel = new Label(""+score, new Label.LabelStyle(font, Color.WHITE));
		jumpLabel = new Label(""+score, new Label.LabelStyle(font, Color.WHITE));
		table.add(scoreLabel).expandX().padTop(10);
		tableBot.bottom();
		tableBot.add(jumpLabel).expandX().padBottom(10);
		stage.addActor(table);
		stage.addActor(tableBot);
		
		textButtonStyle = new TextButtonStyle();
	    textButtonStyle.font = font;

	    buttonPause = new TextButton("Pause", textButtonStyle);
	    buttonPause.setWidth(150f);
	    buttonPause.setHeight(100f);
	    buttonPause.setPosition(0, 0);
		stage.addActor(buttonPause);
	}
	public void update(int i, int jump) {
		this.score = i;
		scoreLabel.setText("Score :"+i);
		this.jump = jump;
		jumpLabel.setText("Jump :"+jump);
	}

	public boolean pausePressed(){ return buttonPause.isPressed();}
	
}
