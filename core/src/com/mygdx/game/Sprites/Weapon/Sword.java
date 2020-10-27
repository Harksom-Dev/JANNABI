package com.mygdx.game.Sprites.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Sword {

    private Body b2body;
    private BodyDef bdef;
    private boolean fireRight;
    private World world;
    private PlayScreen screen;
    private float stateTime;
    private int Dmg;
    private boolean setToDestroy;
    private boolean destroyed;

    public Sword(PlayScreen screen, boolean fireRight, int Dmg) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.fireRight = fireRight;
        this.Dmg = Dmg;
        stateTime = 0;
        destroyed = false;
        //definedBullet(0,0,true);
    }

    public void definedBullet(float x,float y,boolean fireRight,float velocityX,float velocityY) {
        bdef = new BodyDef();
        bdef.position.set( fireRight ? x+10 / Jannabi.PPM + (velocityX * 10) / Jannabi.PPM : x-10 / Jannabi.PPM + (velocityX * 10) / Jannabi.PPM, y+4 / Jannabi.PPM + velocityY / Jannabi.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6/Jannabi.PPM,10/Jannabi.PPM);
        fdef.filter.categoryBits =  Jannabi.SWORDHITBOX_BIT;
        fdef.filter.maskBits =  Jannabi.ENEMY_BIT ;


        fdef.shape = shape;
        //fdef.restitution = 0;
        //fdef.friction = 1;
        //fdef.density = 0.02f;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
        //b2body.setBullet(true);


    }

    public void update(float dt,float x,float y) {

        stateTime += dt;

        if(setToDestroy ) {
            destroy();
            //stateTime = 0;
        }

    }

    public void destroy(){
        Gdx.app.log("destroy","must destroy");
        world.destroyBody(b2body);
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }


    public int getDmg() {
        return Dmg;
    }
}
