package com.mygdx.game.Sprites;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Weapon.pistol;

public class Slime extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> stayAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> tempHitAnimation;

    private Array<TextureRegion> frames;
    private int Hp;
    private boolean setToDestroy;
    private boolean destroy;
    private boolean moveLeft;

    public Slime(PlayScreen screen, float x, float y,int Hp) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for(int i =0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("slime_stay"),i*48,0,64,32));
        }
        stayAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();
        stateTime = 0;
        for(int i = 0; i < 7 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("slime_attack"),i*48,0,64,32));
        }
        walkAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("slime_gethit"),i*48,0,64,32));
        }
        tempHitAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        setBounds(getX(),getY(),64 / Jannabi.PPM,32 / Jannabi.PPM);
        setToDestroy = false;
        destroy = false;
        this.Hp  = Hp;
        moveLeft = false;

    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroy){
            world.destroyBody(b2body);
            destroy = true;
            stateTime = 0;
            setRegion(tempHitAnimation.getKeyFrame(0.5f,false));
        }else if(!destroy){
            if(
                b2body.applyLinearImpulse(new Vector2(0,0.0002f),b2body.getWorldCenter(),true);




            setPosition(b2body.getPosition().x - getWidth() / 2.5f,b2body.getPosition().y - getHeight()/ 3);
            setRegion(stayAnimation.getKeyFrame(stateTime,true));
        }

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        //set box2d our animation now have width16 and height 32
        bdef.position.set(getX(),getY()/Jannabi.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //create fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Jannabi.PPM);

        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox(16,32);

        //set category bit
        fdef.filter.categoryBits = Jannabi.ENEMY_BIT;
        //what our mainPlayer can collide with
        fdef.filter.maskBits = Jannabi.DEFAULT_BIT | Jannabi.OTHERLAYER_BIT | Jannabi.ENEMY_BIT | Jannabi.JANNABI_BIT | Jannabi.PISTOL_BULLET_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.2f;
        fdef.density = 5;
        //fdef.isSensor = false;//this sensor use for jumping through
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        if(!destroy || stateTime < 1){
            super.draw(batch);
        }

    }


    public void getHit(pistol pistol) {
        //setRegion(tempHitAnimation.getKeyFrame(0.5f,true));
        //b2body.setLinearVelocity(3,2);
        Hp -=pistol.Dmg;
        if(Hp <= 0){
            setToDestroy = true;
        }

    }


}
