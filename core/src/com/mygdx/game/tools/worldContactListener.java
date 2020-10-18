package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.StageTile.TestLayer;

//this class get called when 2 box2d collision
public class worldContactListener implements ContactListener {

    //when fixture start to contact
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() == "belowHitBox" || fixB.getUserData() == "belowHitBox"){
            Fixture body = fixA.getUserData() == "belowHitBox" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;
            //Gdx.app.log("begincontact","pass");
           /*if(object.getUserData() != null && B2WorldCreator.class.isAssignableFrom(object.getUserData().getClass())){
                Gdx.app.log("begincontact","pass");
                //((PassTest)object.getUserData()).change();
            }
            if(object.getUserData() != null && PassTest.class.isAssignableFrom(object.getUserData().getClass())){

                ((PassTest)object.getUserData()).notChange();
            }*/
            //TestLayer.test();
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
