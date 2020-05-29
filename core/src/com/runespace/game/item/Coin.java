package com.runespace.game.item;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;
import com.runespace.game.entities.Player;
import com.runespace.game.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Coin extends InteractiveTileObject {
    public static ArrayList<Coin> coinUsed = new ArrayList<Coin>();
    public static ArrayList<TiledMapTile> tileUsed = new ArrayList<TiledMapTile>();
    public Coin(World screen, MapObject object, TiledMap map){
        super(screen, object, map);
        fixture.setUserData(this);
        fixture.isSensor();
        setCategoryFilter(Constants.COINT_BIT);

    }

    @Override
    public void onHeadHit() {
        //if(!coinUsed.contains(this)){
            coinUsed.add(this);
        //}
        //if(!tileUsed.contains(getCell().getTile())){
            System.out.println(getCell().getTile());
            System.out.println((tileUsed));
            tileUsed.add(getCell().getTile());
        //}
        getCell().setTile(null);
        setCategoryFilter(Constants.COIN_USE_BIT);
    }
    public static void resetTile(){
        int i = 0;
        for(i = 0 ; i < coinUsed.size() ; i++){
            coinUsed.get(i).getCell().setTile(tileUsed.get(i));
        }
        tileUsed = new ArrayList<TiledMapTile>();
        coinUsed = new ArrayList<Coin>();
    }
}