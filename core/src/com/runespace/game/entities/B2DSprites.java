package com.runespace.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.runespace.game.handlers.Animation;
import com.runespace.game.utils.Constants;

public class B2DSprites {
    protected Body body;
    protected Animation animation, animationR;
    protected float width;
    protected float height;

    public B2DSprites(Body body){
        this.body = body;

    }

    public void setAnimation(TextureRegion[] sprites, float delay){
        animation = new Animation(sprites, delay);
        width = sprites[0].getRegionWidth();
        height = sprites[0].getRegionHeight();
    }
    public void setAnimationR(TextureRegion[] sprites, float delay){
        animationR = new Animation(sprites, delay);
        width = sprites[0].getRegionWidth();
        height = sprites[0].getRegionHeight();
    }
    public void update(float dt){
        animation.update(dt);
    }

    public void render(SpriteBatch sb) {
        if (this.body.getLinearVelocity().x != 0 && this.body.getLinearVelocity().y == 0) {
            sb.draw(animation.getFrame(), body.getPosition().x * Constants.PIXEL_METER - width / 2, body.getPosition().y * Constants.PIXEL_METER - height / 2, width, height);
        }
    }

    public Body getBody(){
        return body;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    public void resize(float width, float height){
        this.width = width;
        this.height = height;
    }
    public void reverse() {
    	this.height = -this.height;
    }
    public void reverseW() {
    	this.width = -this.width;
    }
}
