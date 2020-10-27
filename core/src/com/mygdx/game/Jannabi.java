package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screen.PlayScreen;

public class Jannabi extends Game {
	public SpriteBatch batch;
	//create constant variable
	//create width & height of our game
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;
	public static final int PISTOL_CLIP = 13;
	public static final int SMG_CLIP = 20;
	public static final int SHOTGUN_CLIP = 8;

	public static final float SWORD_HIT_DELAY = 0.2f;
	public static final float PISTOL_FIRE_RATE = 0.5f;
	public static final float SMG_FIRE_RATE = 0.1f;
	public static final float SHOTGUN_FIRE_RATE = 0.5f;

	//create bit category for collision
	public static final short DEFAULT_BIT = 1;
	public static final short JANNABI_BIT = 2;
	public static final short OTHERLAYER_BIT = 4;
	public static final short PASS_BIT = 8;
	public static final short PISTOL_BULLET_BIT = 16;
	public static final short Edge_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ITEM_BIT = 128;
	public static final short SWORDHITBOX_BIT = 128;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
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
