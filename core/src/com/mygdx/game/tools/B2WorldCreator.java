package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Slime;
import com.mygdx.game.StageTile.FourthFloor;
import com.mygdx.game.StageTile.SecondFloor;
import com.mygdx.game.StageTile.TestLayer;
import com.mygdx.game.StageTile.ThirdFloor;

import java.util.Iterator;

public class B2WorldCreator {
    //this class use for create line for detect collision between ground stage and character
    //create constructor
    Fixture fixture;
    //create array for store spawn point for enemy
    private Array<Slime> slimes;
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();


        getObjectLayer(world,map,7,Jannabi.DEFAULT_BIT);
        getObjectLayer(world,map,12,Jannabi.Edge_BIT);
        for (MapObject object : map.getLayers().get(9).getObjects()) {
            new TestLayer(screen,object);
        }

        //create all slimes
        slimes = new Array<Slime>();
        for(MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            slimes.add(new Slime(screen,rect.getX()/ Jannabi.PPM, rect.getY(),100));
        }



    }
    //getter for use in playScreen
    public Array<Slime> getSlimes() {
        return slimes;
    }

    public Iterator<Slime> getSlimeIterator()
    {
        return slimes.iterator();
    }
    private  void getObjectLayer(World world, TiledMap map, int layer,short bit) {
        BodyDef bdef = new BodyDef();
        //PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        for (MapObject object : map.getLayers().get(layer).getObjects()) {

            Shape shape;
            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject) object);
            } else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject) object);
            } else {
                continue;
            }
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            fdef.shape = shape;
            fdef.filter.categoryBits = bit;
            fixture = body.createFixture(fdef);
            fixture.setUserData(this);
        }
    }

        private  PolygonShape getRectangle(RectangleMapObject rectangleObject) {
            Rectangle rectangle = rectangleObject.getRectangle();
            PolygonShape polygon = new PolygonShape();
            Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / Jannabi.PPM,
                    (rectangle.y + rectangle.height * 0.5f ) / Jannabi.PPM);
            polygon.setAsBox(rectangle.width * 0.5f /Jannabi.PPM,
                    rectangle.height * 0.5f / Jannabi.PPM,
                    size,
                    0.0f);
            return polygon;
        }

    //getPolygon object from stackoverflow
    private  PolygonShape getPolygon(PolygonMapObject polygonObject){
        PolygonShape polygonShape = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length ; i++){
            worldVertices[i] = vertices[i] / Jannabi.PPM  ;
        }
        polygonShape.set(worldVertices);
        return polygonShape;

    }






}
