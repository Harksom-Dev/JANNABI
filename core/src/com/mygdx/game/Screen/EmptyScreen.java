package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;

import java.lang.reflect.Array;

public class EmptyScreen implements Screen {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;


    OrthographicCamera cam;
    Viewport viewport;
    SpriteBatch batch;
    private Stage stage;

    Jannabi game;
    private float loadingTime = 0 ;
    Texture Background;
    Texture Background2;
    Texture Background3;


    public EmptyScreen(Jannabi game) {
        this.game = game;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,((Jannabi) game).batch);
        cam = new OrthographicCamera();
        Background = new Texture("Loading/01.png");
        Background2 = new Texture("Loading/02.png");
        Background3 = new Texture("Loading/03.png");
    }

    @Override
    public void show() { }


    @Override
    public void render(float delta) {
      loadingTime += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("IM IN RENDER_EMPTY","Pass");
        stage.draw();
        game.batch.begin();
        game.batch.draw(Background,0,0);

       // i'm sorry but i have to ///////////////////////
        if(loadingTime > 1){ game.batch.draw(Background2,0,0); }
        if (loadingTime >2){ game.batch.draw(Background3,0,0); }
        if (loadingTime >2.5){ game.setScreen(new PlayScreen(game)); }
        ////////////////////////////////////////////////
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
    public void dispose() { }
}