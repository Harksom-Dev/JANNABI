package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Animation<TextureRegion>[] playerGetHit;
    private Animation<TextureRegion>[] playerRun;
    private Animation<TextureRegion>[] playerRunAimUp;
    private Animation<TextureRegion>[] playerRunAimDown;
    private Animation<TextureRegion>[] playerReload;

    public LoadTexture(PlayScreen screen){
        loadRegion(screen,32,32);
        loadAnimation(screen,32,32);

    }

    private void loadRegion(PlayScreen screen,int width,int height){

        //get jump Texture
        //playerJump[0] = new TextureRegion(screen.getAtlas().findRegion("jump_aim"),64,0,width,height);
        individualRegionLoad(screen,playerJump,0,"jump_aim",0,0,width,height);              //sword jump
        individualRegionLoad(screen,playerJump,1,"jump_aim",64,0,width,height);             //pistol jump
        individualRegionLoad(screen,playerJump,2,"jump_aim",64,0,width,height);             //smg jump
        individualRegionLoad(screen,playerJump,3,"jump_aim",64,0,width,height);             //shotgun jump


        //get stand aim texture
        //aim up
        playerStandAimUp[0] = new TextureRegion(screen.getAtlas().findRegion("stand_aim"),64,0,width,height);
        //aim down
        playerStandAimDown[0] = new TextureRegion(screen.getAtlas().findRegion("stand_aim"),96,0,width,height);

        //get jump & aim
        //jump & aim up
        playerJumAimUp[0] = new TextureRegion(screen.getAtlas().findRegion("jump_aim"),0,0,width,height);
        //jump & aim down
        playerJumAimDown[0] = new TextureRegion(screen.getAtlas().findRegion("jump_aim"),32,0,width,height);


    }

    private void individualRegionLoad(PlayScreen screen,TextureRegion[] regions,int index,String textureName,int startX,int startY,int width , int height){
        regions[index] = new TextureRegion(screen.getAtlas().findRegion(textureName),startX,startY,width,height);
    }

    private void loadAnimation(PlayScreen screen,int width,int height){
        com.badlogic.gdx.utils.Array<TextureRegion> frames = new Array<TextureRegion>();
        //loop for get runAnimation
        for(int i = 1; i < 4 ; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("pistol_run"),i*32,0,width,height));
        }
        playerRun[0] = new Animation<TextureRegion>(0.12f,frames);
        frames.clear();

        //get stand texture
        for(int i = 0;i < 2;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("stand_aim"),i*32,0,width,height));
        }
        playerStand[0] = new Animation<TextureRegion>(0.5f,frames);
        frames.clear();

        //get aim&run
        //aimUp&run
        for(int i = 0;i < 4;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("run_aim"),i*32,0,width,height));
        }
        playerRunAimUp[0] = new Animation<TextureRegion>(0.12f,frames);
        frames.clear();
        //aimDown&run
        for(int i = 4;i < 8;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("run_aim"),i*32,0,width,height));
        }
        playerRunAimDown[0] = new Animation<TextureRegion>(0.12f,frames);
        frames.clear();

        //get reload animation
        for(int i = 0;i < 5;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("normal_reload"),i*32,0,width,height));

        }
        frames.add(new TextureRegion(screen.getAtlas().findRegion("stand_aim"),0,0,width,height));
        playerReload[0] = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        //get hit animation
        //playerPistolGetHit = new TextureRegion(screen.getAtlas().findRegion("pistol_gethit"),0,0,32,32);
        frames.add(new TextureRegion(screen.getAtlas().findRegion("pistol_gethit"),0,0,width,height));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("pistol_gethit"),0,0,width,height));
        playerGetHit[0] = new Animation<TextureRegion>(0.5f,frames);
    }
}
