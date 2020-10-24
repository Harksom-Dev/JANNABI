package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    private TextureAtlas atlas;

    public LoadTexture(PlayScreen screen){
        atlas = new TextureAtlas("Sprite/allCharacter/newCharacterpack.pack");
        loadRegion(screen,32,32);
        loadAnimation(screen,32,32);
    }

    private void loadRegion(PlayScreen screen,int width,int height){

        //get jump Texture
        individualRegionLoad(screen,playerJump,4,"playerJump",new Vector2(32,0),32,32);


        //get stand aim texture
        //aim up
        individualRegionLoad(screen,playerStandAimUp,4,"playerStandAimUp",new Vector2(32,0),32,32);
        //aim down
        individualRegionLoad(screen,playerStandAimDown,4,"playerStandAimDown",new Vector2(32,0),32,32);

        //get jump & aim
        //jump & aim up
        individualRegionLoad(screen,playerJumAimUp,4,"playerJumpAimUp",new Vector2(32,0),32,32);
        //jump & aim down
        individualRegionLoad(screen,playerJumAimDown,4,"playerJumpAimDown",new Vector2(32,0),32,32);

        //gethit
        individualRegionLoad(screen,playerGetHit,4,"playerGetHit",new Vector2(32,0),32,32);

    }

    private void individualRegionLoad(PlayScreen screen,TextureRegion[] regions,int index,String textureName,Vector2 position,int width , int height){
        regions = new TextureRegion[index];
        for(int i =0;i < index; i++){
            regions[i] = new TextureRegion(atlas.findRegion(textureName),(int)position.x * i, (int) position.y,width,height);
        }

    }

    private void individualAnimationLoad(PlayScreen screen, Animation<TextureRegion>[] animations,int framesCount
            ,int index, String textureName, Vector2 position,int width,int height){
        animations = new Animation[index];
        com.badlogic.gdx.utils.Array<TextureRegion> frames = new Array<TextureRegion>();
        int framePerAnimate = framesCount;
        int currentFrame = 0;
        for(int i = 0;i < index;i++){
            for(int j = currentFrame; j < framePerAnimate;j++){
                frames.add(new TextureRegion(atlas.findRegion(textureName),(int)position.x * j,(int)position.y,width,height));
                currentFrame++;
            }
            framePerAnimate += framesCount;
            animations[i] = new Animation<TextureRegion>(0.12f,frames);
            frames.clear();
        }
    }

    private void loadAnimation(PlayScreen screen,int width,int height){
        com.badlogic.gdx.utils.Array<TextureRegion> frames = new Array<TextureRegion>();
        //loop for get runAnimation
        individualAnimationLoad(screen,playerRun,4,4,"playerRun",new Vector2(32,0),32,32);

        //get stand texture
        individualAnimationLoad(screen,playerStand,2,2,"playerStand",new Vector2(32,0),32,32);

        //get aim&run
        //aimUp&run
        individualAnimationLoad(screen,playerRunAimUp,4,4,"playerRunAimUp",new Vector2(32,0),32,32);
        //aimDown&run
        individualAnimationLoad(screen,playerRunAimDown,4,4,"playerRunAimDown",new Vector2(32,0),32,32);

        //get reload animation
        individualAnimationLoad(screen,playerReload,4,4,"playerReload",new Vector2(32,0),32,32);

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
                if(index == 0){
                    Gdx.app.log("Weapons","Sword!!!!");
                }else{
                    Gdx.app.log("Weapons","Something");
                }
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
                region = playerRunAimUp[index].getKeyFrame(stateTimer,true);
                break;
            case RUNNING_AIM_DOWN:
                region = playerRunAimDown[index].getKeyFrame(stateTimer,true);
                break;
            case STAND_AIM_UP:
                region = playerStandAimUp[index];
                break;
            case STAND_AIM_DOWN:
                region = playerStandAimDown[index];
                break;
            case RELOAD:
                region = playerReload[index].getKeyFrame(stateTimer);
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
    public Animation<TextureRegion> getAnimation(){
        return null;
    }

}
