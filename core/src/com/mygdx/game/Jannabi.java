package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screen.GameOverScreen;
import com.mygdx.game.Screen.MainMenuScreen;
import com.mygdx.game.Screen.PlayScreen;

public class Jannabi extends Game {
	public SpriteBatch batch;
	//create constant variable
	//create width & height of our game
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	//create bit category for collision (2bit)
	public static final short DEFAULT_BIT = 1;
	public static final short JANNABI_BIT = 2;
	public static final short OTHERLAYER_BIT = 4;
	public static final short PASS_BIT = 8;
	public static final short PISTOL_BULLET_BIT = 16;
	public static final short Edge_BIT = 32;
	public static final short ENEMY_BIT = 64;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
		//setScreen(new GameOverScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
