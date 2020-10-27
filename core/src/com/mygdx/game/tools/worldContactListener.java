package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Sprites.Enemy.Enemy;
import com.mygdx.game.Sprites.Item.Item;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Weapon.Gun;
import com.mygdx.game.Sprites.Weapon.Sword;

//this class get called when 2 box2d collision
public class worldContactListener implements ContactListener {

    //when fixture start to contact
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //collision magic
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef){
            case Jannabi.ENEMY_BIT | Jannabi.PISTOL_BULLET_BIT:
                if(fixA.getFilterData().categoryBits == Jannabi.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).getHit((Gun) fixB.getUserData());
                    ((Gun)fixB.getUserData()).setToDestroy();
                }
                if(fixB.getFilterData().categoryBits == Jannabi.ENEMY_BIT){
                    ((Enemy)fixB.getUserData()).getHit((Gun) fixA.getUserData());
                    ((Gun)fixA.getUserData()).setToDestroy();
                }
                break;
            case Jannabi.ENEMY_BIT | Jannabi.JANNABI_BIT:
                Gdx.app.log("hit","I'm Hit!!!");
                if(fixA.getFilterData().categoryBits == Jannabi.JANNABI_BIT){
                    ((Player)fixA.getUserData()).getHit();
                }
                if(fixB.getFilterData().categoryBits == Jannabi.JANNABI_BIT){
                    ((Player)fixB.getUserData()).getHit();
                }
                break;
            case Jannabi.ENEMY_BIT | Jannabi.Edge_BIT:
                /*if(fixA.getFilterData().categoryBits == Jannabi.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                }
                if(fixB.getFilterData().categoryBits == Jannabi.ENEMY_BIT){
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                }*/
                break;
            case Jannabi.ITEM_BIT | Jannabi.JANNABI_BIT:
                //Gdx.app.log("hp Up","health potion");
                if(fixA.getFilterData().categoryBits == Jannabi.ITEM_BIT){
                    ((Item)fixA.getUserData()).use((Player) fixB.getUserData());
                }
                if(fixB.getFilterData().categoryBits == Jannabi.ITEM_BIT){
                    ((Item)fixB.getUserData()).use((Player) fixA.getUserData());
                }
                break;
            case Jannabi.ENEMY_BIT | Jannabi.SWORDHITBOX_BIT:
                if(fixA.getFilterData().categoryBits == Jannabi.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).getHit((Sword) fixB.getUserData());
                    //((Sword)fixB.getUserData()).destroy();
                }
                if(fixB.getFilterData().categoryBits == Jannabi.ENEMY_BIT){
                    ((Enemy)fixB.getUserData()).getHit((Sword) fixA.getUserData());
                    //((Sword)fixA.getUserData()).destroy();
                }
                break;

        }




    }

    //after fixture contact
    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() == "body" || fixB.getUserData() == "body"){
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;
             /*if(object.getUserData() != null && PassTest.class.isAssignableFrom(object.getUserData().getClass())){
                Gdx.app.log("canDetectGround","start");
                ((PassTest)object.getUserData()).change();
            }
            if(object.getUserData() != null && B2WorldCreator.class.isAssignableFrom(object.getUserData().getClass())){
                Gdx.app.log("falling","cantpass");
                //TestLayer.test2();
            }*/
            //TestLayer.test2();


        }



    }

    //do some thing when 2 fixture contact
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    //after 2 fixture separate do something
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}
