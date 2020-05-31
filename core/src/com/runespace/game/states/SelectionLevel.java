package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.utils.Constants;

import java.util.ArrayList;

import javax.xml.soap.Text;

/**
 * a Construire plus tard
 */
public class SelectionLevel extends GameState {
	private final FreeTypeFontGenerator generator;
	private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private final BitmapFont font;
	TextButton[][] buttonTab;
	TextButton.TextButtonStyle textButtonStyle;
	Table table;
	ArrayList<TextButton> textButtons;
	Stage stage;
	public SelectionLevel(GameStateManager gsm) {
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
	protected void handleInput() {
		// TODO Auto-generated method stub
		int x = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				x++;
				if (buttonTab[i][j].isPressed()) {
					String level = buttonTab[i][j].getName();
					this.gsm.set(new Level1(gsm, Constants.GRAVITY_WORLD, level, x));
				}
			}
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
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
		stage.getViewport().update(width,height);
		System.out.println("coucou");
	}

	public void createLevelSelector(){
		buttonTab = new TextButton[5][5];
		int x = 0;
		for(int i = 0 ; i < 5 ; i++){
			for(int j = 0 ; j < 5 ; j++){
				x++;
				buttonTab[i][j] = new TextButton("Level " +x, textButtonStyle);
				buttonTab[i][j].setBounds(buttonTab[i][j].getX(), buttonTab[i][j].getY(), buttonTab[i][j].getWidth(), buttonTab[i][j].getHeight());
				buttonTab[i][j].setName("level"+x);
				table.add(buttonTab[i][j]).padLeft(10).width(buttonTab[i][j].getWidth());
			}
			table.row();
		}
	}

}
