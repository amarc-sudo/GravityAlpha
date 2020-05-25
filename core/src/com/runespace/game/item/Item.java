package com.runespace.game.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.runespace.game.utils.Constants;

public abstract class Item extends Sprite {
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(World world, float y, float x){
        this.world = world;
        setPosition(x,y);
        setBounds(getX(), getY(), 64/ Constants.PIXEL_METER, 64/ Constants.PIXEL_METER);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineItem();
    public abstract void use();

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(SpriteBatch sb){
        if(!destroyed){
            super.draw(sb);
        }
    }

    public void destroy(){
        toDestroy = true;
    }
}
