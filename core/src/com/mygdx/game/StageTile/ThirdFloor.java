package com.mygdx.game.StageTile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Jannabi;
import com.mygdx.game.tools.PassTileObject;

public class ThirdFloor extends PassTileObject {
    public ThirdFloor(World world, TiledMap map, int layer) {
        super(world, map, layer);
        fixture.setUserData(this);
        setCategoryFilter(Jannabi.OTHERLAYER_BIT);
    }

    @Override
    public void gotoOtherLayer() {
        Gdx.app.log("3rd","No Collision");
        setCategoryFilter(Jannabi.OTHERLAYER_BIT);
    }
}
