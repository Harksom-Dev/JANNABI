package com.mygdx.game.Sprites.Item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Player;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean Destroy;
    protected Body body;

    public Item(PlayScreen screen,float x,float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        setBounds(getX(),getY(),16 / Jannabi.PPM,16 / Jannabi.PPM);
        defineItem();
        toDestroy = false;
        Destroy = false;
    }

    public abstract void defineItem();
    public abstract void use(Player player);


    public void update(float dt){
        if(toDestroy && !Destroy){
            world.destroyBody(body);
            Destroy = true;
        }
    }

    public void draw(Batch batch){
        if(!Destroy){
            super.draw(batch);
        }
    }

    public void destroy(){
        toDestroy = true;
    }
}
