package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class LoadTexture extends Sprite {
    private Animation<TextureRegion>[] playerStand;
    private TextureRegion[] playerJump;
    private TextureRegion[] playerStandAimUp;
    private TextureRegion[] playerStandAimDown;
    private TextureRegion[] playerJumAimUp;
    private TextureRegion[] playerJumAimDown;
    private TextureRegion[] playerGetHit;
    private Animation<TextureRegion>[] playerRun;
    private Animation<TextureRegion>[] playerRunAimUp;
    private Animation<TextureRegion>[] playerRunAimDown;
    private Animation<TextureRegion>[] playerReload;


    public LoadTexture(PlayScreen screen){
        //atlas = new TextureAtlas("Sprite/allCharacter/newCharacterpack.pack");
        loadRegion(screen);
        loadAnimation(screen,32,32);
    }

    private void loadRegion(PlayScreen screen){

        //get jump Texture
        playerJump= individualRegionLoad(screen, 4,"playerJump",new Vector2(32,0));


        //get stand aim texture
        //aim up
        playerStandAimUp = individualRegionLoad(screen, 4,"playerStandAimUp",new Vector2(32,0));
        //aim down
        playerStandAimDown = individualRegionLoad(screen, 4,"playerStandAimDown",new Vector2(32,0));

        //get jump & aim
        //jump & aim up
        playerJumAimUp = individualRegionLoad(screen, 4,"playerJumpAimUp",new Vector2(32,0));
        //jump & aim down
        playerJumAimDown = individualRegionLoad(screen, 4,"playerJumpAimDown",new Vector2(32,0));

        //gethit
        playerGetHit = individualRegionLoad(screen, 4,"playerGetHit",new Vector2(32,0));

    }

    private TextureRegion[] individualRegionLoad(PlayScreen screen, int index, String textureName, Vector2 position){
        TextureRegion[] regions = new TextureRegion[index];
        for(int i =0;i < index; i++){
            regions[i] = new TextureRegion(screen.getAtlas().findRegion(textureName),(int)position.x * i, (int) position.y, 32, 32);
        }
        return regions;
    }

    private Animation<TextureRegion>[] individualAnimationLoad(PlayScreen screen, int framesCount
            , int index, String textureName, Vector2 position, int width, int height,float frameDuration){
        Animation<TextureRegion>[] animations = new Animation[index];
        com.badlogic.gdx.utils.Array<TextureRegion> frames = new Array<TextureRegion>();
        int framePerAnimate = framesCount;
        int currentFrame = 0;
        for(int i = 0;i < index;i++){
            for(int j = currentFrame; j < framePerAnimate;j++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion(textureName),(int)position.x * j,(int)position.y,width,height));
                currentFrame++;
            }
            framePerAnimate += framesCount;
            animations[i] = new Animation<TextureRegion>(frameDuration,frames);
            frames.clear();

        }
        return animations;
    }

    private void loadAnimation(PlayScreen screen,int width,int height){
        com.badlogic.gdx.utils.Array<TextureRegion> frames = new Array<TextureRegion>();
        //loop for get runAnimation
        playerRun = individualAnimationLoad(screen, 4,4,"playerRun",new Vector2(32,0),32,32,0.12f);

        //get stand texture
        playerStand = individualAnimationLoad(screen, 2,4,"playerStand",new Vector2(32,0),32,32,0.5f);
        //get aim&run
        //aimUp&run
        playerRunAimUp = individualAnimationLoad(screen, 4,3,"playerRunAimUp",new Vector2(32,0),32,32,0.12f);
        //aimDown&run
        playerRunAimDown = individualAnimationLoad(screen, 4,3,"playerRunAimDown",new Vector2(32,0),32,32,0.12f);

        //get reload animation
        playerReload = individualAnimationLoad(screen, 4,3,"playerReload",new Vector2(32,0),32,32,0.1f);
    }
    //getter
    public TextureRegion getRegion(Player.State state, Player.GunState gunState, float stateTimer){

        int index = 0;
        TextureRegion region;
        switch (gunState){
            case SWORD:
                index = 0;
                break;
            case PISTOL:
                index = 1;
                break;
            case SMG:
                index = 2;
                break;
            case SHOTGUN:
                index = 3;
            default:
                break;
        }
        switch (state){
            case JUMPING:
                region = playerJump[index];
                break;
            case JUMP_AIM_UP:
                region = playerJumAimUp[index];
                break;
            case JUMP_AIM_DOWN:
                region = playerJumAimDown[index];
                break;
            case RUNNING:
                region = playerRun[index].getKeyFrame(stateTimer,true);
                break;
            case RUNNING_AIM_UP:
                region = playerRunAimUp[index-1].getKeyFrame(stateTimer,true);
                break;
            case RUNNING_AIM_DOWN:
                region = playerRunAimDown[index-1].getKeyFrame(stateTimer,true);
                break;
            case STAND_AIM_UP:
                region = playerStandAimUp[index];
                break;
            case STAND_AIM_DOWN:
                region = playerStandAimDown[index];
                break;
            case RELOAD:
                region = playerReload[index-1].getKeyFrame(stateTimer);
                break;
            case GETHIT:
            case DEAD:
                region = playerGetHit[index];
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand[index].getKeyFrame(stateTimer,true);
                break;
        }
        return region;
    }
    //getter
    public TextureRegion getIndividualRegion(Player.GunState gunState){
        int index = 0;
        TextureRegion region;
        switch (gunState){
            case SWORD:
                index = 0;
                break;
            case PISTOL:
                index = 1;
                break;
            case SMG:
                index = 2;
                break;
            case SHOTGUN:
                index = 3;
            default:
                break;
        }

        return playerGetHit[index];
    }

}
