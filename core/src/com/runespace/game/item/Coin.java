package com.runespace.game.item;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.runespace.game.entities.Player;
import com.runespace.game.utils.Constants;

public class Coin extends InteractiveTileObject {

    public Coin(World screen, MapObject object, TiledMap map){
        super(screen, object, map);
        fixture.setUserData(this);
        fixture.isSensor();
        setCategoryFilter(Constants.COINT_BIT);

    }

    @Override
    public void onHeadHit() {
        getCell().setTile(null);
        setCategoryFilter(Constants.COIN_USE_BIT);
    }
}