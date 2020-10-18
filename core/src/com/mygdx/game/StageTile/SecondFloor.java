package com.mygdx.game.StageTile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Jannabi;
import com.mygdx.game.tools.PassTileObject;

import java.awt.*;
//this class create box2d object but we it can be walkThrough
public class SecondFloor extends PassTileObject {


    public SecondFloor(World world, TiledMap map, int layer){
        super(world,map,layer);
        //super.getObjectLayer(world,map,layer);
        fixture.setUserData(this);
        setCategoryFilter(Jannabi.PASS_BIT);


    }
    //this method call when we want to jump through tile layer above
    @Override
    public void gotoOtherLayer() {
        Gdx.app.log("2nd","No Collision");
        setCategoryFilter(Jannabi.OTHERLAYER_BIT);

    }


}
