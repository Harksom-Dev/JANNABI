package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;

import java.awt.*;

public class GameOverScreen implements Screen {

    private static final int TRYAGAIN_BUTTON_WIDTH = 330 ;
    private static final int TRYAGAIN_BUTTON_HEIGHT = 148 ;
    private static final int MAINMENU_BUTTON_WIDTH = 544 ;
    private static final int MAINMENU_BUTTON_HEIGHT = 148;
    private static final int QUIT_BUTTON_WIDTH = 759 ;
    private static final int QUIT_BUTTON_HEIGHT = 148 ;
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera cam;
    private Music music;



    Jannabi game ;
    Texture GameOverWallpaper ;
    Texture TryAgainButtonActive;
    Texture TryAgainButtonInactive;
    Texture MainMenuButtonActive;
    Texture MainMenuButtonInactive;
    Texture QuitButtonActive;
    Texture QuitButtonInactive;
    Texture Test;

    public GameOverScreen(Jannabi game){

        this.game = game;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,((Jannabi) game).batch);
        cam = new OrthographicCamera();

        Gdx.app.log("Now i'm in GameOverScreen","Pass");

        GameOverWallpaper = new Texture("GameOver/wallaper.png");
        TryAgainButtonActive = new Texture("GameOver/TryAgainActive.png");
        TryAgainButtonInactive = new Texture("GameOver/TryAgainInactive.png");
        MainMenuButtonActive = new Texture("GameOver/MainMenuActive.png");
        MainMenuButtonInactive = new Texture("GameOver/MainMenuInactive.png");
        QuitButtonActive = new Texture("GameOver/QuitActive.png");
        QuitButtonInactive = new Texture("GameOver/QuitInactive.png");
        Test = new Texture("MainMenu/MainWallpaper.png");
        /*music = Jannabi.manager.get("Audio/Music/GameOverMusic.mp3",Music.class);
        music.setLooping(true);
        music.play();*/
    }


    @Override
    public void show() {
       // stage = new Stage(viewport,((Jannabi) game).batch);
    }

    @Override
    public void render(float delta) {
        //cam.setToOrtho(true, 136, 720);
        Gdx.app.log("Now I'm in Render Game Over Screen","Pass");
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        game.batch.begin();
        Gdx.app.log("Now I'm in Render AT DRAW","WHY NOT DRAW");
        game.batch.draw(GameOverWallpaper,0,0);
        System.out.println("Iam gonna drawing TEST");
        //game.batch.draw(Test,0,0);
            // TRYAGAIN
        if (Gdx.input.getX() > 330 && Gdx.input.getX() < 470 && Gdx.input.getY() > 530 && Gdx.input.getY() <573) {
            game.batch.draw(TryAgainButtonActive, TRYAGAIN_BUTTON_WIDTH, TRYAGAIN_BUTTON_HEIGHT);
            Gdx.app.log("Now I'm in MAINMENU BUTT","Pass");
                if (Gdx.input.isTouched()){
                    Gdx.app.log("Now I'm in TRYAGAIN BUTT","Pass");
                    this.dispose();
                    game.setScreen(new PlayScreen(game));
                    game.batch.end();
                    return;
                }
        }else {
            game.batch.draw(TryAgainButtonInactive, TRYAGAIN_BUTTON_WIDTH, TRYAGAIN_BUTTON_HEIGHT);}
            // END_OF_TRYAGAIN

            //MAINMENU
        if (Gdx.input.getX() > 545 && Gdx.input.getX() < 735 && Gdx.input.getY() > 530 && Gdx.input.getY() <573){
                game.batch.draw(MainMenuButtonActive, MAINMENU_BUTTON_WIDTH, MAINMENU_BUTTON_HEIGHT);
                if (Gdx.input.isTouched()){
                    Gdx.app.log("Now I'm in MAINMENU BUTT","Pass");
                    this.dispose();
                    game.batch.end();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
        }else {
            game.batch.draw(MainMenuButtonInactive, MAINMENU_BUTTON_WIDTH, MAINMENU_BUTTON_HEIGHT);}
            //END_OF_MAINMENU
            //QUIT
        if (Gdx.input.getX() > 759 && Gdx.input.getX() < 948 && Gdx.input.getY() > 530 && Gdx.input.getY() <573){
            game.batch.draw(QuitButtonActive, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
                if (Gdx.input.isTouched()) {
                    Gdx.app.exit();
                }
        } else { game.batch.draw(QuitButtonInactive, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
                            }
            //END_OF_QUIT
        Gdx.app.log("Now I'm at the End of Render","Pass");
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //music.dispose();

    }
}
