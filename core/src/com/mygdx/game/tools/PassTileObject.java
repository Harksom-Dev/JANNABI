package com.mygdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Sprites.Player;

import java.awt.*;

//this class is the hierarchy of all alternate layer in our map
public abstract class PassTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    //create fixture to listener
    protected Fixture fixture;


    public PassTileObject(World world, TiledMap map,int layer){
        this.world = world;
        this.map = map;
        getObjectLayer(world,map,layer);
    }

    protected  void getObjectLayer(World world, TiledMap map, int layer) {
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
            //fdef.filter.categoryBits = Jannabi.PASS_BIT;
            //fdef.filter.maskBits = Jannabi.DEFAULT_BIT | Jannabi.JANNABI_BIT;
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("pass");
            fixture = body.createFixture(fdef);
        }
    }

    private  PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        com.badlogic.gdx.math.Rectangle rectangle = rectangleObject.getRectangle();
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

    //abstract method when get collision
    public abstract void gotoOtherLayer();
    public  void setCategoryFilter(short filterBit){
            Filter filter = new Filter();
            filter.categoryBits = filterBit;
            fixture.setFilterData(filter);


    }

}
