package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Jannabi;

public class GameOverScreen implements Screen {

    private static final int TRYAGAIN_BUTTON_WIDTH = 330 ;
    private static final int TRYAGAIN_BUTTON_HEIGHT = 148 ;
    private static final int MAINMENU_BUTTON_WIDTH = 544 ;
    private static final int MAINMENU_BUTTON_HEIGHT = 148;
    private static final int QUIT_BUTTON_WIDTH = 759 ;
    private static final int QUIT_BUTTON_HEIGHT = 148 ;


    Jannabi game ;
    Texture GameOverWallpaper ;
    Texture TryAgainButtonActive;
    Texture TryAgainButtonInactive;
    Texture MainMenuButtonActive;
    Texture MainMenuButtonInactive;
    Texture QuitButtonActive;
    Texture QuitButtonInactive;

    public GameOverScreen (Jannabi game){
        this.game = game;

        GameOverWallpaper = new Texture("GameOver/wallaper.png");
        TryAgainButtonActive = new Texture("GameOver/TryAgainActive.png");
        TryAgainButtonInactive = new Texture("GameOver/TryAgainInactive.png");
        MainMenuButtonActive = new Texture("GameOver/MainMenuActive.png");
        MainMenuButtonInactive = new Texture("GameOver/MainMenuInactive.png");
        QuitButtonActive = new Texture("GameOver/QuitActive.png");
        QuitButtonInactive = new Texture("GameOver/QuitInactive.png");

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(GameOverWallpaper,0,0);
            // TRYAGAIN
            if (Gdx.input.getX() > 330 && Gdx.input.getX() < 470 && Gdx.input.getY() > 530 && Gdx.input.getY() <573) {
                game.batch.draw(TryAgainButtonActive, TRYAGAIN_BUTTON_WIDTH, TRYAGAIN_BUTTON_HEIGHT);
                if (Gdx.input.isTouched()){
                    this.dispose();
                    game.batch.end();
                    game.setScreen(new PlayScreen(game));
                    return;
                }
            }else {
                game.batch.draw(TryAgainButtonInactive, TRYAGAIN_BUTTON_WIDTH, TRYAGAIN_BUTTON_HEIGHT);}
            // END_OF_TRYAGAIN

            //MAINMENU
            if (Gdx.input.getX() > 545 && Gdx.input.getX() < 735 && Gdx.input.getY() > 530 && Gdx.input.getY() <573){
                game.batch.draw(MainMenuButtonActive, MAINMENU_BUTTON_WIDTH, MAINMENU_BUTTON_HEIGHT);
                if (Gdx.input.isTouched()){
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
            } else {
                            game.batch.draw(QuitButtonInactive, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
                            }
            //END_OF_QUIT
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
