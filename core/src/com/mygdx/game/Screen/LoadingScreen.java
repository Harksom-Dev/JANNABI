package com.mygdx.game.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.MainMenuScreen;

public class LoadingScreen implements Screen {
    private Jannabi mGame;
    private BitmapFont bf_loadProgress;
    private long progress = 0;
    private long startTime = 0;
    private ShapeRenderer mShapeRenderer;
    private OrthographicCamera camera;
    private final int screenWidth = 1280, screenHeight = 720;
    Texture wall;

    public LoadingScreen(Jannabi game) {
        mGame = (Jannabi) game;
        bf_loadProgress = new BitmapFont();
        bf_loadProgress.getData().setScale(2, 1);
        mShapeRenderer = new ShapeRenderer();
        startTime = TimeUtils.nanoTime();
        wall = new Texture("Loading/load.png");
        initCamera();
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        showLoadProgress();
    }


    /**
     * Show progress that updates after every half second "0.5 sec"
     */
    private void showLoadProgress() {

        long currentTimeStamp = TimeUtils.nanoTime();
        if (currentTimeStamp - startTime > TimeUtils.millisToNanos(500)) {
            startTime = currentTimeStamp;
            progress = progress + 20;
        }
        // Width of progress bar on screen relevant to Screen width
        float progressBarWidth = (screenWidth / 100) * progress;

        mGame.getBatch().setProjectionMatrix(camera.combined);
        mGame.getBatch().begin();
        mGame.batch.draw(wall,0,0);
        bf_loadProgress.draw(mGame.getBatch(), "Loading " + progress + " / " + 100, 10, 40);
        mGame.getBatch().end();

        mShapeRenderer.setProjectionMatrix(camera.combined);
        mShapeRenderer.begin(ShapeType.Filled);
        mShapeRenderer.setColor(Color.WHITE);
        mShapeRenderer.rect(0, 10, progressBarWidth, 10);
        mShapeRenderer.end();
        if (progress == 100)
            moveToMenuScreen();

    }

    /**
     * Move to menu screen after progress reaches 100%
     */
    private void moveToMenuScreen() {
        mGame.setScreen(new PlayScreen(mGame));
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        bf_loadProgress.dispose();
        mShapeRenderer.dispose();
    }

}
