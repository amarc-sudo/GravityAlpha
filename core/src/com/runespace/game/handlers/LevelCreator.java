package com.runespace.game.handlers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.runespace.game.LaunchGame;
import com.runespace.game.MapsTiled.MapsTiledLevel;
import com.runespace.game.entities.Player;
import com.runespace.game.item.Item;
import com.runespace.game.stage.GameOver;
import com.runespace.game.stage.Hud;
import com.runespace.game.stage.Win;
import com.runespace.game.utils.Constants;

public class LevelCreator {
    /**
     * Font parameter generator
     */
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    /**
     * Font generator
     */
    private FreeTypeFontGenerator generator;
    /**
     * Score du joueur, est calculé a la fin
     */
    public int score;
    //game over elements
    /**
     * Boolean qui dit si le joueur a perdu ou pas
     */
    protected Boolean gameOverBool;
    /**
     * HUD pour quand le joueur a perdu
     */
    protected GameOver screenGameOver;
    /**
     * Boolean qui dit si le joueur a gagner
     */
    protected Boolean win = false;
    /**
     * HUD pour quand le joueur a gagner
     */
    protected Win screenWin;
    /**
     * Body de base pour les élements de World
     * @see World
     */
    protected Body body;
    /**
     * World de BOX2D
     */
    protected World world;
    /**
     * Définition des comportement du Body
     */
    protected BodyDef bdef;
    /**
     * Définition des comportement des fixture
     */
    protected FixtureDef fdef;
    /**
     * Cam qui gere le monde
     */
    protected OrthographicCamera box2dCam;
    /**
     * Renderer qui permet d'afficher les élements de ce monde
     */
    protected Box2DDebugRenderer debug;
    /**
     * HUD du score et de la pause et des jumps et du time
     */
    protected Hud hud;
    /**
     * Contact listener pour gerer les colisions
     */
    protected CustomContactListener customContactListener;
    /**
     * Joueur
     */
    protected Player player;
    /**
     * Body du joueur
     */
    protected Body boxPlayer;
    /**
     * boolean pour indiquer si le joueur est en marche arrière ou pas (bientot a mettre dans player)
     */
    protected Boolean backward = false;
    /**
     * boolean pour indiquer si le joueur est a l'envers ou pas (bientot a mettre dans player)
     */
    protected Boolean gravityBool = false;
    /**
     * boolean pour indiquer si le joueur marche sur les murs ou pas (bientot a mettre dans player)
     */
    protected Boolean xBool = false;
    /**
     * Maps (tiled map)
     */
    protected MapsTiledLevel map;
    /**
     * Int du time depuis le début du niveau
     */
    protected int time = 0;
    /**
     * Nombre de saut effectuer
     */
    protected int jump = 0;
    /**
     * Musique du niveau, Obselete pour l'instant (futur maj)
     */
    protected Music music;
    /**
     * Int qui represente le numéro du niveau pour la base de donées
     */
    protected int levelInt;
    /**
     * String qui represente le nom du niveau pour la BDD
     */
    protected String levelName;
    /**
     * Array d'item, obselete pour l'instant
     */
    private Array<Item> items;
    /**
     * Font pour différent police (verifié sont utilité)
     */
    private BitmapFont font;
    /**
     * camera de base, recuperer depuis le gameState
     * @see com.runespace.game.states.GameState
     */
    OrthographicCamera cam;
    /**
    * Filter des body
    */
    protected Filter filter;

    /**
     * Constructeur de base de level (a expliquer plus tard)
     */
    public LevelCreator(OrthographicCamera cam){
        this.cam = cam;
        Box2D.init();
        world = new World(Constants.GRAVITY_WORLD, true);
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
        debug.setDrawBodies(true);
        //setup camera
        cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        //set hud
        hud = new Hud(new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, cam), LaunchGame.batch);
    }

    public void create(int levelInt, String levelName){
        this.levelInt = levelInt;
        this.levelName = levelName;
        while(!LaunchGame.assetManager.update());
        //create map
        map = new MapsTiledLevel(world, "maps/" + levelName + ".tmx", box2dCam);
        filter = new Filter();
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
        map.createStaticObject(Constants.FILTER_XSENSOR, filter, "xSensor", true);
        map.createCoin();
        map.createStaticObject(Constants.FILTER_WIN, filter, "win", true);
        Rectangle rect = null;
        for(MapObject object : map.getTiledMap().getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            rect = ((RectangleMapObject) object).getRectangle();
        }
        player = new Player(boxPlayer, customContactListener);
        player.createPlayer((int) rect.getX(), (int) rect.getY(), world, bdef, fdef);
        boxPlayer = player.getBody();
    }

}
