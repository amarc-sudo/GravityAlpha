package com.runespace.game.handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.runespace.game.utils.Constants;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class LightManager {
    private RayHandler rayHandler;
    private PointLight light;
    private HashMap<Vector2, PointLight> lightHashMap;
    private ArrayList<PointLight> lights;
    private OrthographicCamera camera;
    public  LightManager(World world, Color ambientLight, OrthographicCamera camera){
        this.camera = camera;
        lightHashMap = new HashMap<Vector2, PointLight>();
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(1);
        RayHandler.useDiffuseLight(true);
        rayHandler.setAmbientLight(ambientLight);
        rayHandler.setShadows(true);
    }

    public void InitLight(Vector2 lightEmplacement, Color colorOfLight, int ray, int distance, Boolean collisionBool){
        System.out.println(lightEmplacement.x);
        PointLight pl = new PointLight(rayHandler, ray,colorOfLight, distance, (float) lightEmplacement.x/100, (float) lightEmplacement.y/100);
        pl.setActive(true);
        pl.setStaticLight(false);
        pl.setXray(false);
        if(collisionBool) {
            Filter filter = new Filter();
            filter.categoryBits = Constants.TORCH_BIT;
            filter.maskBits = Constants.BOX_BIT | Constants.PLARTFORM_BIT;
        }
        else
            pl.setXray(true);
        lightHashMap.put(lightEmplacement,pl);
    }

    public void updateAndRender(){
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    public void dispose(){
        rayHandler.dispose();
    }
}
