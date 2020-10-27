package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Weapon.*;
import com.mygdx.game.tools.LoadTexture;

//this class is create for create main player create box2d sprite and further
public class Player extends Sprite {

    //enum for checkState
    public enum State {FALLING,JUMPING,STANDING,RUNNING,STAND_AIM_UP,STAND_AIM_DOWN,RUNNING_AIM_UP,RUNNING_AIM_DOWN,
                        JUMP_AIM_UP,JUMP_AIM_DOWN,RELOAD,DEAD,GETHIT,SWORD_ATTACK};

    public enum GunState {SWORD,PISTOL,SMG,SHOTGUN}
    public GunState curGunState;

    //implement state to check current and previous
    public State currentState;
    public State previousState;


    //implement box2d
    public World world;
    public Body b2body;

    //implement StateTime for something(don't Know yet) and boolean check that we running right or not
    private float stateTimer;
    private boolean runningRight;
    private boolean aimUp;
    private boolean aimDown;
    PlayScreen screen;
    //pistol shot array
    private Array<Gun> Bullet;
    private int lastPistolMag;
    private int lastSmgMag;
    private int lastShotgunMag;
    private boolean duplicatedChange;

    //hit count of our main character
    private int hp;
    //use for display hit anim
    private boolean beenHit;

    private boolean swordAttack;
    private float swordAttackTime;

    private float animateDelay;

    //float for  reload delayTime
    private float reloadTime;
    private boolean duplicateReloadCheck;

    //variable for firerate
    private float fireDelay;
    private boolean firstShot;

    private int currentAmmo;
    private int allAmmo;
    private boolean reloaded;
    private LoadTexture loader;

    protected Sword sword;
    protected boolean swordDefined;

    //create Constructor
    public Player( PlayScreen screen){
        //get start image in .pack
        //super(screen.getAtlas().findRegion("stand_aim"));
        super(screen.getAtlas().findRegion("playerStand"));
        this.screen = screen;
        this.world = screen.getWorld();

        //set all necessary State
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        curGunState = GunState.SHOTGUN;

        //all boolean and vaule for animation
        runningRight = true;
        aimUp = false;
        aimDown = false;
        hp = 10;
        beenHit = false;
        reloaded = false;
        animateDelay = 0;

        reloadTime = 0;
        duplicateReloadCheck = false;

        //construc for sword var
        swordAttack = false;
        swordAttackTime = 0;

        //ini for fireDelay
        fireDelay = 0;
        firstShot = false;

        //create this class for shorter and easy access for load all player texture
        loader = new LoadTexture(screen);


        //use to check collision and create box2d body
        definePlayer();

        //link sprite to box2d
        setBounds(0,0,32 / Jannabi.PPM,32 / Jannabi.PPM);

        Bullet = new Array<>();
        currentAmmo = 0;
        allAmmo = 52;

        //check bullet in different gun
        lastPistolMag = Jannabi.PISTOL_CLIP;
        lastSmgMag = Jannabi.SMG_CLIP;
        lastShotgunMag = Jannabi.SHOTGUN_CLIP;
        duplicatedChange = false;

        sword = new Sword(screen,true,50);
        swordDefined = false;


    }


