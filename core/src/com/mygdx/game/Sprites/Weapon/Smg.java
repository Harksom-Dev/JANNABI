package com.mygdx.game.Sprites.Weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;

public class Smg extends Gun {
    public Smg(PlayScreen screen, float x, float y, boolean fireRight, boolean aimUp, boolean aimDown, int Dmg,int clip) {
        super(screen, x, y, fireRight, aimUp, aimDown, Dmg,clip);
        this.reloaded = true;

        img = new TextureRegion(screen.getAtlas().findRegion("bullet"),0,0,16,16);
        setRegion(img);
        //increase width and height to increase bullet size
        setBounds(x-1 / Jannabi.PPM,y, 40 / Jannabi.PPM, 20 / Jannabi.PPM);
        definedBullet();
    }

    @Override
    public void definedBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 / Jannabi.PPM : getX() - 12 / Jannabi.PPM, getY() +2 / Jannabi.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(3/Jannabi.PPM,0.5f/Jannabi.PPM);
        fdef.filter.categoryBits =  Jannabi.PISTOL_BULLET_BIT;
        fdef.filter.maskBits = Jannabi.JANNABI_BIT | Jannabi.ENEMY_BIT ;


        fdef.shape = shape;
        //fdef.restitution = 0;
        //fdef.friction = 1;
        fdef.density = 0.02f;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
        //b2body.setBullet(true);


        /////////////////////////Set position when fire with different angle/////////////////////////////////////////////
        if(!aimUp && !aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 0));

        }else if(aimUp && !aimDown){                                                    //aimUp
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 10));
            if(fireRight){
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 3.5f),b2body.getPosition().y- (getHeight() / 15f) ,60 / Jannabi.PPM);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 3.5f) + 28/ Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 15f),-45);
            }

        }else if(!aimUp && aimDown){                                                    //aimdown
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, -10));
            if(fireRight){
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 50),
                        b2body.getPosition().y- getHeight() / 2,-45);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 50) + 12/ Jannabi.PPM,
                        b2body.getPosition().y,45);
            }


        }
        /////////////////////////Set position when fire with different angle/////////////////////////////////////////////
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        //setRegion(fireAnimation.getKeyFrame(stateTime, true));


        //change position of sprite here
        if(!aimUp && !aimDown){
            //normal case
            setPosition(b2body.getPosition().x - getWidth() / 2.1f, b2body.getPosition().y - getHeight() / 2.1f);

            //aim up case
        }else if(aimUp && !aimDown){
            if(fireRight){

                setRotation(45);
                setPosition(b2body.getPosition().x - (getWidth() / 10) - 1 / Jannabi.PPM, b2body.getPosition().y - (getHeight() / 2) - 9  /Jannabi.PPM  );
            }else{
                setRotation(-45);
                setPosition(b2body.getPosition().x - (getWidth() / 10) - 16 /Jannabi.PPM, b2body.getPosition().y - (getHeight() / 2) + 16 / Jannabi.PPM);
            }

            //aim down case
        }else if(!aimUp && aimDown){

            if(fireRight){
                setRotation(-58);
                setPosition(b2body.getPosition().x - getWidth() / 1.9f  , b2body.getPosition().y - (getHeight() / 50) +16 / Jannabi.PPM );
            }else{
                setRotation(58);
                setPosition(b2body.getPosition().x - getWidth() / 15  , b2body.getPosition().y - (getHeight() / 50) -25 / Jannabi.PPM );
            }



        }


        if((stateTime > 3f || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }

        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();

    }

    @Override
    public void setToDestroy() {
        setToDestroy = true;
    }

    @Override
    public boolean isDestroyed() {
        return  destroyed;
    }
    public int getDmg() {
        return Dmg;
    }
}
