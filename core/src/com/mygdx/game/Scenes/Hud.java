package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.awt.*;

public class Hud {
    public Stage stage;
    public Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private Label countDownLabel;
    private static Label scoreLabel;
    private Label textScoreLabel;
    private Label levelLabel;
    private Label stageLabel;
    private Label janabiLabel;

    public Hud(SpriteBatch sb){
        //worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Jannabi.V_WIDTH, Jannabi.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%03d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        textScoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stageLabel = new Label("STAGE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        janabiLabel = new Label("JANABI", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(janabiLabel).expandX().padTop(2);
        table.add(stageLabel).expandX().padTop(2);
        table.add(textScoreLabel).expandX().padTop(2);
        table.row();
        table.add(scoreLabel).expandX(); //must change to health
        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();

        stage.addActor(table);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%03d",score));
    }


}
