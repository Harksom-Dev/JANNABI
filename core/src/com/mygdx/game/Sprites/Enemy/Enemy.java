package com.mygdx.game.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Weapon.Gun;
import com.mygdx.game.Sprites.Weapon.Pistol;
import com.mygdx.game.Sprites.Weapon.Sword;

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected int Hp;
    public Vector2 velocity;
    protected float pauseJump;
    protected boolean drop;


    public Body b2body;
    public Enemy(PlayScreen screen,float x,float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(-0.5f,1.4f);

        //set to not active at first so we can wake when we near it
        b2body.setActive(false);

    }

    protected abstract void setTexture();
    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void getHit(Gun gun);
    public abstract void getHit(Sword sword);
    protected abstract void randomMove(float dt);
    protected abstract void animateGetHit(float dt);

    public void reverseVelocity(boolean x,boolean y){
        if(x)velocity.x = -velocity.x;
        if(y)velocity.y = -velocity.y;
    }

    public abstract boolean getToDestroy();


    public abstract boolean getDestroyed();


    public abstract void setDestroyed(boolean d);

    public abstract float getStateTime();

}
