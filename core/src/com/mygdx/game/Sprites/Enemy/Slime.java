package com.mygdx.game.Sprites.Enemy;


import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.Sprites.Enemy.Enemy;
import com.mygdx.game.Sprites.Item.ItemDef;
import com.mygdx.game.Sprites.Item.Potion;
import com.mygdx.game.Sprites.Weapon.Gun;
import com.mygdx.game.Sprites.Weapon.Sword;

public class Slime extends Enemy {

    private float animateDelay;         //delay time
    private float stateTime;
    private Animation<TextureRegion> stayAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> deadAnimation;
    private Animation<TextureRegion> getHitAnimation;

    private Array<TextureRegion> frames;
    private int Hp;
    private boolean setToDestroy;
    private boolean destroy;
    private boolean moveLeft;
    private boolean beenHit;



    public Slime(PlayScreen screen, float x, float y,int Hp) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();

        setTexture();

        setBounds(getX(),getY(),64 / Jannabi.PPM,32 / Jannabi.PPM);
        setToDestroy = false;
        destroy = false;
        this.Hp  = Hp;
        moveLeft = true;
        pauseJump = 1;
        beenHit = false;
        drop = false;

    }

    public void update(float dt){

        stateTime += dt;
        if(setToDestroy && !destroy){
            //world.destroyBody(b2body);
            //destroy = true;
            stateTime = 0;
            setRegion(deadAnimation.getKeyFrame(0.5f,false));
        }else if(!destroy){
            //random movement ot this slime
            randomMove(dt);

            setPosition(b2body.getPosition().x - getWidth() / 2.5f,b2body.getPosition().y - getHeight()/ 3);
            //check if slime get hit change animation
            if(beenHit){
                animateGetHit(dt);

            }else{
                setRegion(stayAnimation.getKeyFrame(stateTime,true));
            }

        }

    }

    @Override
    protected void setTexture() {
        for(int i =0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("slime_stay"),i*48,0,64,32));
        }
        stayAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();
        stateTime = 0;
        animateDelay = 0;
        for(int i = 0; i < 7 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("slime_attack"),i*48,0,64,32));
        }
        walkAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("slime_dead"),i*48,0,64,32));
        }
        deadAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("slime_gethit"),i*48,0,64,32));
        }
        getHitAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        //set box2d our animation now have width32 and height 32
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
        //what our slime can collide with
        fdef.filter.maskBits = Jannabi.DEFAULT_BIT | Jannabi.OTHERLAYER_BIT | Jannabi.ENEMY_BIT | Jannabi.JANNABI_BIT | Jannabi.PISTOL_BULLET_BIT
                                | Jannabi.Edge_BIT | Jannabi.SWORDHITBOX_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.2f;
        fdef.density = 50;
        //fdef.isSensor = false;//this sensor use for jumping through
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        if(!destroy || stateTime < 1){
            super.draw(batch);
        }

    }


    public void getHit(Gun gun) {
        //setRegion(tempHitAnimation.getKeyFrame(0.5f,true));
        //b2body.setLinearVelocity(3,2);

        Hp -= gun.getDmg();
        beenHit = true;
        if(Hp <= 0){
            setToDestroy = true;
            //define drop condition
            if(!drop){
                screen.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x,b2body.getPosition().y + 25),Potion.class));
                drop = true;
            }

        }

    }

    @Override
    public void getHit(Sword sword) {
        Gdx.app.log("Im Bleeding!!!","");
        Hp -= sword.getDmg();
        beenHit = true;
        if(Hp <= 0){
            setToDestroy = true;
            //define drop condition
            if(!drop){
                screen.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x,b2body.getPosition().y + 25),Potion.class));
                drop = true;
            }

        }
    }

    @Override
    protected void randomMove(float dt) {
        pauseJump -= dt;
        if(pauseJump < 0){
            if(moveLeft && b2body.getLinearVelocity().y == 0){
                b2body.applyLinearImpulse(velocity,b2body.getWorldCenter(),true);
                moveLeft = false;
                pauseJump = 1.5f;
            }else if(!moveLeft && b2body.getLinearVelocity().y == 0){
                //b2body.applyLinearImpulse(new Vector2(0.07f,0.15f),b2body.getWorldCenter(),true);
                moveLeft = true;
                pauseJump = 1.5f;
            }if(b2body.getLinearVelocity().y == 0 && b2body.getLinearVelocity().x !=0){
                b2body.setLinearVelocity(0,0);
            }

        }
    }

    @Override
    protected void animateGetHit(float dt) {
        animateDelay += dt;
        setRegion(getHitAnimation.getKeyFrame(stateTime,false));
        if(animateDelay > 0.125f){

            beenHit = false;
            animateDelay = 0;
        }
    }

    @Override
    public boolean getToDestroy() {
        return setToDestroy;
    }

    @Override
    public boolean getDestroyed() {
        return destroy;
    }

    @Override
    public void setDestroyed(boolean d) {
        destroy = d;
    }

    @Override
    public float getStateTime() {
        return stateTime;
    }
}
