package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Screen.introToMainMenu;
import com.mygdx.game.Sprites.Player;

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
		manager.load("Audio/Music/GameOver.mp3", Music.class);
		manager.load("Audio/Music/Harksom.mp3", Music.class);
		manager.load("Audio/Music/jannabi.mp3", Music.class);
		manager.load("Audio/Music/MainMenu.mp3", Music.class);
		manager.load("Audio/Music/GameClear.mp3", Music.class);
		manager.load("Audio/Music/backgroundSong.mp3", Music.class);
		manager.load("Audio/Sound/gameoversound.mp3", Sound.class);
		manager.load("Audio/Sound/mons/monsterHit.wav", Sound.class);
		manager.load("Audio/Sound/mons/monDes.wav", Sound.class);
		manager.load("Audio/Sound/gun/pistol.wav", Sound.class);
		manager.load("Audio/Sound/gun/reload.mp3", Sound.class);
		manager.load("Audio/Sound/gun/shotGun.mp3", Sound.class);
		manager.load("Audio/Sound/gun/smg.mp3", Sound.class);
		manager.load("Audio/Sound/gun/sword.mp3", Sound.class);
		manager.load("Audio/Sound/gun/changeWeapons.mp3", Sound.class);
		manager.load("Audio/Sound/gun/outOfAmmo.wav", Sound.class);
		manager.load("Audio/Sound/main/isTouchButton.mp3", Sound.class);
		manager.load("Audio/Sound/main/selected.mp3", Sound.class);
		manager.load("Audio/Sound/player/beenHit.wav", Sound.class);
		manager.load("Audio/Sound/player/jump.mp3", Sound.class);
		manager.load("Audio/Sound/player/pickup.wav", Sound.class);
		manager.finishLoading();

		setScreen(new introToMainMenu(this));
		//setScreen(new GameOverScreen(this));
		//setScreen(new PlayScreen(this));
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
