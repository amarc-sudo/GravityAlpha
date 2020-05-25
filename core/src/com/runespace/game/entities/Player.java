package com.runespace.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.runespace.game.LaunchGame;
import com.runespace.game.handlers.CustomContactListener;
import com.runespace.game.utils.Constants;

public class Player extends B2DSprites {

    protected boolean backward;
    protected Texture Handle, jump, hurt;
    protected Boolean gravity;
    protected int rotation;
    public Player(Body body) {
        super(body);
        backward = false;
        Handle = LaunchGame.assetManager.get("Sprites/p1_stand.png", Texture.class);
        jump = LaunchGame.assetManager.get("Sprites/p1_jump.png", Texture.class);
        hurt = LaunchGame.assetManager.get("Sprites/p1_hurt.png", Texture.class);
        Texture texture = LaunchGame.assetManager.get("Sprites/player.png", Texture.class);
        TextureRegion[] sprites = TextureRegion.split(texture, Constants.WIDTH_PLAYER,Constants.HEIGHT_PLAYER)[0];
        
        setAnimation(sprites, 1/16f);
        resize(sprites[0].getRegionWidth()/1.5f,sprites[0].getRegionHeight()/1.5f);
        gravity = true;
        rotation=0;
    }

    public void render(SpriteBatch sb, Boolean reverseBool){

    	if(reverseBool && gravity) {
    		reverse();

    		gravity = false;
    	}
    	if(!reverseBool && !gravity) {
    		reverse();

    		gravity = true;
    	}
    		
        if(this.body.getLinearVelocity().x != 0 && this.body.getLinearVelocity().y == 0) {
            sb.draw(animation.getFrame(), body.getPosition().x * Constants.PIXEL_METER - width / 2, body.getPosition().y * Constants.PIXEL_METER - height / 2, width, height);
            animation.update(Gdx.graphics.getDeltaTime());
        }
        else if(this.body.getLinearVelocity().y > 0) {
            sb.draw(jump, body.getPosition().x * Constants.PIXEL_METER - width / 2, body.getPosition().y * Constants.PIXEL_METER - height / 2, width, height);
        }
        else if(this.body.getLinearVelocity().y < 0) {
            sb.draw(hurt, body.getPosition().x * Constants.PIXEL_METER - width / 2, body.getPosition().y * Constants.PIXEL_METER - height / 2, width, height);
        }
        else if(this.body.getLinearVelocity().x == 0){
            sb.draw(Handle, body.getPosition().x * Constants.PIXEL_METER - width / 2, body.getPosition().y * Constants.PIXEL_METER - height / 2, width, height);
        }

    }
    public void update(float dt){
        animation.update(dt);
    }

	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		this.height = -this.height ;
	}
    public void movePlayer(CustomContactListener customContactListener, boolean gravityBool) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !gravityBool ) {
            if(customContactListener.isOnGround())
                body.applyForceToCenter(0, 400, true);
            	
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(customContactListener.isOnHead())
                body.applyForceToCenter(0, -400, true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && body.getLinearVelocity().x > -3.00f) {
            body.applyLinearImpulse( new Vector2(-0.15f,0), body.getWorldCenter(), true);

            if(!backward) {
                reverseW();
                backward = true;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && body.getLinearVelocity().x < 3.00f) {
            body.applyLinearImpulse( new Vector2(0.15f,0), body.getWorldCenter(), true);
            if(backward) {
                reverseW();
                backward = false;
            }
        }
    }

}
