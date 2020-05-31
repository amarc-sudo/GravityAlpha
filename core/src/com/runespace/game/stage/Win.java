package com.runespace.game.stage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Win extends ApplicationAdapter {

	//Stage set
	private Stage stage;
	//font set
	private BitmapFont font;


	//button set
	private Button buttonRetry;
	private Button buttonMenu;
	private TextButtonStyle textButtonStyle;
	public Win()  {
		
		//Create the main Stage
		stage = new Stage();
		
		
		//create the table for fill the stage
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		//generate the font for the button
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);
		
		textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
		
		//create and generate the button
		buttonRetry = new TextButton("Replay for better ?", textButtonStyle);
		table.add(buttonRetry).expandX().padTop(10);
		table.row();
		buttonMenu = new TextButton("Menu", textButtonStyle);
		buttonMenu.setPosition(10, 10);
		buttonMenu.setSize(10, 10);
		table.add(buttonMenu).expandX().padTop(10);
		buttonMenu.setVisible(true);
		
		
		
		stage.addActor(table);
	}
	public void dispose(){
		font.dispose();
		stage.dispose();
	}

	public Stage Stage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Button getButtonRetry() {
		return buttonRetry;
	}

	public void setButtonRetry(Button buttonRetry) {
		this.buttonRetry = buttonRetry;
	}

	public Button getButtonMenu() {
		return buttonMenu;
	}

	public void setButtonMenu(Button buttonMenu) {
		this.buttonMenu = buttonMenu;
	}
	public boolean retryPressed(){
		return buttonRetry.isPressed();
	}
	public boolean menuPressed(){
		return buttonMenu.isPressed();
	}

	public void resize(int width, int height){
		stage.getViewport().update(width, height);
	}
}
