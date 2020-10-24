package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;

public class EmptyScreen2 implements Screen {

    OrthographicCamera cam;
    Viewport viewport;
    SpriteBatch batch;
    Jannabi game;

    public EmptyScreen2(Jannabi game) {
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);
        viewport.apply();

        //this.batch = batch;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.app.log("IM IN RENDER_EMPTY2","Pass");
        game.setScreen(new MainMenuScreen(game));
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