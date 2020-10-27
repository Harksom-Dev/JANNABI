package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screen.EmptyScreen2;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Screen.LoadingScreen;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Screen.*;

public class Jannabi extends Game {
	private Player player;
	public SpriteBatch batch;
	//create constant variable
	//create width & height of our game
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final int F_WIDTH = 1280;
	public static final int F_HEIGHT = 730;
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
	public static AssetManager manager;
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		/*manager.load("Audio/Music/i.mp3",Music.class);
		manager.load("Audio/Music/GameOverMusic.mp3",Music.class);
		manager.load("Audio/Sound/Slime/SlimeBeenHit.mp3", Sound.class);
		manager.load("Audio/Sound/Slime/PickupPotion.mp3",Sound.class);
		manager.load("Audio/Sound/Slime/hit.mp3",Sound.class);
		manager.load("Audio/Sound/Weapons/Gun.wav",Sound.class);
		manager.load("Audio/Sound/MainMenu/Select.mp3",Sound.class);
		manager.load("Audio/Sound/MainMenu/Selected.mp3",Sound.class);
		manager.load("Audio/Sound/MainMenu/openUp.mp3",Sound.class);
		manager.load("Audio/Sound/Player/beenHit.wav",Sound.class);
		manager.load("Audio/Sound/Player/Jump.mp3",Sound.class);
		manager.load("Audio/Sound/GameOver/GameOver.mp3",Sound.class);
		manager.load("Audio/Sound/Weapons/pistolReload.mp3",Sound.class);*/
		manager.finishLoading();

		//setScreen(new EmptyScreen2(this));
		//setScreen(new GameOverScreen(this));
		setScreen(new PlayScreen(this));
		//setScreen(new LoadingScreen(this));

	}

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		manager.dispose();
		batch.dispose();
	}


}
