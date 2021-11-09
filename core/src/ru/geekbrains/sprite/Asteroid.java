package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.ExplosionPool;

public class Asteroid extends Sprite {

    private Rect worldBounds;
    private Vector2 v;
    private int damage;


    public Asteroid(Rect worldBounds) {
        regions = new TextureRegion[1];
        v = new Vector2();
        this.worldBounds= worldBounds;
    }

    public void set(
            TextureRegion region,
            float height,
            int damage,
            Vector2 pos0,
            Vector2 v0

    ) {
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.damage = damage;
    }


    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

}
