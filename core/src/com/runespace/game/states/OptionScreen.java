package com.runespace.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.runespace.game.handlers.CustomInputHandling;
import com.runespace.game.handlers.CustomInputProcessor;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.utils.Constants;

import java.util.ArrayList;

public class OptionScreen extends GameState {
    private final FreeTypeFontGenerator generator;
    private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private final BitmapFont font;
    TextButton[][] buttonTab;
    TextButton buttonMenu;
    TextButton.TextButtonStyle textButtonStyle;
    Label.LabelStyle labelStyle;
    Table table;
    Table tableButtons;
    Stage stage;

    Label nameOption[];
    TextButton changeOption[];
    Table tableOption;

    public OptionScreen(GameStateManager gsm) {
        super(gsm);
        //font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        Gdx.input.setInputProcessor(new CustomInputProcessor());
        //stage/layout
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        /*
        label initialisation
         */
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        nameOption = new Label[6];
        for(int i = 0 ; i < 6; i++){
            nameOption[i] = new Label(Constants.NAME_OPTION[i], labelStyle);
            table.row();
            table.add(nameOption[i]);
        }
        stage.addActor(table);
        //table.reset();
    }

    @Override
    protected void handleInput() {
        int i = CustomInputHandling.keyPressed();
        if( i != 0) {
            System.out.println("ok");
        }
    }

    @Override
    public void update(float dt) {

        handleInput();
        CustomInputHandling.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}
