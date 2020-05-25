package com.runespace.game.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
    private TextureRegion[] frames;
    private float time, delay;
    private int currentFrame, timesPlayed;

    public Animation(TextureRegion [] frames, float delay) {
        setFrames(frames, delay);
    }
    public Animation(TextureRegion[] frames){
        this(frames, 1/12f);
    }
    public void setFrames(TextureRegion[] frames, float delay){
        this.frames = frames;
        this.delay = delay;
        time = 0;
        currentFrame = 0;
        timesPlayed = 0;
    }

    public void update(float dt) {
        time += dt;

        while(time >= delay){
            step();
        }
    }

    public void step(){
        time -= delay;
        currentFrame++;
        if(currentFrame == frames.length){
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame(){
        return frames[currentFrame];
    }

    public int getTimesPlayed(){
        return  timesPlayed;
    }

    public void resetTimesPlayed(){
        timesPlayed = 0;
    }

}
