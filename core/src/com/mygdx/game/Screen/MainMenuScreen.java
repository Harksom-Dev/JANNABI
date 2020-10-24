package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Jannabi;

public class MainMenuScreen implements Screen {

    private static final int START_BUTTON_WIDTH = 88 ;
    private static final int START_BUTTON_HEIGHT = 356;
    //870
    private static final int ABOUT_BUTTON_WIDTH = 88 ;
    private static final int ABOUT_BUTTON_HEIGHT = 262 ;
    private static final int EXIT_BUTTON_WIDTH = 88 ;
    private static final int EXIT_BUTTON_HEIGHT = 212 ;
    private static final int HOW_BUTTON_WIDTE = 88 ;
    private static final int HOW_BUTTON_HEIGHT = 307;

    Jannabi game;

    Texture MainMenuWallpaper;
    Texture PlayButtonActive;
    Texture PlayButtonInactive;
    Texture HowtoplayActive;
    Texture HowtoplayInactive;
    Texture AboutButtonActive;
    Texture AboutButtonInactive;
    Texture ExitButtonActive;
    Texture ExitButtonInactive;

    public MainMenuScreen (Jannabi game){
        this.game = game;

        PlayButtonActive = new Texture("MainMenu/StartActive.png");
        PlayButtonInactive = new Texture("MainMenu/StartInactive.png");
        AboutButtonActive = new Texture("MainMenu/AboutActive.png");
        AboutButtonInactive = new Texture("MainMenu/AboutInactive.png");
        ExitButtonActive = new Texture("MainMenu/ExitActive.png");
        ExitButtonInactive = new Texture("MainMenu/ExitInactive.png");
        HowtoplayActive = new Texture("MainMenu/HowtoplayActive.png");
        HowtoplayInactive = new Texture("MainMenu/HowtoplayInactive.png");
        MainMenuWallpaper = new Texture("MainMenu/MainWallpaper.png");
        //MainMenuWallpaper = new Texture("MainMenu/giphy.gif");

    }




    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(MainMenuWallpaper,0,0);
        /// START GAME
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 327&& Gdx.input.getY() < 364) {
            game.batch.draw(PlayButtonActive, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){
                game.setScreen(new PlayScreen(game));
            }
        }
        else
            {game.batch.draw(PlayButtonInactive, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);}
        /// HOW TO PLAY
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 373&& Gdx.input.getY() < 410) {
            game.batch.draw(HowtoplayActive, HOW_BUTTON_WIDTE, HOW_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){
                game.setScreen(new GameOverScreen(game));
            }
        }
        else
        {game.batch.draw(HowtoplayInactive, HOW_BUTTON_WIDTE, HOW_BUTTON_HEIGHT);}
        ///ABOUT
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 420 && Gdx.input.getY() < 460) {
            game.batch.draw(AboutButtonActive,ABOUT_BUTTON_WIDTH,ABOUT_BUTTON_HEIGHT);
        }
        else
        {game.batch.draw(AboutButtonInactive,ABOUT_BUTTON_WIDTH,ABOUT_BUTTON_HEIGHT);}
        /// EXIT
        if (Gdx.input.getX() > 88 && Gdx.input.getX() < 417 && Gdx.input.getY() > 470 && Gdx.input.getY() < 510) {
            game.batch.draw(ExitButtonActive,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        else
        {game.batch.draw(ExitButtonInactive,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);}
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

    }
}

