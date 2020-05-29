package com.runespace.game.MapsTiled;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.LightManager;
import com.runespace.game.item.Coin;
import com.runespace.game.utils.Constants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aurel
 */
public class MapsTiledLevel implements Cloneable{
    
    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer tmr;
    protected float tileSize;
    protected TiledMapTileLayer layer;
    protected World world;
    protected OrthographicCamera b2DCam;
    protected LightManager lightManager;

    @Override
    public TiledMap clone() throws CloneNotSupportedException {
        return (TiledMap)super.clone();
    }

    public MapsTiledLevel(World world, String mapPath, OrthographicCamera camera){
        this.b2DCam = camera;
        tiledMap = (TiledMap) (LaunchGame.assetManager.get(mapPath, TiledMap.class));
        tmr = new OrthogonalTiledMapRenderer(tiledMap);
        this.world = world;
        lightManager = new LightManager(world, new Color(0.2f, 0.2f , 0.2f , 0.2f), b2DCam);
        setLight();
        setTorch();
    }
    
    public void createStaticObject(int layer,  Filter filterCategory,String nameObject, boolean sensor){
        tmr = new OrthogonalTiledMapRenderer(this.tiledMap);
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        BodyDef bdef = new BodyDef();
        for(MapObject object : tiledMap.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()/Constants.PIXEL_METER + rect.getWidth()/2/Constants.PIXEL_METER, rect.getY()/Constants.PIXEL_METER + rect.getHeight()/2/Constants.PIXEL_METER);

            Body body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/Constants.PIXEL_METER , rect.getHeight()/2/Constants.PIXEL_METER);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = filterCategory.categoryBits;
            fixtureDef.filter.maskBits = filterCategory.maskBits;
            fixtureDef.isSensor = false;
            if(sensor)
                fixtureDef.isSensor = true;
            body.createFixture(fixtureDef).setUserData(nameObject);
            if(sensor)
                fixtureDef.isSensor = false;
        }     
    }

    public void createCoin(){
	    for(MapObject object : tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(world, object, tiledMap);
	    }
    }
    
    public void dispose(){
        tmr.dispose();
        tiledMap.dispose();
        lightManager.dispose();
    }
    public void setLight(){
        for(MapObject object : tiledMap.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            lightManager.InitLight(new Vector2(rect.getX(), rect.getY()),new Color(0.4f, 0.4f, 0.4f, 1f), 90, 3, false);
        }
    }

    public void setTorch(){
        for(MapObject object : tiledMap.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            lightManager.InitLight(new Vector2(rect.getX(), rect.getY()),new Color(0.7f, 0.3f, 0.1f, 1f), 90, 6, true);
        }
    }
    public TiledMap getTiledMap(){
        return tiledMap;
    }
    public LightManager getLightManager(){
        return lightManager;
    }
    public OrthogonalTiledMapRenderer getTmr(){
        return tmr;
    }
    public void renderRayHandler(){
        lightManager.updateAndRender();
    }


}
