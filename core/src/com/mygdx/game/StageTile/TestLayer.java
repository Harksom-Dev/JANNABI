package com.mygdx.game.StageTile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.tools.PassTest;

public class TestLayer extends PassTest {
    public static boolean isChange = false;
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected MapObject bounds;
    protected Body body;


    //protected  Fixture fixture;

    public TestLayer(PlayScreen screen, MapObject object) {
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(Jannabi.PASS_BIT);


    }

    public static void test(){
        Gdx.app.log("test","pass");
    }

    public static void test2(){
        Gdx.app.log("test","notpass");
    }


    @Override
    public  void change() {
        Gdx.app.log("floor2","pass");
        setCategoryFilter(Jannabi.PASS_BIT);

    }

    @Override
    public void notChange() {
        Gdx.app.log("floor2","not pass");
        setCategoryFilter(Jannabi.OTHERLAYER_BIT);
    }

/*private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
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

    }*/



}
