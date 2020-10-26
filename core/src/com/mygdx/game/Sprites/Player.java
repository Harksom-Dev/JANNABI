package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Weapon.pistol;

//this class is create for create main player create box2d sprite and further
public class Player extends Sprite {

    //enum for checkState
    public enum State {FALLING,JUMPING,STANDING,RUNNING,STAND_AIM_UP,STAND_AIM_DOWN,RUNNING_AIM_UP,RUNNING_AIM_DOWN,
                        JUMP_AIM_UP,JUMP_AIM_DOWN,RELOAD,DEAD,GETHIT};


    //implement state to check current and previous
    public State currentState;
    public State previousState;

    //implement box2d
    public World world;
    public Body b2body;

    //implement texture or animation for character
    private Animation<TextureRegion> playerStand;
    private TextureRegion playerJump;
    private TextureRegion playerStandAimUp;
    private TextureRegion playerStandAimDown;
    private TextureRegion playerJumAimUp;
    private TextureRegion playerJumAimDown;
    private Animation<TextureRegion> playerPistolGetHit;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerRunAimUp;
    private Animation<TextureRegion> playerRunAimDown;
    private Animation<TextureRegion> playerReload;


    //implement StateTime for something(don't Know yet) and boolean check that we running right or not
    private float stateTimer;
    private boolean runningRight;
    private boolean aimUp;
    private boolean aimDown;
    PlayScreen screen;
    //pistol shot array
    private Array<pistol> pistolsBullet;

    //hit count of our main character
    private int hp;
    //use for display hit anim
    private boolean beenHit;

    private float animateDelay;

    private int pistolClip = 13;
    private int currentAmmo;
    private int allAmmo;
    private boolean reloaded;

    //create Constructor
    public Player( PlayScreen screen){
        //get start image in .pack
        super(screen.getAtlas().findRegion("stand_aim"));
        this.screen = screen;
        this.world = screen.getWorld();

        //set all necessary State
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        //all boolean and vaule for animation
        runningRight = true;
        aimUp = false;
        aimDown = false;
        hp = 10;
        beenHit = false;
        reloaded = false;
        animateDelay = 0;


        //create this class for shorter constructor that implement all pistol animation
        setJannabiWithPistol(screen);

        //use to check collision and create box2d body
        definePlayer();

        //link sprite to box2d
        setBounds(0,0,32 / Jannabi.PPM,32 / Jannabi.PPM);
        //setRegion(playerStand);

        pistolsBullet = new Array<>();
        currentAmmo = pistolClip;
        allAmmo = 52;
    }

