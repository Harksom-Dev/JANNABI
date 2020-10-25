package com.mygdx.game.Sprites.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screen.PlayScreen;

//just doing random stuff about parent of all our weapon
public abstract class Gun extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    protected int ammoCount;
    float stateTime;
    float fireRate;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    boolean aimUp;
    boolean aimDown;
    boolean reloaded;

    protected int Dmg;
    protected int clip;
    //TextureRegion region;
    TextureRegion img;
    //for further use
    //public ArrayList<ammo> activeAmmo;

    public Gun(PlayScreen screen, float x, float y, boolean fireRight, boolean aimUp, boolean aimDown, int Dmg, int clip){
        this.world = screen.getWorld();
        this.screen = screen;
        this.fireRight = fireRight;
        this.aimUp = aimUp;
        this.aimDown =aimDown;
        this.Dmg = Dmg;
        this.clip = clip;
    }

    public abstract void definedWeapon();
    public abstract void update(float dt);
    public abstract void setToDestroy();
    public abstract boolean isDestroyed();

    public int getClip() {
        return clip;
    }

    public int getDmg() {
        return Dmg;
    }
}
