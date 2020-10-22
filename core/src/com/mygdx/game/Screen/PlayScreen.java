package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.btree.decorator.Random;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Slime;
import com.mygdx.game.tools.B2WorldCreator;
import com.mygdx.game.tools.worldContactListener;

public class PlayScreen implements Screen {
    //create Texture for background
    Texture background;

    //implement main game camera and viewport
    private Jannabi game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //create tile map variable
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //create texture atlas to get all frame
    private TextureAtlas atlas;

    //sprite variable
    private Player player;

    //box2d variable
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    public PlayScreen(Jannabi game) {

        background = new Texture("Background/Stage1/stage1.png");

        //create atlas and load from path
        atlas = new TextureAtlas("Sprite/allCharacter/character_gdx.pack");

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


    }

    //create getter for texture atlas
    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            player.fire();
        }

    }

    public void update(float dt){
        //check for input
        handleInput(dt);


        //dont know what this doing
        world.step(1/60f,6,2);

        //update player
        player.update(dt);
        //spawn all slimes
        for(Slime enemy : creator.getSlimes()){
            enemy.update(dt);
            if(enemy.getX() < player.getX() +224 / Jannabi.PPM){
                enemy.b2body.setActive(true);
                //we can use this to enable attack in the future
            }
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



        //render box2d

        //if we comment this we not gonna outline object but not sure we can collide or not
        b2dr.render(world, gamecam.combined);


        game.batch.setProjectionMatrix(gamecam.combined);

        //need to draw background before render and render before player.draw
        game.batch.begin();
        //multiple width to increase background (now get commented to check box2d)
        //now comment background due to check collision
        game.batch.draw(background,0,0,(Jannabi.V_WIDTH /Jannabi.PPM) * 8,Jannabi.V_HEIGHT / Jannabi.PPM);
        game.batch.end();

        //need to render after background
        renderer.render();

        //draw things
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getSlimes()){
            enemy.draw(game.batch);
        }
        game.batch.end();



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
