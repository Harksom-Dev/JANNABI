package com.mygdx.game.Sprites.Item;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Jannabi;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Mag extends Item {
    public Mag(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("clip"),0,0,16,16);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        //set box2d our animation now have width16 and height 32
        bdef.position.set(getX(),getY()/ Jannabi.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        //create fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / Jannabi.PPM);
        fdef.filter.categoryBits = Jannabi.ITEM_BIT;
        //what our item can collide with
        fdef.filter.maskBits = Jannabi.DEFAULT_BIT | Jannabi.OTHERLAYER_BIT | Jannabi.JANNABI_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.7f;
        //fdef.density = 50;
        //fdef.isSensor = false;//this sensor use for jumping through
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Player player) {
        player.getMag();
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2,body.getPosition().y - getHeight() / 2);
    }
}
