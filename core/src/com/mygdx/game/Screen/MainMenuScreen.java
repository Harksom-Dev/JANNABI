package com.mygdx.game.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;
import org.w3c.dom.Text;

public class MainMenuScreen implements Screen {

    Jannabi game;
    private static final int START_BUTTON_WIDTH = 88 ;
    private static final int START_BUTTON_HEIGHT = 356;
    private static final int ABOUT_BUTTON_WIDTH = 88 ;
    private static final int ABOUT_BUTTON_HEIGHT = 262 ;
    private static final int EXIT_BUTTON_WIDTH = 88 ;
    private static final int EXIT_BUTTON_HEIGHT = 212 ;
    private static final int HOW_BUTTON_WIDTE = 88 ;
    private static final int HOW_BUTTON_HEIGHT = 307;
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    private static final int MUTE_BUTTON_WIDTH = 1207;
    private static final int MUTE_BUTTON_HEIGHT = 31;

    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera cam;;
    private Music music , sound;
    private float loadingScreen;
    private boolean mutepress ;
    private float loadingTime;




    Texture MainMenuWallpaper;
    Texture PlayButtonActive;
    Texture PlayButtonInactive;
    Texture HowtoplayActive;
    Texture HowtoplayInactive;
    Texture AboutButtonActive;
    Texture AboutButtonInactive;
    Texture ExitButtonActive;
    Texture ExitButtonInactive;
    Texture MuteButton;
    Texture UnmuteButton;

    public MainMenuScreen (Jannabi game){
        this.game = game;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,((Jannabi) game).batch);
        cam = new OrthographicCamera();

        PlayButtonActive = new Texture("MainMenu/StartActive.png");
        PlayButtonInactive = new Texture("MainMenu/StartInactive.png");
        AboutButtonActive = new Texture("MainMenu/AboutActive.png");
        AboutButtonInactive = new Texture("MainMenu/AboutInactive.png");
        ExitButtonActive = new Texture("MainMenu/ExitActive.png");
        ExitButtonInactive = new Texture("MainMenu/ExitInactive.png");
        HowtoplayActive = new Texture("MainMenu/HowtoplayActive.png");
        HowtoplayInactive = new Texture("MainMenu/HowtoplayInactive.png");
        MainMenuWallpaper = new Texture("MainMenu/MainWallpaper.png");
        UnmuteButton = new Texture("MainMenu/unmute.png");
        MuteButton = new Texture("MainMenu/mute.png");
        //music background;
        music = Jannabi.manager.get("Audio/Music/MainMenu.mp3", Music.class);
        music.setLooping(true);
        music.play();


    }

    @Override
    public void show() {

    }

    /*public boolean isMutepress(){
        *//*if (mutepress == true){ return false; }
        else { return true; }*//*
    }
    public void press() {
        if (Gdx.input.getX() > 1207 && Gdx.input.getX() < 1257 && Gdx.input.getY() > 30 && Gdx.input.getY() < 80) {
            if (Gdx.input.justTouched()) {
                if (mutepress == true) {
                    music.play();
                    mutepress = false;
                } else {
                    music.pause();
                    mutepress = true;
                }
            } else {
            }
        } else {
        }
    }


    public void muteMusic() {
        if (mutepress == false){
        game.batch.draw(UnmuteButton,MUTE_BUTTON_WIDTH,MUTE_BUTTON_HEIGHT);}
        else{game.batch.draw(MuteButton,MUTE_BUTTON_WIDTH,MUTE_BUTTON_HEIGHT);}
    }*/
    public void touchButtonSound(){ Jannabi.manager.get("Audio/Sound/main/isTouchButton.mp3", Sound.class).play(); }
    public  void isClicked(){ Jannabi.manager.get("Audio/Sound/main/selected.mp3", Sound.class).play(); }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        game.batch.begin();
        game.batch.draw(MainMenuWallpaper,0,0);

        /// START GAME
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 327&& Gdx.input.getY() < 364) {
            game.batch.draw(PlayButtonActive, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
           touchButtonSound();
            if (Gdx.input.isTouched()){
                isClicked();
                game.setScreen(new LoadingScreen(game));
                this.dispose();
                game.batch.end();
                return;
            }
        } else {game.batch.draw(PlayButtonInactive, START_BUTTON_WIDTH, START_BUTTON_HEIGHT); }

        /// HOW TO PLAY
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 373&& Gdx.input.getY() < 410) {
            game.batch.draw(HowtoplayActive, HOW_BUTTON_WIDTE, HOW_BUTTON_HEIGHT);
            touchButtonSound();
            if (Gdx.input.justTouched()){
                isClicked();
                this.dispose();
                game.batch.end();
                game.setScreen(new HowToPlay(game));
                return;
            }
        } else {game.batch.draw(HowtoplayInactive, HOW_BUTTON_WIDTE, HOW_BUTTON_HEIGHT);}

        ///ABOUT
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 420 && Gdx.input.getY() < 460) {
            touchButtonSound();
            game.batch.draw(AboutButtonActive,ABOUT_BUTTON_WIDTH,ABOUT_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()){
                isClicked();
                this.dispose();
                game.batch.end();
                game.setScreen(new Credits(game));
                return;
            }
        } else {game.batch.draw(AboutButtonInactive,ABOUT_BUTTON_WIDTH,ABOUT_BUTTON_HEIGHT);}

        /// EXIT
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 470 && Gdx.input.getY() < 510) {
            game.batch.draw(ExitButtonActive,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
            touchButtonSound();
            if (Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {game.batch.draw(ExitButtonInactive,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);}

        /// MUTE BUTTON
        //muteMusic();

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        music.dispose();
    }
}

