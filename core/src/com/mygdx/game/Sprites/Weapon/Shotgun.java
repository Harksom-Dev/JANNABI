package com.mygdx.game.Sprites.Weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;


public class Shotgun extends Gun {

    private Body bullet1;
    private Body bullet2;
    private Body bullet3;

    public Shotgun(PlayScreen screen, float x, float y, boolean fireRight, boolean aimUp, boolean aimDown, int Dmg, int clip) {
        super(screen, x, y, fireRight, aimUp, aimDown, Dmg, clip);
        this.reloaded = true;

        img = new TextureRegion(screen.getAtlas().findRegion("bullet"),32,0,16,16);
        setRegion(img);
        //increase width and height to increase bullet size
        setBounds(x-1 / Jannabi.PPM, y, 30 / Jannabi.PPM, 20 / Jannabi.PPM);
        /*definedBullet(bullet1,0,10,-10,0);
        definedBullet(bullet2,1,15,-8,15 / Jannabi.PPM);
        definedBullet(bullet3,-1,8,-13,-15/Jannabi.PPM);*/
        definedBullet();
    }



    public void definedBullet(Body body,float normalVeloY,float aimUpVeloY,float aimDownVeloy,float angle) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 / Jannabi.PPM : getX() - 12 / Jannabi.PPM, getY() +2 / Jannabi.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        if(!world.isLocked())
            body = world.createBody(bdef);


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
        body.createFixture(fdef).setUserData(this);
        //b2body.setBullet(true);


