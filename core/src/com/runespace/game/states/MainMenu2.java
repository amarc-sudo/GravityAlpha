package com.runespace.game.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.runespace.game.LaunchGame;
import com.runespace.game.MapsTiled.MapsTiledLevel;
import com.runespace.game.bdd.MySql;
import com.runespace.game.entities.Player;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.item.Coin;
import com.runespace.game.scoreboard.ScoreBoard;
import com.runespace.game.utils.Constants;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainMenu2 extends GameState implements ApplicationListener {

	ScoreBoard scoreArray;

	private final FreeTypeFontGenerator generator;
	private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private final BitmapFont font;
	TextButton[] buttonTab;
	TextButton.TextButtonStyle textButtonStyle;
	Table table;
	ArrayList<TextButton> textButtons;
	Stage stage;
	World world;
	MapsTiledLevel maps;

	public MainMenu2(GameStateManager gsm) {
		super(gsm);

		stage = new Stage();

		table = new Table();
		Gdx.input.setInputProcessor(stage);
		table.setDebug(true);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);

		font.setColor(new Color( Color.WHITE));
		//skin = new Skin();
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		textButtons = new ArrayList<TextButton>();
		table.defaults().width(100);
		createLevelSelector();
		// TODO Auto-generated constructor stub
		table.setFillParent(true);

		stage.addActor(table);

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
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		//super.handleInput();
		for(int i = 0 ; i < 4 ; i++){
			if(buttonTab[i].isPressed()){
				String text = buttonTab[i].getName();
				if(text == Constants.NAME_MENU[0])
					gsm.set(new SelectionLevel(gsm));
				if(text == Constants.NAME_MENU[1])
					gsm.set(new ScoreVieweur(gsm));
				if(text == Constants.NAME_MENU[2])
					gsm.set(new OptionScreen(gsm));
				if(text == Constants.NAME_MENU[3]);
				//gsm.set(new LoadingScreen(gsm));
			}
		}
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}


	public void createLevelSelector(){
		buttonTab = new TextButton[5];
		int x = 0;
		for(int i = 0 ; i < 4 ; i++) {
			buttonTab[i] = new TextButton(Constants.NAME_MENU[i], textButtonStyle);
			buttonTab[i].setBounds(buttonTab[i].getX(), buttonTab[i].getY(), buttonTab[i].getWidth(), buttonTab[i].getHeight());
			buttonTab[i].setName(Constants.NAME_MENU[i]);
			table.add(buttonTab[i]).width(buttonTab[i].getWidth());
			table.row();
		}
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
		stage.getViewport().update(width,height);
	}

	@Override
	public void render() {

	}
}
