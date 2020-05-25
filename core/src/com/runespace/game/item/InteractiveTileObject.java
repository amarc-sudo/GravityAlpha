package com.runespace.game.item;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.runespace.game.entities.Player;
import com.runespace.game.utils.Constants;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(World world, MapObject object, TiledMap map){
        this.object = object;
        this.world = world;
        this.map = map;
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        fdef.isSensor = true;
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PIXEL_METER, (bounds.getY() + bounds.getHeight() / 2) / Constants.PIXEL_METER);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Constants.PIXEL_METER, bounds.getHeight() / 2 / Constants.PIXEL_METER);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(Constants.FILTER_TILE_COIN);
        return layer.getCell((int)(body.getPosition().x * Constants.PIXEL_METER/ 64),
                (int)(body.getPosition().y * Constants.PIXEL_METER / 64));
    }

}

