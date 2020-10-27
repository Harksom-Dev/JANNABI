package com.mygdx.game.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;

public class Credits implements Screen {

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;

    Music music;

    Jannabi game;
    OrthographicCamera cam;
    Viewport viewport;
    SpriteBatch batch;
    Stage stage;

    Texture cWallpaper;

    public Credits(Jannabi game){
        this.game = game;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,((Jannabi) game).batch);
        cam = new OrthographicCamera();
        cWallpaper = new Texture("Credit/credit.png");
        music = Jannabi.manager.get("Audio/Music/jannabi.mp3", Music.class);
        music.setLooping(true);
        music.play();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("IM IN RENDER_EMPTY","Pass");
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
        stage.draw();
        game.batch.begin();
        game.batch.draw(cWallpaper,0,0);

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
        music.dispose();
    }
}
