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

import java.text.CollationElementIterator;

public class Hud {

    //    private static Image healthBar;healthBar
//    public Image imageHealth;
//    imageHealthprivate static int updateHp;
    public Stage stage;
    public Viewport viewport;


    static Player player;


    //implement field
    private static Integer score;
    private static Integer ammo;
    private static int allAmmo;
    private String x;

    //implement image in hud
    private Image ammoBox;
    private Image janabi;
    private static Image healthBar;

    //implement Label
    private static Label scoreLabel;
    private static Label ammoLabel;
    private static Label ammoBoxLabel;
    private Label textScoreLabel;
    private Label levelLabel;
    private Label stageLabel;
    private Label slashLabel;



    public Hud(SpriteBatch sb, Player player){
        //static field implement
        score = 0;
        ammo = player.getAmmo();
        allAmmo = player.getAllAmmo();

        //image implement
        ammoBox = new Image(new Texture("Hud/Ammo-box.png"));
        janabi = new Image(new Texture("Hud/Janabi.png"));
        healthBar = new Image(new Texture("Hud/Healthbar/1.png")); //default

        //set view and stage
        viewport = new FitViewport(Jannabi.F_WIDTH, Jannabi.F_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //implement table
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //set Label
        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        textScoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont() , Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stageLabel = new Label("STAGE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ammoLabel = new Label(String.format("%02d", ammo ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ammoBoxLabel = new Label(String.format("%02d", allAmmo ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        slashLabel = new Label("/", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //set table
        table.add(janabi).width(75).height(75).padTop(40).padLeft(30);
        table.add().expandX();
        table.add().width(20).height(12);
        table.add().expandX();
        table.add().expandX();
        table.add().expandX();
        table.add().width(75).height(75);
        table.add().width(75).height(75);
        table.row();
        table.add(ammoBox).width(40).height(40).padLeft(20);
        table.add(ammoLabel).width(2).height(10).padLeft(1);
        table.add(slashLabel).width(1).height(10).padLeft(1);
        table.add(ammoBoxLabel).width(1).height(10).padLeft(1);
        table.add().expandX();
        table.add().expandX();
        table.add().expandX();
        table.add().expandX();

        stage.addActor(table);
    }

    //method for add score
    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%03d",score));
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
