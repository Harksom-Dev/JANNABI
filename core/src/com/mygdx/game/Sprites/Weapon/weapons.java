package com.mygdx.game.Sprites.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screen.PlayScreen;

//just doing random stuff about parent of all our weapon
public abstract class weapons extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    protected float ammoCount;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    boolean aimUp;
    boolean aimDown;

    protected int Dmg;
    //TextureRegion region;
    Texture img;
    //for further use
    //public ArrayList<ammo> activeAmmo;

    public weapons(PlayScreen screen, float x, float y, boolean fireRight,boolean aimUp,boolean aimDown){
        this.world = screen.getWorld();
        this.screen = screen;
        this.fireRight = true;
        this.aimUp = true;
        this.aimDown =true;
    }

    public abstract void definedWeapon();
    public abstract void update(float dt);
    public abstract void setToDestroy();
    public abstract boolean isDestroyed();


}
