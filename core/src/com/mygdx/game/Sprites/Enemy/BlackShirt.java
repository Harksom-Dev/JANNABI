package com.mygdx.game.Sprites.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.mygdx.game.Sprites.Item.ItemDef;
import com.mygdx.game.Sprites.Item.Mag;
import com.mygdx.game.Sprites.Item.Potion;
import com.mygdx.game.Sprites.Weapon.Gun;
import com.mygdx.game.Sprites.Weapon.Sword;

public class BlackShirt extends Enemy {
    public enum State{WALK,STAND,ATTACK,GETHIT,DEAD}
    public State currentState;
    public State previousState;

    private float animateDelay;         //delay time
    private float stateTime;
    private float walkTime;
    private Animation<TextureRegion> stayAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> deadAnimation;
    private Animation<TextureRegion> getHitAnimation;
    private Animation<TextureRegion> attackAnimation;

    private Array<TextureRegion> frames;
    private int Hp;
    private boolean setToDestroy;
    private boolean destroy;
    private boolean moveLeft;
    private boolean beenHit;
    private boolean attackLeft;

    public BlackShirt(PlayScreen screen, float x, float y,int Hp) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();

        setTexture();

        setBounds(getX(),getY(),32 / Jannabi.PPM,32 / Jannabi.PPM);
        setToDestroy = false;
        destroy = false;
        this.Hp  = Hp;
        moveLeft = true;
        pauseJump = 1;
        beenHit = false;
        drop = false;
        walkTime = 0;
        stateTime = 0;
        currentState = State.STAND;
    }

    @Override
    protected void setTexture() {

        for(int i =0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("tamruot_stand"),i*32,0,32,32));
        }
        stayAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        animateDelay = 0;
        for(int i = 0; i < 4 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("tamruot_walk"),i*35,0,35,32));
        }
        walkAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("tamruot_gethit"),i*32,0,32,32));
        }
        deadAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("tamruot_gethit"),i*32,0,32,32));
        }
        getHitAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();
        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("tamruot_hit"),i*32,0,32,32));
        }
        attackAnimation = new Animation<TextureRegion>(0.5f,frames);
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
        shape.setRadius(8 / Jannabi.PPM);

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

    private TextureRegion getFrame(float dt){
        stateTime += dt;
        TextureRegion region;
        switch (currentState){
            case WALK:
                region = walkAnimation.getKeyFrame(stateTime,true);
                break;
            case STAND:
                region = stayAnimation.getKeyFrame(stateTime,true);
                break;
            case ATTACK:
                region = attackAnimation.getKeyFrame(stateTime,true);
                break;
            case GETHIT:
                region = getHitAnimation.getKeyFrame(stateTime,false);
                break;
            default:
                region = deadAnimation.getKeyFrame(stateTime,true);
                break;

        }
        if((b2body.getLinearVelocity().x < 0 ) && region.isFlipX() == false ){
            region.flip(true,false);
        }
        if((b2body.getLinearVelocity().x > 0 ) && region.isFlipX() == true){
            region.flip(true,false);
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;

    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        setRegion(getFrame(dt));
        if(setToDestroy && !destroy){
            //world.destroyBody(b2body);
            //destroy = true;
            stateTime = 0;
            currentState = State.DEAD;
            //setRegion(deadAnimation.getKeyFrame(0.5f,false));
        }else if(!destroy){
            //random movement ot this slime
            randomMove(dt);
            currentState = State.WALK;
            setPosition(b2body.getPosition().x - getWidth() / 2.5f,b2body.getPosition().y - getHeight()/ 3);
            //check if slime get hit change animation
            if(beenHit){
                currentState = State.DEAD;
                animateGetHit(dt);

            }

        }


    }

    @Override
    public void draw(Batch batch) {
        if(!destroy || stateTime < 1){
            super.draw(batch);
        }

    }

    @Override
    public void attack(boolean attack,boolean attackLeft) {
        if(attack){
            currentState = State.ATTACK;
            this.attackLeft = attackLeft;
        }


    }

    @Override
    public void getHit(Gun gun) {
        Hp -= gun.getDmg();
        beenHit = true;
        currentState = State.GETHIT;
        if(Hp <= 0){
            setToDestroy = true;
            currentState = State.DEAD;
            //define drop condition
            if(!drop){

                screen.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x,b2body.getPosition().y + 32), Mag.class));
                drop = true;
            }

        }
    }

    @Override
    public void getHit(Sword sword) {
        Gdx.app.log("Im Bleeding!!!","");
        Hp -= sword.getDmg();
        beenHit = true;
        currentState = State.GETHIT;
        if(Hp <= 0){
            setToDestroy = true;
            currentState = State.DEAD;
            //define drop condition
            if(!drop){

                screen.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x,b2body.getPosition().y + 32),Mag.class));
                drop = true;
            }

        }
    }

    @Override
    protected void randomMove(float dt) {
        //need to change
        pauseJump -= dt;
        if(pauseJump < 0){
            if(moveLeft){
                b2body.setLinearVelocity(-0.6f,0);
                //b2body.applyLinearImpulse(new Vector2(-0.5f,0),b2body.getWorldCenter(),true);
                moveLeft = false;
                pauseJump = 2;
            }else if(!moveLeft){
                b2body.setLinearVelocity(+0.6f,0);
                //b2body.applyLinearImpulse(new Vector2(0.5f,0),b2body.getWorldCenter(),true);
                moveLeft = true;
                pauseJump = 2;
            }

        }
    }

    @Override
    protected void animateGetHit(float dt) {
        Jannabi.manager.get("Audio/Sound/mons/monsterHit.wav", Sound.class).play();
        animateDelay += dt;
        setRegion(deadAnimation.getKeyFrame(stateTime,false));
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

    @Override
    public boolean isBossDead() {
        return false;
    }
}
