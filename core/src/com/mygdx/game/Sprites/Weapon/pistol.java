package com.mygdx.game.Sprites.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;

public class pistol extends weapons {

    public pistol(PlayScreen screen, float x, float y, boolean fireRight,boolean aimUp,boolean aimdown) {
        super(screen,x,y,fireRight,aimUp,aimdown);
        this.fireRight = fireRight;
        this.aimUp = aimUp;
        this.aimDown = aimdown;
        this.screen = screen;
        this.world = screen.getWorld();

        img = new Texture("bullet/pistol.png");
        setRegion(img);
        //increase width and height to increase bullet size
        setBounds(x-1 / Jannabi.PPM, y, 50 / Jannabi.PPM, 30 / Jannabi.PPM);
        definedWeapon();
    }

    @Override
    public void definedWeapon() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 / Jannabi.PPM : getX() - 12 / Jannabi.PPM, getY() +2 / Jannabi.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5/Jannabi.PPM,1/Jannabi.PPM);
        fdef.filter.categoryBits =  Jannabi.PISTOL_BULLET_BIT;
        fdef.filter.maskBits = Jannabi.JANNABI_BIT ;


        fdef.shape = shape;
        fdef.restitution = 0;
        fdef.friction = 1;
        fdef.density = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setBullet(true);

        if(!aimUp && !aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 0));


        }else if(aimUp && !aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, 10));
            if(fireRight){
                b2body.setTransform(b2body.getPosition().x- getWidth()/ 3.5f,b2body.getPosition().y- getHeight() / 10,45);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 50) + 15/ Jannabi.PPM,b2body.getPosition().y- getHeight() / 10,-45);
            }

        }else if(!aimUp && aimDown){
            b2body.setLinearVelocity(new Vector2(fireRight ? 10 : -10, -10));
            if(fireRight){
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 50) + 5/ Jannabi.PPM,b2body.getPosition().y- getHeight() / 2,-45);
            }else{
                b2body.setTransform(b2body.getPosition().x- (getWidth()/ 50) + 5/ Jannabi.PPM,b2body.getPosition().y- getHeight() / 2,45);
            }


        }

        //
        //b2body.setLinearVelocity(3.5f,0);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        //setRegion(fireAnimation.getKeyFrame(stateTime, true));
        //change position of sprite here
        if(!aimUp && !aimDown){
            //normal case
            setPosition(b2body.getPosition().x - getWidth() / 1.88697f, b2body.getPosition().y - getHeight() / 2.1f);

        //aim up case
        }else if(aimUp && !aimDown){
            if(fireRight){

                setRotation(58);
                setPosition(b2body.getPosition().x - getWidth() / 19, b2body.getPosition().y - getHeight() / 1 );
            }else{
                setRotation(-58);
                setPosition(b2body.getPosition().x - (getWidth() / 15) - 6.5f /Jannabi.PPM, b2body.getPosition().y - (getHeight() / 7) + 4/Jannabi.PPM);
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
