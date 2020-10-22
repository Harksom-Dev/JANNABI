package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Weapon.pistol;
import com.mygdx.game.Sprites.Weapon.weapons;

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected int Hp;
    public Vector2 velocity;
    protected float pauseJump;

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

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void getHit(pistol pistol);
    protected abstract void randomMove(float dt);

    public void reverseVelocity(boolean x,boolean y){
        if(x)velocity.x = -velocity.x;
        if(y)velocity.y = -velocity.y;
    }
}
