package com.mygdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;

public abstract class PassTest {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected MapObject bounds;
    protected Body body;

    protected  boolean isChange;

    protected Fixture fixture;

    public PassTest(PlayScreen screen, MapObject object) {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = object;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        Body body;
        Shape shape = null;
        if (object instanceof RectangleMapObject) {
            shape = getRectangle((RectangleMapObject) object);
        } else if (object instanceof PolygonMapObject) {
            shape = getPolygon((PolygonMapObject) object);
        } else {

        }
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        //fdef.filter.categoryBits = Jannabi.PASS_BIT;
        //fdef.filter.categoryBits = Jannabi.OTHERLAYER_BIT;
        //fdef.filter.categoryBits = Jannabi.PASS_BIT;
        //fdef.filter.maskBits = Jannabi.DEFAULT_BIT | Jannabi.JANNABI_BIT;
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
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

    public abstract void change();
    public abstract void notChange();
    public  void setCategoryFilter(short filterBit){
            Filter filter = new Filter();
            filter.categoryBits = filterBit;
            fixture.setFilterData(filter);

    }


}
