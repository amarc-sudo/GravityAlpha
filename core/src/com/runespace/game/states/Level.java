package com.runespace.game.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.runespace.game.LaunchGame;
import com.runespace.game.entities.Player;
import com.runespace.game.handlers.CustomContactListener;
import com.runespace.game.handlers.GameStateManager;
import com.runespace.game.item.Coin;
import com.runespace.game.item.Item;
import com.runespace.game.item.Piece;
import com.runespace.game.stage.GameOver;
import com.runespace.game.stage.Hud;
import com.runespace.game.utils.Constants;


public abstract class Level extends GameState implements ApplicationListener {

	
	//body
	protected Body body;
	//score
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private FreeTypeFontGenerator generator;
	public int score;
	
	//game over elements
	protected Boolean gameOverBool;
	protected GameOver screenGameOver;

	//win elements
    protected Boolean win = false;

	//box2d elements
	protected World world;
	protected BodyDef bdef;
	protected FixtureDef fdef;
	protected OrthographicCamera box2dCam;
	protected Box2DDebugRenderer debug;
	Hud hud;
	//customcontactlistener
	protected CustomContactListener customContactListener;


	//tiled elements
	protected TiledMap tiledMap;
	protected OrthogonalTiledMapRenderer tmr;
	protected float tileSize;
	protected TiledMapTileLayer layer;
	
	//player elements
	protected Player player;
	protected Body boxPlayer;
	
	//boolean for gravity and backwalk
	protected Boolean backward = false;
	protected Boolean gravityBool = false;
	
	protected Texture background;
	

	//timer for gravity change
	protected int time = 0;
	protected int jump = 0;
	//Music
	Music music;
	
	//item
	private Array<Item> items;
	
	BitmapFont font;
	public Level(GameStateManager gsm, Vector2 gravity) {
		super(gsm);

		//
		tiledMap = LaunchGame.assetManager.get("maps/Main.tmx", TiledMap.class);
		//setup background;
		//background = LaunchGame.assetManager.get("background/aled.png", Texture.class);

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


		//set contactlistener
		customContactListener = new CustomContactListener();
		world.setContactListener(customContactListener);
		
		//set font
		
		debug.setDrawBodies(false);
		//setup camera
		cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		//set hud
		hud = new Hud(new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, cam), LaunchGame.batch);
		hud.update(score, jump);
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
	}

	public void update(float dt) {
		//update World
		if(!gameOverBool || !win) {
			scoreUpdate();
			world.step(1f / 60f, 6, 2);
		}

	}
	
	public void render(SpriteBatch sb) {
		
		hud.update(score, jump);
		tmr.setView(cam);
		tmr.render();
		sb.setProjectionMatrix(cam.combined);
	}
	
	@Override
	public void dispose() {


		tmr.dispose();
		tiledMap.dispose();

	}

	@Override
	public void resize(int width, int height) {

	}

	
	public void createTileCondition(short BIT, String type, Boolean fixture){
		
		layer = (TiledMapTileLayer)this.tiledMap.getLayers().get(type);
		
		tileSize = layer.getTileHeight();
		for(int row = 0 ; row < layer.getHeight() ; row++) {
			for(int col = 0 ; col < layer.getWidth() ;col++) {
				TiledMapTileLayer.Cell cell = layer.getCell(col, row);
						
				if(cell == null)continue;
				if(cell.getTile()==null)continue;
				
				bdef.type = BodyDef.BodyType.StaticBody;
				bdef.position.set((col+0.5f)*tileSize/Constants.PIXEL_METER,(row+0.5f)*tileSize/Constants.PIXEL_METER);
				ChainShape chainShape = new ChainShape();
				Vector2[] vectors= new Vector2[5];
				if(type.equals("deadInversed")) {
					vectors[0] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 12 / Constants.PIXEL_METER);
					vectors[1] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[2] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[3] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, -tileSize / 12 / Constants.PIXEL_METER);
					vectors[4] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 12 / Constants.PIXEL_METER);
				}
				else if(type.equals("dead")) {
					vectors[0] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[1] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, tileSize / 12 / Constants.PIXEL_METER);
					vectors[2] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, tileSize / 12 / Constants.PIXEL_METER);
					vectors[3] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[4] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
				}
				else{
					vectors[0] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[1] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[2] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, tileSize / 2 / Constants.PIXEL_METER);
					vectors[3] = new Vector2(tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
					vectors[4] = new Vector2(-tileSize / 2 / Constants.PIXEL_METER, -tileSize / 2 / Constants.PIXEL_METER);
				}
				chainShape.createChain(vectors);
				fdef.shape = chainShape;
				fdef.filter.categoryBits = Constants.PLARTFORM_BIT;
				fdef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
				if(fixture)
					fdef.isSensor = true;
				world.createBody(bdef).createFixture(fdef).setUserData(type);
				if(fixture)
					fdef.isSensor = false;
			}
		}
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
		fdef.filter.maskBits = Constants.PLARTFORM_BIT | Constants.SPHERE_BIT | Constants.COINT_BIT;

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
		//Constants.WIDTH_PLAYER/5/Constants.PIXEL_METER, 10/Constants.PIXEL_METER
		//Constants.WIDTH_PLAYER/5/Constants.PIXEL_METER, 10/Constants.PIXEL_METER
		//////Create head sensor///////
		pshape.setAsBox(Constants.WIDTH_PLAYER/5/Constants.PIXEL_METER, 10/Constants.PIXEL_METER,
				new Vector2(0, Constants.WIDTH_PLAYER/3/Constants.PIXEL_METER+1/Constants.PIXEL_METER), 0);
		fdef.shape = pshape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = Constants.HEAD_BIT;
		fdef.filter.maskBits = Constants.PLARTFORM_BIT;
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
        LaunchGame.assetManager.unload("maps/sans.tmx");
        LaunchGame.assetManager.load("maps/sans.tmx", TiledMap.class);
		gsm.set(new MainMenue2(gsm, true));
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
	
	

	public void createGround(){

		tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		for(MapObject object : tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
			fixtureDef.shape = shape;
			fixtureDef.filter.categoryBits = Constants.PLARTFORM_BIT;
			fixtureDef.filter.maskBits = Constants.BOX_BIT
                    | Constants.SPHERE_BIT
                    | Constants.HEAD_BIT;
			body.createFixture(fixtureDef).setUserData("ground");
		}
	}

	public void createCoin(){
		for(MapObject object : tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Coin(world, object, tiledMap);
		}
	}
	public void createDead(){

		tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		for(MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
			fixtureDef.shape = shape;
			fixtureDef.filter.categoryBits = Constants.PLARTFORM_BIT;
			fixtureDef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef).setUserData("dead");
			fixtureDef.isSensor = false;
		}
	}
	public void createSensorG(){

		tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		for(MapObject object : tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
			fixtureDef.shape = shape;
			fixtureDef.filter.categoryBits = Constants.PLARTFORM_BIT;
			fixtureDef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef).setUserData("sensorG");
			fixtureDef.isSensor = false;
		}
	}
    public void createWin(){

        tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        for(MapObject object : tiledMap.getLayers().get(Constants.FILTER_WIN).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Constants.PLARTFORM_BIT;
            fixtureDef.filter.maskBits = Constants.BOX_BIT | Constants.SPHERE_BIT;
            fixtureDef.isSensor = true;
            body.createFixture(fixtureDef).setUserData("win");
            fixtureDef.isSensor = false;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}
