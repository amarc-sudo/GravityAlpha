package com.runespace.game.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.runespace.game.LaunchGame;
import com.runespace.game.MapsTiled.MapsTiledLevel;
import com.runespace.game.entities.Player;
import com.runespace.game.handlers.CustomContactListener;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.item.Item;
import com.runespace.game.stage.GameOver;
import com.runespace.game.stage.Hud;
import com.runespace.game.stage.Win;
import com.runespace.game.utils.Constants;



public abstract class LevelAbstract extends GameState implements ApplicationListener {

	
	//body
	
	//score
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private FreeTypeFontGenerator generator;
	public int score;
	
	//game over elements
	protected Boolean gameOverBool;
	protected GameOver screenGameOver;

	//win elements
	protected Boolean win = false;
    protected Win screenWin;
	//box2d elements
    protected Body body;
	protected World world;
	protected BodyDef bdef;
	protected FixtureDef fdef;
	protected OrthographicCamera box2dCam;
	protected Box2DDebugRenderer debug;
	Hud hud;
	//customcontactlistener
	protected CustomContactListener customContactListener;

	
	//player elements
	protected Player player;
	protected Body boxPlayer;
	
	//boolean for gravity and backwalk
	protected Boolean backward = false;
	protected Boolean gravityBool = false;
	
	//map
	MapsTiledLevel map;

	//timer for gravity change
	protected int time = 0;
	protected int jump = 0;
	//Music
	Music music;
	protected int levelInt;
	protected String levelName;
	//item
	private Array<Item> items;

	//level int
	protected int level;

	long startTime;
	long ellaspeTime;
	BitmapFont font;
	public LevelAbstract(GameStateManager gsm, Vector2 gravity) {
		super(gsm);
		//setup Box2d elements
		Box2D.init();
		world = new World(gravity, true);
		bdef = new BodyDef();
		fdef = new FixtureDef();
		box2dCam = new OrthographicCamera();
		box2dCam.setToOrtho(false, Constants.VIEWPORT_WIDTH/Constants.PIXEL_METER,Constants.VIEWPORT_HEIGHT/Constants.PIXEL_METER);
		debug = new Box2DDebugRenderer();
		screenGameOver = new GameOver();
		gameOverBool = false;
		screenWin = new Win();
		//set contactlistener
		customContactListener = new CustomContactListener();
		world.setContactListener(customContactListener);
		debug.setDrawBodies(false);
		//setup camera
		cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		//set hud
		hud = new Hud(new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, cam), LaunchGame.batch);

	}
	
	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		if(!gameOverBool)	
			player.movePlayer(customContactListener,gravityBool);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && (this.customContactListener.isOnGround() || this.customContactListener.isOnHead())){
			jump++;
		}
		if(Gdx.input.isKeyJustPressed((Input.Keys.A)));
			debug.setDrawBodies(false);
	}

	public void update(float dt) {
		//update World
		if(!gameOverBool && !win) {
			scoreUpdate();
			world.step(1f / 60f, 6, 2);
			hud.update(score, jump, dt);
		}

	}
	
	public void render(SpriteBatch sb) {
		

		map.getTmr().setView(cam);
		map.getTmr().render();
		sb.setProjectionMatrix(cam.combined);
	}
	
	@Override
	public void dispose() {
		map.dispose();
		hud.dispose();
		screenWin.dispose();
		screenGameOver.dispose();
	}

	@Override
	public void resize(int width, int height) {
		hud.resize(width, height);
		screenGameOver.resize(width, height);
		screenWin.resize(width, height);

	}
	
	public void createPlayer(int x, int y) {
		//Body Def
		PolygonShape pshape = new PolygonShape();
		/////////////BOX/////////////

		//Body Def
		bdef.position.set(x/Constants.PIXEL_METER, y/Constants.PIXEL_METER);
		bdef.type = BodyDef.BodyType.DynamicBody;

		//Create Body
		boxPlayer = world.createBody(bdef);


		//Polygon shape
		pshape.setAsBox(Constants.WIDTH_PLAYER/4/Constants.PIXEL_METER, Constants.HEIGHT_PLAYER/3/Constants.PIXEL_METER);

		fdef.shape = pshape;
		fdef.filter.categoryBits = Constants.BOX_BIT;

		fdef.filter.maskBits = Constants.PLARTFORM_BIT | Constants.SPHERE_BIT | Constants.COINT_BIT | Constants.TORCH_BIT;

		//create Fixture
		boxPlayer.createFixture(fdef).setUserData("box");

		player = new Player(boxPlayer);
		boxPlayer.setUserData(player);

		pshape.setAsBox(Constants.WIDTH_PLAYER/5/Constants.PIXEL_METER, 10/Constants.PIXEL_METER,
				new Vector2(0, -Constants.WIDTH_PLAYER/3/Constants.PIXEL_METER-5/Constants.PIXEL_METER), 0);
		fdef.shape = pshape;
		fdef.isSensor = true;
		boxPlayer.createFixture(fdef).setUserData("foot");
		fdef.isSensor = false;
		//////Create head sensor///////
		pshape.setAsBox(Constants.WIDTH_PLAYER/5/Constants.PIXEL_METER, 10/Constants.PIXEL_METER,
				new Vector2(0, Constants.WIDTH_PLAYER/3/Constants.PIXEL_METER+1/Constants.PIXEL_METER), 0);
		fdef.shape = pshape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = Constants.HEAD_BIT;
		fdef.filter.maskBits = Constants.PLARTFORM_BIT ;
		boxPlayer.createFixture(fdef).setUserData(this);

		fdef.isSensor = false;
		///////END BOX/////////////////

		 
	}
	
	
	public void moveCam(int speedCam) {
		
		this.box2dCam.position.x = this.boxPlayer.getPosition().x;
		this.box2dCam.position.y = boxPlayer.getPosition().y;
		this.box2dCam.update();
		this.cam.position.x = boxPlayer.getPosition().x*Constants.PIXEL_METER;
		this.cam.position.y = boxPlayer.getPosition().y*Constants.PIXEL_METER;
		this.cam.update();
		
	}

	public void scoreUpdate() {
		if((!gravityBool && !customContactListener.isOnGround()) || (gravityBool && !this.customContactListener.isOnHead()))
			score+=1;
		if((!gravityBool && customContactListener.isOnGround()) || (gravityBool && this.customContactListener.isOnHead()))
			score-=1;
		if(customContactListener.isCoinG()){
		    score+=100;
        }
	}
	
	public void checkGameOver() {
		if(customContactListener.isDead()) {
			gameOverBool = true;
		}
	}

	public void checkWin() {
	    if(customContactListener.isWin()){
	        win = true;
        }
    }
	
	public void gameOver() {
		gsm.set(new MainMenu(gsm, true));
	}
	public void gravityChange() {
		if(customContactListener.isSensorG() && !gravityBool && time >= 50) {
			world.setGravity(new Vector2(0, -Constants.GRAVITY));
			gravityBool = true;
			time = 0;
		}
		time++;
		if(customContactListener.isSensorG() && gravityBool && time >= 50) {
			world.setGravity(new Vector2(0, Constants.GRAVITY));
			gravityBool = false;
			time = 0;
		}
	}
	
	public void createFont() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gamefont.TTF"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);
	}

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public boolean waitStart(){
		startTime = System.currentTimeMillis();
		long elapsedTime = System.currentTimeMillis() - startTime;
		ellaspeTime = elapsedTime / 1000;
		if(ellaspeTime < 9){
			return true;
		}
		return false;
	}
	public void renderStart(){

	}
}
