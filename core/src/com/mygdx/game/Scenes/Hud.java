package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Player;

import java.awt.*;

public class Hud {
    public Stage stage;
    public Viewport viewport;

    //player for get ammo
    Player player;

    //score field
    private static Integer score;
    private static Integer ammo;

    //image in hud
    private Image gun;
    private Image janabi;
    private Image health;

    //Label
    private static Label scoreLabel;
    private static Label ammoLabel;
    private Label textScoreLabel;
    private Label levelLabel;
    private Label stageLabel;


    public Hud(SpriteBatch sb, Player player){
        //static field implement
        score = 0;
        ammo = player.getAmmo();

        //image implement
        gun = new Image(new Texture("Hud/Pistol.png"));
        janabi = new Image(new Texture("Hud/Janabi.png"));
        health = new Image(new Texture("Hud/Healthbar/1.png"));


        viewport = new FitViewport(Jannabi.V_WIDTH, Jannabi.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);


        Table table = new Table();

        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        textScoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stageLabel = new Label("STAGE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ammoLabel = new Label(String.format("%02d", ammo ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add().expandX();
        table.add(janabi).width(25).height(25);
        table.add(health).width(200).height(20);
        table.add().expandX();
        table.add(stageLabel).expandX().padTop(2);
        table.add(textScoreLabel).expandX().padTop(2);
        table.row();
        table.add().expandX();
        table.add(gun).width(25).height(25);
        table.add(ammoLabel).expandX();
        table.add().expandX();      // ammoLabel
        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();

        stage.addActor(table);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%03d",score));
    }

    public static void updateAmmo(int value){
        ammo = value;
        ammoLabel.setText(String.format("%02d",ammo));
    }


}