    //create methods for implement all animation
    private void setJannabiWithPistol(PlayScreen screen){
        //create Array to store all image to use to animation
        com.badlogic.gdx.utils.Array<TextureRegion> frames = new Array<TextureRegion>();
        //loop for get runAnimation
        for(int i = 1; i < 4 ; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("pistol_run"),i*32,0,32,32));
        }
        playerRun = new Animation<TextureRegion>(0.12f,frames);
        frames.clear();

        //get jump animation
        playerJump = new TextureRegion(screen.getAtlas().findRegion("jump_aim"),64,0,32,32);


        //get stand texture
        for(int i = 0;i < 2;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("stand_aim"),i*32,0,32,32));
        }
        playerStand = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();
        //playerStand = new TextureRegion(screen.getAtlas().findRegion("stand_aim"),0,0,32,32);

        //get stand aim texture
        //aim up
        playerStandAimUp = new TextureRegion(screen.getAtlas().findRegion("stand_aim"),64,0,32,32);
        //aim down
        playerStandAimDown = new TextureRegion(screen.getAtlas().findRegion("stand_aim"),96,0,32,32);

        //get aim&run
        //aimUp&run
        for(int i = 0;i < 4;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("run_aim"),i*32,0,32,32));
        }
        playerRunAimUp = new Animation<TextureRegion>(0.12f,frames);
        frames.clear();
        //aimDown&run
        for(int i = 4;i < 8;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("run_aim"),i*32,0,32,32));
        }
        playerRunAimDown = new Animation<TextureRegion>(0.12f,frames);
        frames.clear();

        //get jump & aim
        //jump & aim up
        playerJumAimUp = new TextureRegion(screen.getAtlas().findRegion("jump_aim"),0,0,32,32);
        //jump & aim down
        playerJumAimDown = new TextureRegion(screen.getAtlas().findRegion("jump_aim"),32,0,32,32);

        //get reload animation
        for(int i = 0;i < 5;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("normal_reload"),i*32,0,32,32));

        }
        frames.add(new TextureRegion(screen.getAtlas().findRegion("stand_aim"),0,0,32,32));
        playerReload = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        //get hit animation
        //playerPistolGetHit = new TextureRegion(screen.getAtlas().findRegion("pistol_gethit"),0,0,32,32);
        frames.add(new TextureRegion(screen.getAtlas().findRegion("pistol_gethit"),0,0,32,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("pistol_gethit"),0,0,32,32));
        playerPistolGetHit = new Animation<TextureRegion>(0.5f,frames);
    }


    public void update(float dt){

        //set position of sprite here
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 3.5f);
        if(beenHit){
            animateDelay += dt;
            Gdx.app.log("player gethit",""+animateDelay);
            setRegion(playerPistolGetHit.getKeyFrame(dt,false));
            if(animateDelay > 0.125f){

                beenHit = false;
                animateDelay = 0;
            }
        }else{
            setRegion(getFrame(dt));
        }


            for(pistol  bullet : pistolsBullet) {
                bullet.update(dt);
                if(bullet.isDestroyed())
                    pistolsBullet.removeValue(bullet, true);
            }



    }

    //checkState
    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = playerJump;
                break;
            case JUMP_AIM_UP:
                region = playerJumAimUp;
                break;
            case JUMP_AIM_DOWN:
                region = playerJumAimDown;
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer,true);
                break;
            case RUNNING_AIM_UP:
                region = playerRunAimUp.getKeyFrame(stateTimer,true);
                break;
            case RUNNING_AIM_DOWN:
                region = playerRunAimDown.getKeyFrame(stateTimer,true);
                break;
            case STAND_AIM_UP:
                region = playerStandAimUp;
                break;
            case STAND_AIM_DOWN:
                region = playerStandAimDown;
                break;
            case RELOAD:
                region = playerReload.getKeyFrame(stateTimer);
                break;
            case GETHIT:
            case DEAD:
                region = playerPistolGetHit.getKeyFrame(stateTimer);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand.getKeyFrame(stateTimer,true);
                break;
        }

        //check now we running to the right or left
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;

        }else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;

        }

        //confuse af
        //if cur = pre state+dt else state+0
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    //this class check that we jump or do other animation
    public State getState(){
        if(hp <= 0){
            return State.DEAD;
        }else if(beenHit){
            beenHit = false;
            return State.GETHIT;
        }else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                aimUp = true;
                return State.JUMP_AIM_UP;
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                aimDown = true;
                return State.JUMP_AIM_DOWN;
            }else {
                aimUp = false;
                aimDown = false;
                return State.JUMPING;
            }
        }else if(b2body.getLinearVelocity().y < 0){
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                aimUp = true;
                return State.JUMP_AIM_UP;
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                aimDown = true;
                return State.JUMP_AIM_DOWN;
            }else {
                aimUp = false;
                aimDown = false;
                return State.FALLING;
            }
        }else if(b2body.getLinearVelocity().x != 0){
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                aimUp = true;
                return State.RUNNING_AIM_UP;
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                aimDown = true;
                return State.RUNNING_AIM_DOWN;
            }else{
                aimUp = false;
                aimDown = false;
                return State.RUNNING;
            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            aimUp = true;
            return State.STAND_AIM_UP;
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            aimDown = true;
            return State.STAND_AIM_DOWN;
        }else if(Gdx.input.isKeyPressed(Input.Keys.F)){
            aimUp = false;
            aimDown = false;
            reloaded = true;
            return State.RELOAD;
        }else{
            aimUp = false;
            aimDown = false;
            return State.STANDING;
        }
    }


    //create box2d and set fixture
    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        //set box2d our animation now have width16 and height 32
        bdef.position.set(32 / Jannabi.PPM,32 / Jannabi.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //create fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f / Jannabi.PPM);

        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox(16,32);

        //set category bit
        fdef.filter.categoryBits = Jannabi.JANNABI_BIT;
        //what our mainPlayer can collide with
        fdef.filter.maskBits = Jannabi.DEFAULT_BIT | Jannabi.OTHERLAYER_BIT | Jannabi.ENEMY_BIT | Jannabi.Edge_BIT;

        fdef.shape = shape;
        //fdef.isSensor = false;//this sensor use for jumping through
        b2body.createFixture(fdef).setUserData(this);
        //b2body.createFixture(fdef);
        /*
        //create above hitBox with edgeShape
        EdgeShape aboveHitBox = new EdgeShape();
        aboveHitBox.set(new Vector2(-6.5f / Jannabi.PPM,16 / Jannabi.PPM),new Vector2(6.5f / Jannabi.PPM , 16 / Jannabi.PPM));
        fdef.shape = aboveHitBox;
        fdef.isSensor = true;
        //b2body.createFixture(fdef).setUserData(this);
        b2body.createFixture(fdef);

        //create leftHitBox
        EdgeShape leftHitBox = new EdgeShape();
        leftHitBox.set(new Vector2(-7.5f / Jannabi.PPM,16 / Jannabi.PPM),new Vector2(-7.5f / Jannabi.PPM , -8 / Jannabi.PPM));
        fdef.shape = leftHitBox;
        fdef.isSensor = true;
        //b2body.createFixture(fdef).setUserData(this);
        b2body.createFixture(fdef);

        //create rightHitBox
        EdgeShape rightHitBox = new EdgeShape();
        rightHitBox.set(new Vector2(7.5f / Jannabi.PPM,16 / Jannabi.PPM),new Vector2(7.5f / Jannabi.PPM , -8 / Jannabi.PPM));
        fdef.shape = rightHitBox;
        fdef.isSensor = true;
        //b2body.createFixture(fdef).setUserData(this);
        b2body.createFixture(fdef);

        //create belowHitBox
        EdgeShape belowHitBox = new EdgeShape();
        belowHitBox.set(new Vector2(-6.5f / Jannabi.PPM,-8 / Jannabi.PPM),new Vector2(6.5f / Jannabi.PPM , -8 / Jannabi.PPM));
        fdef.shape = belowHitBox;
        fdef.isSensor = true;
        //b2body.createFixture(fdef).setUserData(this);
        b2body.createFixture(fdef);*/


    }
    //fire method for pistol gun (temporary)
    public void fire(){
        if(currentAmmo > 0){
            if(reloaded){
                pistolsBullet.clear();
                Gdx.app.log("ammo","reload complete");
                allAmmo -= pistolClip;
                currentAmmo = pistolClip;
                reloaded = false;
                Gdx.app.log("ammo = "+allAmmo,"ammoleft ");
            }
            pistolsBullet.add(new pistol(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, aimUp ? true:false,
                    aimDown ? true : false,20));
            currentAmmo--;
        }else if(allAmmo > 0){
            //clink sound
            Gdx.app.log("ammo","need to reload");
            //reload
            if(reloaded){
                pistolsBullet.clear();
                Gdx.app.log("ammo","reload complete");
                allAmmo -= pistolClip;
                currentAmmo = pistolClip;
                reloaded = false;
                Gdx.app.log("ammo = "+allAmmo,"ammoleft ");
            }

        }else if(currentAmmo <= 0 && allAmmo <= 00){
            //clinksound
            pistolsBullet.clear();
            Gdx.app.log("ammo out","no bullet left");
        }

    }
    //draw each bullet
    public void draw(Batch batch){
        super.draw(batch);
        for(pistol bullet:pistolsBullet){
            bullet.draw(batch);
        }
    }

    public void getHit(){
        hp--;
        beenHit = true;
        //push back when hit
        //b2body.applyLinearImpulse(new Vector2(100,0),b2body.getWorldCenter(),true);
        //next is die stage

    }



    public int getHp() {
        return hp;
    }
}
