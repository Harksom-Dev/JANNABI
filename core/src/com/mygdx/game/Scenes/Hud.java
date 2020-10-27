package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
        viewport = new FitViewport(Jannabi.V_WIDTH, Jannabi.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //implement table
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //set Label
        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        textScoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stageLabel = new Label("STAGE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ammoLabel = new Label(String.format("%02d", ammo ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ammoBoxLabel = new Label(String.format("%02d", allAmmo ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        slashLabel = new Label("/", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //set table
        table.add().expandX();
        table.add(janabi).width(25).height(25);
        table.add().width(20).height(12);
        table.add().expandX();
        table.add().expandX();
        table.add().expandX();
        table.add(stageLabel).expandX().padTop(2);
        table.add(textScoreLabel).expandX().padTop(2);
        table.row();
        table.add().expandX();
        table.add(ammoBox).width(25).height(25);
        table.add(ammoLabel).width(2).height(10);
        table.add(slashLabel).width(0.5f).height(10);
        table.add(ammoBoxLabel).width(1).height(10);
        table.add().expandX();
        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();

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
//    public static void updateHealthbar(){
//        if(player.getHp() == 10){
//            healthBar = new Image(new Texture("Hud/Healthbar/1.png"));
//        }else if(player.getHp() == 9){
//            healthBar = new Image(new Texture("Hud/Healthbar/2.png"));
//        }
//    }

    public void dispose() {
        stage.dispose();
    }



}
