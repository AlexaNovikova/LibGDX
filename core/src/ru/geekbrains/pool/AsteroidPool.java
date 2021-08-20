package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Asteroid;
import ru.geekbrains.sprite.EnemyShip;

public class AsteroidPool extends SpritesPool<Asteroid> {

    private final Rect worldBounds;
    private final ExplosionPool explosionPool;

    public AsteroidPool(Rect worldBounds, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Asteroid newSprite() {
        return new Asteroid(worldBounds);
    }
}
