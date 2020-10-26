package com.mygdx.game.Sprites.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;

public class Sword {

    private Body b2body;
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
        definedBullet();
    }

    public void definedBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ?  + 12 / Jannabi.PPM :  - 12 / Jannabi.PPM,  +2 / Jannabi.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5/Jannabi.PPM,1/Jannabi.PPM);
        fdef.filter.categoryBits =  Jannabi.PISTOL_BULLET_BIT;
        fdef.filter.maskBits =  Jannabi.ENEMY_BIT ;


        fdef.shape = shape;
        //fdef.restitution = 0;
        //fdef.friction = 1;
        fdef.density = 0.02f;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
        //b2body.setBullet(true);


    }

    public void update(float dt) {
        if((stateTime > 2f || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }

    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
