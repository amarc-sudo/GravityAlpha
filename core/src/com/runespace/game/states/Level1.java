package com.runespace.game.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.runespace.game.LaunchGame;
import com.runespace.game.MapsTiled.MapsTiledLevel;
import com.runespace.game.bdd.MySql;
import com.runespace.game.entities.Player;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.item.Coin;
import com.runespace.game.scoreboard.ScoreBoard;
import com.runespace.game.utils.Constants;

import java.sql.SQLException;

public class Level1 extends LevelAbstract implements ApplicationListener {

	ScoreBoard scoreArray;

	public Level1(GameStateManager gsm, Vector2 gravity, String levelName, int levelInt) {
		super(gsm, gravity);
		this.levelInt = levelInt;
		this.levelName = levelName;
		level = 3;
        while(!LaunchGame.assetManager.update());
        //create map
		map = new MapsTiledLevel(world, "maps/" + levelName + ".tmx", box2dCam);
		Filter filter = new Filter();
		filter.categoryBits = Constants.PLARTFORM_BIT;
		filter.maskBits = Constants.BOX_BIT
				| Constants.SPHERE_BIT
				| Constants.HEAD_BIT
				| Constants.TORCH_BIT;
		map.createStaticObject(1,filter, "ground", false );
		filter.maskBits = Constants.BOX_BIT
				| Constants.SPHERE_BIT
				| Constants.HEAD_BIT;

		map.createStaticObject(Constants.FILTER_DEAD, filter, "dead", true);
		map.createStaticObject(Constants.FILTER_SENSORG, filter, "sensorG", true);
		map.createCoin();
		map.createStaticObject(Constants.FILTER_WIN, filter, "win", true);
		//end map
		Rectangle rect = null;
		for(MapObject object : map.getTiledMap().getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
			rect = ((RectangleMapObject) object).getRectangle();
		}
		player = new Player(boxPlayer);
		player.createPlayer((int) rect.getX(), (int) rect.getY(), world, bdef, fdef);
		boxPlayer = player.getBody();
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		super.update(dt);
		moveCam(4);
		if(waitStart()) {
			if (!gameOverBool && !win)
				scoreUpdate();
			handleInput();
		}
		checkGameOver();
		checkWin();
		gravityChange();
		if(gameOverBool) {
			Gdx.input.setInputProcessor(screenGameOver.Stage());
			if(this.screenGameOver.menuPressed()) {
				this.gameOver();
			}
			if(this.screenGameOver.retryPressed())	{
				gsm.set(new Level1(gsm, Constants.GRAVITY_WORLD, levelName, levelInt));
			}
		}
		if(win) {
			Gdx.input.setInputProcessor(screenWin.Stage());
			if(this.screenWin.menuPressed()) {
				this.winScore();
			}
			if(this.screenWin.retryPressed())	{
				score = hud.score;
				try {
				    MySql.connect("gravity_guest", "GravityMarc");
					MySql.addScore(levelInt, score);
					MySql.disconnect();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				gsm.set(new Level1(gsm, Constants.GRAVITY_WORLD, levelName, levelInt));
			}
		}
		//rayHandler.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		super.render(sb);

		
		sb.begin();

		if(gravityBool)
			player.render(sb, gravityBool);
		if(!gravityBool)
			player.render(sb, gravityBool);
		sb.end();
		debug.render(world, box2dCam.combined);
		map.renderRayHandler();
		if(gameOverBool) {
			screenGameOver.Stage().draw();
		}
		if(win){
			screenWin.Stage().draw();
		}
		hud.stage.draw();


	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        Coin.resetTile();
		super.dispose();
		world.dispose();
	}
/*
	@Override
	public void createTiles(TiledMap tiledMap) {
		// TODO Auto-generated method stub
		super.createTiles(tiledMap);
	}

	@Override
	public void createTiledBodies(TiledMapTileLayer layer, short BITS) {
		// TODO Auto-generated method stub
		super.createTiledBodies(layer, BITS);
	}*/



	@Override
	public void createPlayer(int x, int y) {
		// TODO Auto-generated method stub
		super.createPlayer(x, y);
	}



	@Override
	public void moveCam(int speedCam) {
		// TODO Auto-generated method stub
		super.moveCam(speedCam);

	}

	@Override
	public void checkGameOver() {
		// TODO Auto-generated method stub
		super.checkGameOver();
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
        score = hud.score;
		super.gameOver();
	}

	public void winScore(){
		score = hud.score;
		try {

			MySql.connect("gravity_guest", "GravityMarc");
			MySql.addScore(levelInt, score);
			MySql.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        gsm.set(new MainMenu(gsm, true));
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		super.handleInput();
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	}
