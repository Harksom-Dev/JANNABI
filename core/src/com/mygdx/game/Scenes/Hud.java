package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.GameOverScreen;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Player;


public class Hud {

    //    private static Image healthBar;healthBar
//    public Image imageHealth;
//    imageHealthprivate static int updateHp;
    public Stage stage;
    public Viewport viewport;



   // static Player player;
    Player player;


    //implement field
    private static Integer score;
    private static Integer ammo;
    private static int allAmmo;

    //implement image in hud
    private Image ammoBox;
    private Image janabi;
    private static Image healthBar;

    //implement Label
    private static Label ammoLabel;
    private static Label ammoBoxLabel;

    private ShapeRenderer shapeRenderer;
    private BitmapFont bitmapFont;
    private long health ;
    private final int screenWidth = 1280, screenHeight = 720;
    private OrthographicCamera camera;
    Jannabi game;


    public Hud(Jannabi game, Player player){
        this.game = game;
        this.player = player;
        ammo = player.getAmmo();
        allAmmo = player.getAllAmmo();
        shapeRenderer = new ShapeRenderer();
        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(2,1);
        health = player.getHp();
        initCamera();
        ShowHealth();

        //image implement
        ammoBox = new Image(new Texture("Hud/Ammo-box.png"));
        janabi = new Image(new Texture("Hud/Janabi.png"));
        healthBar = new Image(new Texture("Hud/Healthbar/01.png")); //default

        //set view and stage
        viewport = new FitViewport(1280, 720, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

       /* //implement table
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //set Label
        ammoLabel = new Label(String.format("%02d", ammo ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ammoBoxLabel = new Label(String.format("%02d", allAmmo ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));*/

    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
    }
    private void ShowHealth() {
        //long currentTimeStamp = TimeUtils.nanoTime();
        if (player.getHp() > 0) {
            health = health - 1;
        }
        // Width of progress bar on screen relevant to Screen width
        float healthBarWidth = (screenWidth / 100) * health;

        /*game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        bitmapFont.draw(game.getBatch(), "Loading " + health + " / " + 10, 100, 100);
        game.getBatch().end();*/

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(0, 10, healthBarWidth, 10);
        shapeRenderer.end();
    }

    //method for update ammo
    public static void updateAmmo(int value){
        ammo = value;
        ammoLabel.setText(String.format("%02d",ammo));
    }
    public static void updateAllammo(int value){
        allAmmo = value;
        ammoBoxLabel.setText(String.format("%02d",allAmmo));
    }



    public void dispose() {
        stage.dispose();
    }
}
