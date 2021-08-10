package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class MainShip extends Sprite {

    private static final float PADDING = 0.03f;


    private final float SPEED = 0.01f;
    private Rect worldBounds;

    private Vector2 v;
    private Vector2 dest;
    private Vector2 direction;


    public MainShip(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship")), 0f,0f, 0.5f, 1f);
        v = new Vector2();
        dest = new Vector2();
        direction= new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(worldBounds.getHeight() / 8);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (pos.dst(dest) > v.len()) {
            pos.add(v);
        } else {
            pos.set(dest);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x <= pos.x){
            dest.set(worldBounds.getLeft()+getHalfWidth(), pos.y);
        }
        if (touch.x > pos.x){
            dest.set(worldBounds.getRight()-getHalfWidth(), pos.y);
        }
        direction.set(dest).sub(pos).nor();
        v.set(direction).scl(SPEED);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        dest.set(pos);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==21){
            dest.set(worldBounds.getLeft()+getHalfWidth(), pos.y);
        }
        if(keycode==22){
            dest.set(worldBounds.getRight()-getHalfWidth(), pos.y);
        }
        direction.set(dest).sub(pos).nor();
        v.set(direction).scl(SPEED);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
      if(keycode==21||keycode==22){
          dest.set(pos);
      }
       return false;
    }
}