    public void update(float dt){

        //set position of sprite here
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 3.5f);
        if(beenHit){
            animateDelay += dt;
            setRegion(loader.getPlayerGetHitRegion(curGunState));
            if(animateDelay > 0.125f){

                beenHit = false;
                animateDelay = 0;
            }
        }else{
            setRegion(getFrame(dt));
        }
        for(Gun bullet : Bullet) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                Bullet.removeValue(bullet, true);
        }
        //Gdx.app.log("sword boolean","" + swordAttack);

        if(swordAttack){
            swordAttackTime += dt;
        }
        if(swordDefined){
            sword.update(dt,b2body.getPosition().x,b2body.getPosition().y);
        }


    }

    //checkState
    private TextureRegion getFrame(float dt){
        currentState = getState(dt);
        TextureRegion region;

        region = loader.getRegion(currentState,curGunState,stateTimer);


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
    private State getState(float dt){
        if(hp <= 0){
            return State.DEAD;
        }else if(beenHit){
            beenHit = false;
            return State.GETHIT;
        }else if(swordAttack){
            if(swordAttackTime > 0.3f){
                swordAttack = false;
                sword.destroy();
                swordDefined = false;
                swordAttackTime =0;
            }
            return State.SWORD_ATTACK;
        }else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                aimUp = true;
                if(curGunState == GunState.SWORD ){
                    return State.JUMPING;
                }else{
                    return State.JUMP_AIM_UP;
                }

            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                aimDown = true;
                if(curGunState == GunState.SWORD ){
                    return State.JUMPING;
                }else{
                    return State.JUMP_AIM_DOWN;
                }
            }else {
                aimUp = false;
                aimDown = false;
                return State.JUMPING;
            }
        }else if(b2body.getLinearVelocity().y < 0){
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                aimUp = true;
                if(curGunState == GunState.SWORD ){
                    return State.JUMPING;
                }else{
                    return State.JUMP_AIM_UP;
                }
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                aimDown = true;
                if(curGunState == GunState.SWORD ){
                    return State.JUMPING;
                }else{
                    return State.JUMP_AIM_DOWN;
                }

            }else {
                aimUp = false;
                aimDown = false;
                return State.FALLING;
            }
        }else if(b2body.getLinearVelocity().x != 0){
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                aimUp = true;
                if(curGunState == GunState.SWORD){
                    return State.RUNNING;
                }else{
                    return State.RUNNING_AIM_UP;
                }

            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                aimDown = true;
                if(curGunState == GunState.SWORD){
                    return State.RUNNING;
                }else{
                    return State.RUNNING_AIM_DOWN;
                }

            }else{
                aimUp = false;
                aimDown = false;
                return State.RUNNING;
            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            aimUp = true;
            if(curGunState == GunState.SWORD){
                return State.STANDING;
            }else{
                return State.STAND_AIM_UP;
            }

        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            aimDown = true;
            if(curGunState == GunState.SWORD){
                return State.STANDING;
            }else{
                return State.STAND_AIM_DOWN;
            }

        }else if(Gdx.input.isKeyPressed(Input.Keys.F)){
            aimUp = false;
            aimDown = false;
            reloadTime += dt;
            if(allAmmo > 0){
                if(curGunState == GunState.SWORD){
                    return State.STANDING;
                }else{
                    if(reloadTime > 0.6f && !duplicateReloadCheck){
                        duplicateReloadCheck = true;
                        reloaded = true;
                        reload();
                        Gdx.app.log("reloadcomplete","");
                    }

                    return State.RELOAD;
                }
            }else{
                return State.STANDING;
            }



        }else{
            aimUp = false;
            aimDown = false;
            // reloadtime and duplicate is use to prevent when user hold reload key
            reloadTime = 0;
            duplicateReloadCheck = false;
            return State.STANDING;
        }
    }


    //create box2d and set fixture
    private void definePlayer(){
        BodyDef bdef = new BodyDef();
        //set box2d our animation now have width32 and height 32
        bdef.position.set(32 / Jannabi.PPM,32 / Jannabi.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //create fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Jannabi.PPM);

        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox(16,32);

        //set category bit
        fdef.filter.categoryBits = Jannabi.JANNABI_BIT;
        //what our mainPlayer can collide with
        fdef.filter.maskBits = Jannabi.DEFAULT_BIT | Jannabi.OTHERLAYER_BIT | Jannabi.ENEMY_BIT | Jannabi.Edge_BIT | Jannabi.ITEM_BIT;

        fdef.shape = shape;
        //fdef.isSensor = false;//this sensor use for jumping through
        b2body.createFixture(fdef).setUserData(this);
        //b2body.createFixture(fdef);




    }
    public void attack(float dt){
        if(curGunState != GunState.SWORD){
            fire(dt);

        }else{
            //sword method
            if(!swordDefined) swing(dt);
            //sword.definedBullet(b2body.getPosition().x,b2body.getPosition().y,runningRight ? true : false);


        }
    }

    //swing method for sword
    private void swing(float dt){
        //maybe check sword radius
        sword.definedBullet(b2body.getPosition().x,b2body.getPosition().y,runningRight ? true : false,b2body.getLinearVelocity().x,b2body.getLinearVelocity().y);
        swordAttack = true;
        swordDefined = true;

    }

    //fire method
    private void fire(float dt){
        if(currentAmmo > 0){
            fireDelay += dt;
            if(curGunState == GunState.PISTOL){
                if(fireDelay > Jannabi.PISTOL_FIRE_RATE){
                    Bullet.add(new Pistol(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, aimUp ? true:false,
                            aimDown ? true : false,20,Jannabi.PISTOL_CLIP));
                    currentAmmo--;
                    fireDelay = 0;
                }else if(!firstShot){
                    Bullet.add(new Pistol(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, aimUp ? true:false,
                            aimDown ? true : false,20,Jannabi.PISTOL_CLIP));
                    currentAmmo--;
                    firstShot = true;

                }

            }else if(curGunState == GunState.SMG){
                if(fireDelay > Jannabi.SMG_FIRE_RATE){
                    Bullet.add(new Smg(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, aimUp ? true:false,
                            aimDown ? true : false,10,Jannabi.SMG_CLIP));
                    currentAmmo--;
                    fireDelay = 0;
                }else if(!firstShot){
                    Bullet.add(new Smg(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, aimUp ? true:false,
                            aimDown ? true : false,10,Jannabi.SMG_CLIP));
                    currentAmmo--;
                    firstShot = true;
                }

            }else if(curGunState == GunState.SHOTGUN){
                if(fireDelay > Jannabi.SHOTGUN_FIRE_RATE){
                    Bullet.add(new Shotgun(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, aimUp ? true:false,
                            aimDown ? true : false,10,Jannabi.SHOTGUN_CLIP));
                    currentAmmo--;
                    fireDelay = 0;
                }else if(!firstShot){
                    Bullet.add(new Shotgun(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, aimUp ? true:false,
                            aimDown ? true : false,10,Jannabi.SHOTGUN_CLIP));
                    currentAmmo--;
                    firstShot = true;
                }
            }

        }else if(allAmmo > 0){
            //clink sound
            Gdx.app.log("ammo","need to reload");


        }else if(allAmmo <= 0){
            //clinksound
            Bullet.clear();
            Gdx.app.log("ammo out","no bullet left");
        }

    }

    public boolean isBeenHit(){
        return beenHit;
    }
    private void reload(){
        //reload
        if(reloaded && allAmmo > 0){
            Bullet.clear();
            Gdx.app.log("ammo","reload complete");
            //need to change when different gunState
            allAmmo -= getClip();
            currentAmmo = getClip();
            reloaded = false;
            Gdx.app.log("ammo = "+allAmmo,"ammoleft ");
        }
    }


    private short getClip(){
        switch (curGunState){
            case PISTOL:
                return Jannabi.PISTOL_CLIP;
            case SMG:
                return Jannabi.SMG_CLIP;
            case SHOTGUN:
                return Jannabi.SHOTGUN_CLIP;
            default:
                return 0;
        }
    }
    //draw each bullet
    public void draw(Batch batch){
        super.draw(batch);
        for(Gun bullet: Bullet){
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

    public void getPotion(){
        hp+= 3;
        Gdx.app.log("hp Up","cur hp is" + hp);
    }

    public int getHp() {
        return hp;
    }

    public void changeWeapon(String weapon){
        switch (weapon){
            case "sword":
                curGunState = GunState.SWORD;
                Gdx.app.log("change to sword","");
                break;
            case "pistol":
                if(!duplicatedChange){
                    setLastMag(currentAmmo);
                    curGunState = GunState.PISTOL;
                    currentAmmo = lastPistolMag;
                }
                duplicatedChange = true;

                Gdx.app.log("change to pistol","");

                break;
            case "smg":
                if(!duplicatedChange){
                    setLastMag(currentAmmo);
                    curGunState = GunState.SMG;
                    currentAmmo = lastSmgMag;
                }
                duplicatedChange = true;
                Gdx.app.log("change to smg","");
                break;
            default:
                if(!duplicatedChange){
                    setLastMag(currentAmmo);
                    curGunState = GunState.SHOTGUN;
                    currentAmmo = lastShotgunMag;
                }
                duplicatedChange = true;
                Gdx.app.log("change to shotgun","");
        }
    }

    public void setLastMag(int lastMag){
        switch (curGunState){
            case PISTOL:
                lastPistolMag = lastMag;
                Gdx.app.log("set last mag to",""+lastMag);
                break;
            case SMG:
                lastSmgMag = lastMag;
                Gdx.app.log("set last mag to",""+lastMag);
                break;
            case SHOTGUN:
                lastShotgunMag = lastMag;
                Gdx.app.log("set last mag to",""+lastMag);
                break;
        }
    }

    public void setFirstShot(boolean firstShot) {
        this.firstShot = firstShot;
    }

    public void setDuplicatedChange(boolean change){
        duplicatedChange = change;
    }

    public int getAllAmmo(){
        return allAmmo;
    }

    public int getAmmo(){
        return currentAmmo;
    }
}
