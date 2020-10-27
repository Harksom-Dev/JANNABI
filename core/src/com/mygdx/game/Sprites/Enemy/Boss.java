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
import com.mygdx.game.Sprites.Item.ItemDef;
import com.mygdx.game.Sprites.Item.Potion;
import com.mygdx.game.Sprites.Weapon.Gun;
import com.mygdx.game.Sprites.Weapon.Sword;

public class Boss extends Enemy {
    public enum State{WALK,STAND,ATTACK,GETHIT,DEAD}
    public State currentState;
    public State previousState;

    private float animateDelay;         //delay time
    private float stateTime;
    private float walkTime;

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
    private boolean bossDead;

    public Boss(PlayScreen screen, float x, float y,int hp) {
        super(screen, x, y);
        this.Hp = hp;
        frames = new Array<TextureRegion>();

        setTexture();

        setBounds(getX(),getY(),64 / Jannabi.PPM,64 / Jannabi.PPM);
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
        bossDead = false;
    }

    @Override
    protected void setTexture() {

        animateDelay = 0;
        for(int i = 0; i < 4 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("boss_walk"),i*47,0,47,32));
        }
        walkAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("boss_gethit"),i*47,0,47,32));
        }
        deadAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        for(int i = 0; i < 2 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("boss_gethit"),i*47,0,47,32));
        }
        getHitAnimation = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();
        for(int i = 0; i < 7 ;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("boss_attack"),i*47,0,47,32));
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
        shape.setRadius(20 / Jannabi.PPM);

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
            case ATTACK:
                region = attackAnimation.getKeyFrame(stateTime,true);
                break;
            case GETHIT:
                region = getHitAnimation.getKeyFrame(stateTime,false);
                break;
            default:
                region = deadAnimation.getKeyFrame(stateTime,false);
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
            setPosition(b2body.getPosition().x - getWidth() / 2 - 8 / Jannabi.PPM,b2body.getPosition().y - getHeight()/ 2 + 11 / Jannabi.PPM);
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
    public void getHit(Gun gun) {
        Hp -= gun.getDmg();
        beenHit = true;
        if(Hp <= 0){
            setToDestroy = true;
            bossDead = true;
            //define drop condition
            if(!drop){
                screen.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x,b2body.getPosition().y + 25), Potion.class));
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
            bossDead = true;
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
            if(moveLeft&& b2body.getLinearVelocity().y == 0){
                b2body.applyLinearImpulse(new Vector2(-7,9),b2body.getWorldCenter(),true);
                b2body.setLinearVelocity(-10,0);
                moveLeft = false;
                pauseJump = 2;
            }else if(!moveLeft && b2body.getLinearVelocity().y == 0){
                b2body.applyLinearImpulse(new Vector2(7,9),b2body.getWorldCenter(),true);
                b2body.setLinearVelocity(10,0);
                moveLeft = true;
                pauseJump = 2;
            }else if(pauseJump == 2){
                if(moveLeft){
                    b2body.setLinearVelocity(-20,10);
                }else
                    b2body.setLinearVelocity(20,10);
            }

        }
    }

    @Override
    protected void animateGetHit(float dt) {
        animateDelay += dt;
        setRegion(deadAnimation.getKeyFrame(stateTime,false));
        if(animateDelay > 0.125f){

            beenHit = false;
            animateDelay = 0;
        }
    }

    @Override
    public void attack(boolean attack, boolean attackLeft) {
        if(attack){
            currentState = State.ATTACK;
            this.attackLeft = attackLeft;
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
        return bossDead;
    }
}
