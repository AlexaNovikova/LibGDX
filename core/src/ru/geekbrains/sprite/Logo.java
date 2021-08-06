package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private final float SPEED = 0.01f;

    private Vector2 pos;
    private Vector2 movement;
    private Vector2 destination;
    private Vector2 direction;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        pos= new Vector2(0,0);
        movement = new Vector2();
        destination = new Vector2();
        direction = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight()/10);
    }

    public void move (){
        direction.set(destination).sub(pos).nor();
        movement.set(direction).scl(SPEED);
       if (pos.dst(destination) > movement.len()) {
            pos.add(movement);
        } else {
            pos.set(destination);
        }
    }

    public void draw(SpriteBatch batch) {
        move();
        setBottom(pos.y);
        setLeft(pos.x);
        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        destination.set(touch);
        return super.touchDown(touch, pointer, button);
    }
}
