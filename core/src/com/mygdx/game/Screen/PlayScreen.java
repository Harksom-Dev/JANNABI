package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.linearmath.HullDesc;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemy.BlackShirt;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Item.Item;
import com.mygdx.game.Sprites.Item.ItemDef;
import com.mygdx.game.Sprites.Item.Potion;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Enemy.Slime;
import com.mygdx.game.tools.B2WorldCreator;
import com.mygdx.game.tools.worldContactListener;
import com.mygdx.game.Sprites.Player;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class PlayScreen implements Screen {
    public String imageAddress;
    //create Texture for background
    Texture background;
    Texture jannaHead;

    //creat Texture fot health bar
    Texture healthBar;

    //implement main game camera and viewport
    private Jannabi game;
    private OrthographicCamera gamecam;
    private OrthographicCamera camera;
    private Viewport gamePort;

    //create tile map variable
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //create texture atlas to get all frame
    private TextureAtlas atlas;

    //sprite variable
    private Player player;
    public static int hp = 10;

    //box2d variable
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //iterator
    private Iterator<Slime> slimeIterator;
    private Iterator<BlackShirt> BlackShirtIterator;

    //array for item
    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemToSpawn;

    //background music player
    private Music music;
    private BitmapFont bf_loadProgress;
    private long progress = 0;
    private ShapeRenderer shapeRenderer;
    private BitmapFont bf_healthProgress;
    private long health = 100;
    private final int healthWidth = 1000 ;
    private final int screenWidth = 1280, screenHeight = 720;
    private boolean consHit ;

    private Hud hud;
    Stage stage;
    Viewport viewport;


    public PlayScreen(Jannabi game) {

        background = new Texture("Background/Stage1/stage1.png");

        atlas = new TextureAtlas("Sprite/allCharacter/character_pack.pack");

        viewport = new FitViewport(1280,720);
        stage = new Stage(viewport,((Jannabi)game).batch);
        //set this class to current screen
        this.game = game;

        //set game camera
        gamecam = new OrthographicCamera();
        gamePort = new StretchViewport(Jannabi.V_WIDTH / Jannabi.PPM,Jannabi.V_HEIGHT/ Jannabi.PPM,gamecam);

        //create map and load fromfile
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map/Stage1/Stage1.tmx");
        renderer  = new OrthogonalTiledMapRenderer(map,1 / Jannabi.PPM);

        //set camera at start
        gamecam.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() / 2,0);

        //add tilemap to become box2d(static)
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();

        //call B2WorldCreator to set them at box2d
        creator = new B2WorldCreator(this);

        //create player class
        player = new Player(this);

        //use this class to use collision detect
        world.setContactListener(new worldContactListener());

        //initialize item
        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingDeque<ItemDef>();

        //Hud
        hud = new Hud(game.batch, this.player);
        //Health Bar
        bf_healthProgress = new BitmapFont();
        bf_healthProgress.getData().setScale(2,1);
        shapeRenderer = new ShapeRenderer();
        initCamera();
        jannaHead = new Texture("Hud/JanHead.png");


    }
    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
    }

    //create getter for texture atlas
    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void spawnItem(ItemDef idef){
        itemToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemToSpawn.isEmpty()){
            ItemDef idef = itemToSpawn.poll();
            if(idef.type == Potion.class){
                items.add(new Potion(this,idef.position.x,idef.position.y));
            }
        }
    }

    //create handle input when we get input from user
    public void handleInput( float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.b2body.getLinearVelocity().y == 0){
            player.b2body.applyLinearImpulse(new Vector2(0,3),player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0.07f,0),player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.07f,0),player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.C)){
            player.attack(dt);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            player.changeWeapon("sword");
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            player.changeWeapon("pistol");
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            player.changeWeapon("smg");
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            player.changeWeapon("shotgun");
        }else{
            player.setDuplicatedChange(false);
            player.setFirstShot(false);
        }

    }

    public void update(float dt){
        if(player.getHp() > 0){
            //check for input
            handleInput(dt);
        }

        handleSpawningItems();

        //dont know what this doing
        world.step(1/60f,6,2);

        //update player
        player.update(dt);
        //spawn all slimes

        /////////////need to destroy enemy in playscreen////////////
        slimeIterator = creator.getSlimeIterator();
        while(slimeIterator.hasNext())
        {
            Slime nextSlime = slimeIterator.next();
            nextSlime.update(dt);
            if(nextSlime.getX()<player.getX()+224 / Jannabi.PPM)
                nextSlime.b2body.setActive((true));
            if (nextSlime.getToDestroy() && !nextSlime.getDestroyed())
            {
                world.destroyBody(nextSlime.b2body);
                nextSlime.setDestroyed(true);
            }

            if (nextSlime.getStateTime() >= 1 && nextSlime.getDestroyed())
            {
                Gdx.app.log("removing slime from array", "");
                slimeIterator.remove();
            }

        }

        BlackShirtIterator = creator.getBlackShirtIterator();
        while(BlackShirtIterator.hasNext())
        {
            BlackShirt nextBlackShirt = BlackShirtIterator.next();
            nextBlackShirt.update(dt);
            if(nextBlackShirt.getX()<player.getX()+224 / Jannabi.PPM)
                nextBlackShirt.b2body.setActive((true));
            if (nextBlackShirt.getToDestroy() && !nextBlackShirt.getDestroyed())
            {
                world.destroyBody(nextBlackShirt.b2body);
                nextBlackShirt.setDestroyed(true);
            }

            if (nextBlackShirt.getStateTime() >= 1 && nextBlackShirt.getDestroyed())
            {
                Gdx.app.log("removing slime from array", "");
                slimeIterator.remove();
            }

        }

        for(Item item : items){
            item.update(dt);
        }
        //track camera with main player
        //check if we ae at the beginning of the stage or end of stage to freeze camera
        if(player.b2body.getPosition().x > (Jannabi.V_WIDTH/2) /Jannabi.PPM ){
            if(player.b2body.getPosition().x < (((Jannabi.V_WIDTH * 7) + (Jannabi.V_WIDTH/2)) /Jannabi.PPM))
            gamecam.position.x = player.b2body.getPosition().x;
        }


        //update camera
        gamecam.update();

        //set view to camera
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {

        update(delta);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ////////////////// HUD /////////////////////////////////////////////////
        if (player.isBeenHit() && consHit == false) {
            consHit = true;
        }else{ consHit = false; }
        // Width of progress bar on screen relevant to Screen width
        float healthBarWidth = (50) * player.getHp() ;
        game.batch.setProjectionMatrix(camera.combined);

        ////////////////////////////////////////////////////////////////////////

        //if we comment this we not gonna outline object but not sure we can collide or not
        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        //need to draw background before render and render before player.draw
        game.batch.begin();
        //multiple width to increase background (now get commented to check box2d)
        //can comment background  to check collision
        //game.batch.draw(background,0,0,(Jannabi.V_WIDTH /Jannabi.PPM) * 8,Jannabi.V_HEIGHT / Jannabi.PPM);
        game.batch.end();
        //need to render after background
        renderer.render();
        //draw things
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getSlimes()){ enemy.draw(game.batch); }
        for(Item item : items){ item.draw(game.batch); }
        if (gameOver()){ game.setScreen(new GameOverScreen(game)); }
        game.batch.draw(jannaHead,100,100);

        game.batch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(100, 625, healthBarWidth, 25);
        shapeRenderer.end();
        hud.stage.draw();
    }
    public boolean gameOver(){
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 2  ){
            return true;
        }else{return false;}
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();

    }
}
