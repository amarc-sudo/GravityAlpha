package com.runespace.game.states;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.runespace.game.LaunchGame;
import com.runespace.game.bdd.MySql;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.scoreboard.ScoreBoard;

public class ScoreVieweur extends GameState {

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private FreeTypeFontGenerator generator;
	/*
	private Boolean gameEnd;
	*/

    Stage stage;
    TextButton button[];
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    Image backImage;
    Texture background;
    ScoreBoard highscore;
    public ScoreVieweur(GameStateManager gsm) {
        super(gsm);
        //highscore = new ScoreBoard();
        //highscore.load("level3");
        stage = new Stage();
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

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        button = new TextButton[4];
        String text = null;
        MySql.connect("gravity_guest", "GravityMarc");
        for(int i = 0 ;i < 4; i++ ) {
            try {
                //highscore.load("level"+(i+1));
                if(i<3){
                    text = levelScore(i+1);
                    button[i] = new TextButton(text, textButtonStyle);}
                else{

                    button[i] = new TextButton("menu", textButtonStyle);
                }

            } catch (ClassCastException e) {
            	Gdx.app.error("Exception thrown : ", "bug ScoreVieweur" , e);
            } catch (NoSuchElementException e) {
                button[i] = new TextButton("level : Nothing", textButtonStyle);
                Gdx.app.error("Exception thrown : ", "bug ScoreVieweur" , e);
            }
            button[i].setWidth(150f);
            button[i].setHeight(100f);
            if(i<3)
                button[i].setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/1.5f  - ((button[0].getHeight()*1.5f)*i)/2);
            else {
                button[i].setPosition(Gdx.graphics.getWidth()/ 2f, Gdx.graphics.getHeight()/1.5f - ((button[0].getHeight() * 1.5f) * i)/2);
            }
             stage.addActor(button[i]);
        }
        MySql.disconnect();

    }

    @Override
    protected void handleInput() {
        if(button[3].isPressed())
            gsm.push(new MainMenu(gsm, false));
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(checkResize()){
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.draw();
    }

    @Override
    public void dispose() {

        font.dispose();
        skin.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.draw();
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
    }

    public String levelScore(int i){
        String rs = MySql.getQuerry("select max(score) , id from Score where level = " + i, i);
        return rs;
    }
}