        /////////////////////////Set position when fire with different angle/////////////////////////////////////////////
        if(!aimUp && !aimDown){
            body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, normalVeloY));
            if(angle != 0){
                if(fireRight){
                    body.setTransform((body.getPosition().x- getWidth()/ 2) + 3.5f / Jannabi.PPM,(body.getPosition().y- getHeight() / 2) + 8 / Jannabi.PPM,-angle);
                }else{
                    body.setTransform(body.getPosition().x- (getWidth()/ 2) + 25 / Jannabi.PPM,
                            (body.getPosition().y- getHeight() / 2)+ 12 / Jannabi.PPM,angle);
                }
            }


        }else if(aimUp && !aimDown){
            body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, normalVeloY));
            if(fireRight){
                body.setTransform((body.getPosition().x- getWidth()/ 2) + 3.5f / Jannabi.PPM,(body.getPosition().y- getHeight() / 2) + 8 / Jannabi.PPM,45 + angle);
            }else{
                body.setTransform(body.getPosition().x- (getWidth()/ 2) + 25 / Jannabi.PPM,
                        (body.getPosition().y- getHeight() / 2)+ 12 / Jannabi.PPM,-45 - angle);
            }

        }else if(!aimUp && aimDown){
            body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, aimDownVeloy));
            if(fireRight){
                body.setTransform(body.getPosition().x- (getWidth()/ 2) + 5/ Jannabi.PPM,
                        (body.getPosition().y- getHeight() / 2)+ 8 / Jannabi.PPM ,-45 + angle);
            }else{
                body.setTransform(body.getPosition().x- (getWidth()/ 2) + 25/ Jannabi.PPM,
                        (body.getPosition().y- getHeight() / 2) + 12 / Jannabi.PPM,45 - angle);
            }


        }
        /////////////////////////Set position when fire with different angle/////////////////////////////////////////////
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

        }else if(aimUp && !aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 10));
            if(fireRight){
                b2body.setTransform((b2body.getPosition().x- getWidth()/ 2) + 3.5f / Jannabi.PPM,(b2body.getPosition().y- getHeight() / 2) + 8 / Jannabi.PPM,45);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25 / Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 12 / Jannabi.PPM,-45);
            }

        }else if(!aimUp && aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, -10));
            if(fireRight){
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 5/ Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 8 / Jannabi.PPM ,-45);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25/ Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2) + 12 / Jannabi.PPM,45);
            }


        }
        /////////////////////////Set position when fire with different angle/////////////////////////////////////////////
    }


    /*public void definedBullet2() {
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
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 1));
            if(fireRight){
                b2body.setTransform((b2body.getPosition().x- getWidth()/ 2) + 3.5f / Jannabi.PPM,(b2body.getPosition().y- getHeight() / 2) + 8 / Jannabi.PPM,15 / Jannabi.PPM);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25 / Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 12 / Jannabi.PPM,-15 / Jannabi.PPM);
            }

        }else if(aimUp && !aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 15));
            if(fireRight){
                b2body.setTransform((b2body.getPosition().x- getWidth()/ 2) + 3.5f / Jannabi.PPM,(b2body.getPosition().y- getHeight() / 2) + 8 / Jannabi.PPM,45 + 15 / Jannabi.PPM);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25 / Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 12 / Jannabi.PPM,-45 - 15 / Jannabi.PPM);
            }

        }else if(!aimUp && aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, -8));
            if(fireRight){
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 5/ Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 8 / Jannabi.PPM ,-45 + 15/Jannabi.PPM);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25/ Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2) + 12 / Jannabi.PPM,45 - 15/ Jannabi.PPM);
            }


        }
        /////////////////////////Set position when fire with different angle/////////////////////////////////////////////

    }
    public void definedBullet3() {
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
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, -1));
            if(fireRight){
                b2body.setTransform((b2body.getPosition().x- getWidth()/ 2) + 3.5f / Jannabi.PPM,(b2body.getPosition().y- getHeight() / 2) + 8 / Jannabi.PPM,-15 / Jannabi.PPM);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25 / Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 12 / Jannabi.PPM,15 / Jannabi.PPM);
            }

        }else if(aimUp && !aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 8));
            if(fireRight){
                b2body.setTransform((b2body.getPosition().x- getWidth()/ 2) + 3.5f / Jannabi.PPM,(b2body.getPosition().y- getHeight() / 2) + 8 / Jannabi.PPM,45 - 15 / Jannabi.PPM);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25 / Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 12 / Jannabi.PPM,-45 + 15 / Jannabi.PPM);
            }

        }else if(!aimUp && aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, -13));
            if(fireRight){
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 5/ Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2)+ 8 / Jannabi.PPM ,-45 - 15 / Jannabi.PPM);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 2) + 25/ Jannabi.PPM,
                        (b2body.getPosition().y- getHeight() / 2) + 12 / Jannabi.PPM,45 + 15 / Jannabi.PPM);
            }


        }
        /////////////////////////Set position when fire with different angle/////////////////////////////////////////////

    }*/

    @Override
    public void update(float dt) {
        stateTime += dt;
        //setRegion(fireAnimation.getKeyFrame(stateTime, true));


        //change position of sprite here
        if(!aimUp && !aimDown){
            //normal case
            setPosition(b2body.getPosition().x - (getWidth() / 2) + 1/Jannabi.PPM, b2body.getPosition().y - getHeight() / 2.1f);

            //aim up case
        }else if(aimUp && !aimDown){
            if(fireRight){

                setRotation(58);
                setPosition(b2body.getPosition().x - (getWidth() / 2) + 15.5f / Jannabi.PPM, b2body.getPosition().y - (getHeight() / 2) - 7 / Jannabi.PPM  );
            }else{
                setRotation(-58);
                setPosition(b2body.getPosition().x - (getWidth() / 2) , b2body.getPosition().y - (getHeight() / 2) + 16/Jannabi.PPM);
            }

            //aim down case
        }else if(!aimUp && aimDown){

            if(fireRight){
                setRotation(-58);
                setPosition(b2body.getPosition().x - getWidth() / 2  , (b2body.getPosition().y - getHeight() / 2) +16 / Jannabi.PPM );
            }else{
                setRotation(58);
                setPosition((b2body.getPosition().x - getWidth() / 2) + 15 / Jannabi.PPM  , (b2body.getPosition().y - getHeight() / 2) - 8 / Jannabi.PPM );
            }



        }


        //setPosition(b2body.getPosition().x - getWidth() / 1.88697f, b2body.getPosition().y - getHeight() / 2.1f);
        if((stateTime > 3f || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        /*if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 0.1f);*/
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
}
