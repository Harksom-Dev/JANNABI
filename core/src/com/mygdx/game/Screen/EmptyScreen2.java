package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

public class EmptyScreen2 implements Screen {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    private Stage stage;
    private float loadingTime = 0 ;

    Jannabi game;
    OrthographicCamera cam;
    Viewport viewport;
    SpriteBatch batch;

    Texture Background;
    Texture Background2;


    public EmptyScreen2(Jannabi game) {
        this.game = game;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,((Jannabi) game).batch);
        cam = new OrthographicCamera();
        Background = new Texture("Loading/main.png");
        Background2 = new Texture("Loading/main2.png");
        Jannabi.manager.get("Audio/Sound/MainMenu/openUp.mp3", Sound.class).play();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        loadingTime += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("IM IN RENDER_EMPTY","Pass");
        stage.draw();
        game.batch.begin();
        game.batch.draw(Background,0,0);
        /////////////// I AM SORRY BUT I HAVE TO /////////////////////////////
        if(loadingTime > 2){ game.batch.draw(Background2,0,0); }
        if (loadingTime >3){ game.setScreen(new MainMenuScreen(game)); }
        //////////////////////////////////////////////////////////////////////
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